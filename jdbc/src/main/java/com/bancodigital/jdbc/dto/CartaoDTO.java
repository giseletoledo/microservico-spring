package com.bancodigital.jdbc.dto;

import com.bancodigital.jdbc.model.TipoCartao;

import java.math.BigDecimal;

public record CartaoDTO(
		Long id,
		Long contaId,
		String numeroCartao,
		TipoCartao tipoCartao, // "CREDITO" ou "DEBITO"
		BigDecimal limite,
		BigDecimal limiteDiario,
		boolean ativo,
		String senha
	) {}


