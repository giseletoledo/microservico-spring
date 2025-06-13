package com.bancoprodutos.produtos_service.dto.request;

import com.bancoprodutos.produtos_service.model.TipoProduto;
import jakarta.annotation.Nullable;

import java.math.BigDecimal;

public record ProdutoRequest(
        String nome,
        @Nullable String descricao,
        TipoProduto tipo,
        BigDecimal taxaJuros
) {}
