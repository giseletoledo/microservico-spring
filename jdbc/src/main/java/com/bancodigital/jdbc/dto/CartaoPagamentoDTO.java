package com.bancodigital.jdbc.dto;

import java.math.BigDecimal;

public record CartaoPagamentoDTO(
 BigDecimal valor,
 String descricao
) {}

