package com.bancodigital.jdbc.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.bancodigital.jdbc.model.Conta;
import com.bancodigital.jdbc.model.TipoTransacao;
import com.bancodigital.jdbc.model.Transacao;

public class RegraRendimentoContaPoupanca implements RegraRendimento {

	@Override
	public void aplicarRendimento(Conta conta) {
	    if (!"POUPANCA".equalsIgnoreCase(conta.getTipoConta())) return;

	    double taxaAnual = switch (conta.getCliente().getTipo()) {
	        case COMUM -> 0.005;
	        case SUPER -> 0.007;
	        case PREMIUM -> 0.009;
	        default -> 0.0;
	    };

	    BigDecimal rendimento = conta.getSaldo()
	        .multiply(BigDecimal.valueOf(Math.pow(1 + taxaAnual, 1.0 / 12) - 1))
	        .setScale(2, RoundingMode.HALF_EVEN);

	    conta.depositar(rendimento); // Já adiciona transação de depósito
	    conta.adicionarTransacao(new Transacao("Rendimento mensal", rendimento, TipoTransacao.DEPOSITO)); // 👈 Este é essencial!
	}
}
