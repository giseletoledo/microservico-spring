package com.bancoprodutos.produtos_service.repository;

import com.bancoprodutos.produtos_service.model.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProdutoRepository extends JpaRepository<ProdutoEntity, Long> {
}
