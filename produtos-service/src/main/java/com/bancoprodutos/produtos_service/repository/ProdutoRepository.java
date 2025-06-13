package com.bancoprodutos.produtos_service.repository;

import com.bancoprodutos.produtos_service.core.domain.ProdutoBancario;
import java.util.List;

public interface ProdutoRepository {
    ProdutoBancario save(ProdutoBancario produto);
    List<ProdutoBancario> findAll();
}
