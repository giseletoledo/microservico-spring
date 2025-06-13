package com.bancoprodutos.produtos_service.core.domain;

import com.bancoprodutos.produtos_service.model.TipoProduto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProdutoBancario {
    private Long id;
    private String nome;
    private String descricao;
    private TipoProduto tipo;
    private BigDecimal taxaJuros;
    private LocalDateTime dataCriacao;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoProduto getTipo() {
        return tipo;
    }

    public void setTipo(TipoProduto tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getTaxaJuros() {
        return taxaJuros;
    }

    public void setTaxaJuros(BigDecimal taxaJuros) {
        this.taxaJuros = taxaJuros;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public String toString() {
        return "ProdutoBancario{" +
               "id=" + id +
               ", nome='" + nome + '\'' +
               ", descricao='" + descricao + '\'' +
               ", tipo=" + tipo +
               ", taxaJuros=" + taxaJuros +
               ", dataCriacao=" + dataCriacao +
               '}';
    }
}
