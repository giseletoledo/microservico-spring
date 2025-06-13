package com.bancoprodutos.produtos_service.mapper;

import com.bancoprodutos.produtos_service.core.domain.ProdutoBancario;
import com.bancoprodutos.produtos_service.dto.request.ProdutoRequest;
import com.bancoprodutos.produtos_service.dto.response.ProdutoResponse;
import com.bancoprodutos.produtos_service.model.ProdutoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProdutoMapper {

    // ProdutoRequest → ProdutoBancario
    ProdutoBancario requestToDomain(ProdutoRequest request);

    // ProdutoBancario → ProdutoEntity (para persistência)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    ProdutoEntity toEntity(ProdutoBancario domain);

    // ProdutoEntity → ProdutoBancario (ao ler do banco)
    ProdutoBancario toDomain(ProdutoEntity entity);

    // ProdutoBancario → ProdutoResponse (para retornar à API)
    ProdutoResponse toResponse(ProdutoBancario domain);
}
