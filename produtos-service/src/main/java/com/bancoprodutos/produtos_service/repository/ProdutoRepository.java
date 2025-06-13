package com.bancoprodutos.produtos_service.repository;

import com.bancoprodutos.produtos_service.core.domain.ProdutoBancario;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository {
    ProdutoBancario save(ProdutoBancario produto);
    List<ProdutoBancario> findAll();
}
