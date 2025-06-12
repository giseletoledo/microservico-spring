package com.bancoprodutos.produtos_service.core.usecase;

import com.bancoprodutos.produtos_service.dto.ProdutoDTO;
import java.util.List;

public interface ProdutoService {
    List<ProdutoDTO> listarProdutos();
    ProdutoDTO salvarProduto(ProdutoDTO dto);
}

