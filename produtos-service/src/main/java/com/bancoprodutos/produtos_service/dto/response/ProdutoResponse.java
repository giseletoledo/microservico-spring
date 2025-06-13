package com.bancoprodutos.produtos_service.dto.response;

import com.bancoprodutos.produtos_service.model.TipoProduto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProdutoResponse(
            Long id,
            LocalDateTime dataCriacao,
            @NotBlank String nome,
            String descricao,
            @NotNull TipoProduto tipo,
            @Positive BigDecimal taxaJuros
) {}

