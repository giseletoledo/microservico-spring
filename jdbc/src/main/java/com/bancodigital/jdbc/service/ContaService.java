package com.bancodigital.jdbc.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.bancodigital.jdbc.dto.ProdutoBancarioDTO;
import com.bancodigital.jdbc.exception.BancoDadosException;
import com.bancodigital.jdbc.exception.RegraNegocioException;
import com.bancodigital.jdbc.feign.ProdutoBancarioClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.bancodigital.jdbc.dao.ClienteDAO;
import com.bancodigital.jdbc.dao.ContaDAO;
import com.bancodigital.jdbc.dto.ContaDTO;
import com.bancodigital.jdbc.mapper.ContaMapper;
import com.bancodigital.jdbc.model.Cliente;
import com.bancodigital.jdbc.model.Conta;

@Service
public class ContaService {

    @Autowired
    private ContaDAO contaDAO;

    @Autowired
    private ClienteDAO clienteDAO;

    private final ProdutoBancarioClient produtoClient;

    public ContaService(ProdutoBancarioClient produtoClient) {
        this.produtoClient = produtoClient;
    }

    private static final Logger logger = LoggerFactory.getLogger(ContaService.class);

    @Cacheable(value = "produtosCache", key = "'todosProdutos'")
    public List<ProdutoBancarioDTO> obterProdutosParaConta() {
        return produtoClient.listarTodosProdutos();
    }

    public void criarConta(ContaDTO dto) throws SQLException {
        Cliente cliente = clienteDAO.buscarClientePorId(dto.clienteId());
        if (cliente == null) throw new IllegalArgumentException("Cliente não encontrado.");

        Conta conta = new Conta(
                null,
                dto.numero(),
                cliente,
                dto.chavePix(),
                dto.tipo().toUpperCase(),
                dto.limiteEspecial() != null ? dto.limiteEspecial() : BigDecimal.ZERO
        );

        switch (dto.tipo().toUpperCase()) {
            case "CORRENTE" -> conta.setRegraTaxa(new RegraTaxaContaCorrente());
            case "POUPANCA" -> conta.setRegraRendimento(new RegraRendimentoContaPoupanca());
            default -> throw new IllegalArgumentException("Tipo de conta inválido.");
        }

        contaDAO.criarConta(conta);
    }

    public ContaDTO buscarContaPorId(Long id) throws SQLException {
        Conta conta = contaDAO.buscarContaPorId(id);
        return ContaMapper.toDTO(conta);
    }

    public BigDecimal consultarSaldo(Long id) throws SQLException {
        return contaDAO.buscarContaPorId(id).getSaldo();
    }

    public void depositar(Long id, BigDecimal valor) throws SQLException {
        Conta conta = contaDAO.buscarContaPorId(id);
        conta.depositar(valor);
        contaDAO.atualizarConta(conta);
    }

    public void sacar(Long id, BigDecimal valor) throws SQLException {
        Conta conta = contaDAO.buscarContaPorId(id);
        if (!conta.sacar(valor)) {
            throw new IllegalArgumentException("Saldo insuficiente.");
        }
        contaDAO.atualizarConta(conta);
    }

    public void transferir(Long origemId, Long destinoId, BigDecimal valor) throws SQLException {
        Conta origem = contaDAO.buscarContaPorId(origemId);
        Conta destino = contaDAO.buscarContaPorId(destinoId);

        origem.transferirPara(destino, valor);

        contaDAO.atualizarConta(origem);
        contaDAO.atualizarConta(destino);
    }

    public void realizarPix(Long origemId, String chavePixDestino, BigDecimal valor) throws SQLException {
        Conta origem = contaDAO.buscarContaPorId(origemId);
        Conta destino = contaDAO.buscarContaPorChavePix(chavePixDestino);

        if (destino == null) throw new IllegalArgumentException("Chave Pix não encontrada.");

        origem.transferirPara(destino, valor);

        contaDAO.atualizarConta(origem);
        contaDAO.atualizarConta(destino);
    }

    public String gerarExtratosCliente(Long clienteId) throws SQLException {
        List<Conta> contas = contaDAO.buscarContasPorClienteId(clienteId);
        StringBuilder sb = new StringBuilder();

        for (Conta conta : contas) {
            sb.append(conta.gerarExtrato()).append("\n\n");
        }

        return sb.toString();
    }

    public void aplicarTaxaManutencao(Long id) throws SQLException {
        Conta conta = contaDAO.buscarContaPorId(id);
        conta.aplicarTaxa();
        contaDAO.atualizarConta(conta);
    }

    public void aplicarRendimento(Long id) throws SQLException {
        Conta conta = contaDAO.buscarContaPorId(id);
        conta.aplicarRendimento();
        contaDAO.atualizarConta(conta);
    }

    public String gerarExtrato(Long id) throws SQLException {
        Conta conta = contaDAO.buscarContaPorId(id);
        return conta.gerarExtrato();
    }

    public boolean verificarTransacoesPendentes(Long contaId) throws SQLException {
        Conta conta = contaDAO.buscarContaPorId(contaId);
        return conta.getTransacoesNaoPersistidas().isEmpty();
    }

    /**
     * Deleta uma conta do sistema após validar todas as regras de negócio
     * @param id ID da conta a ser deletada
     * @throws "ContaNaoEncontradaException" se a conta não existir
     * @throws IllegalStateException se a conta não puder ser deletada (saldo ≠ 0 ou transações pendentes)
     * @throws BancoDadosException se ocorrer erro no banco de dados
     */
    public void deletarConta(Long id) {
        try {
            // 1. Valida existência
            Conta conta = contaDAO.buscarContaPorId(id);

            // 2. Valida regras de negócio
            validarPodeDeletar(conta);

            // 3. Executa deleção
            contaDAO.deletarConta(id);

        } catch (SQLException e) {
            logger.error("Erro ao deletar conta ID {}: {}", id, e.getMessage());
            throw new BancoDadosException("Falha na operação de deleção", e);
        }
    }

    private void validarPodeDeletar(Conta conta) {
        if (conta.getSaldo().compareTo(BigDecimal.ZERO) != 0) {
            throw new RegraNegocioException("Conta com saldo não pode ser deletada");
        }

        if (!conta.getTransacoesNaoPersistidas().isEmpty()) {
            throw new RegraNegocioException("Conta com transações pendentes");
        }
    }

    public List<Conta> buscarContasPorClienteId(Long clienteId) {
        if (clienteId == null || clienteId <= 0) {
            throw new IllegalArgumentException("ID de cliente inválido.");
        }

        return contaDAO.buscarContasPorClienteId(clienteId);
    }
}
