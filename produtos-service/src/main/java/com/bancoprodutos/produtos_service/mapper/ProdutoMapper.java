package com.bancoprodutos.produtos_service.mapper;

import com.bancoprodutos.produtos_service.domain.ProdutoBancario;
import com.bancoprodutos.produtos_service.dto.ProdutoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {
    ProdutoDTO toDTO(ProdutoBancario entity);
    ProdutoBancario toEntity(ProdutoDTO dto);
}