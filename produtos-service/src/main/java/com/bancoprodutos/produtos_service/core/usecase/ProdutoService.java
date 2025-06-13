package com.bancoprodutos.produtos_service.core.usecase;

import com.bancoprodutos.produtos_service.dto.request.ProdutoRequest;
import com.bancoprodutos.produtos_service.dto.response.ProdutoResponse;

import java.util.List;

public interface ProdutoService {
    List<ProdutoResponse> listarProdutos();
    ProdutoResponse salvarProduto(ProdutoRequest request);
}

