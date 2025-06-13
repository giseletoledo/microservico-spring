package com.bancoprodutos.produtos_service.core.service;

import com.bancoprodutos.produtos_service.core.domain.ProdutoBancario;
import com.bancoprodutos.produtos_service.core.usecase.ProdutoService;
import com.bancoprodutos.produtos_service.dto.request.ProdutoRequest;
import com.bancoprodutos.produtos_service.dto.response.ProdutoResponse;
import com.bancoprodutos.produtos_service.mapper.ProdutoMapper;
import com.bancoprodutos.produtos_service.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository repository;
    private final ProdutoMapper mapper;

    public ProdutoServiceImpl(ProdutoRepository repository, ProdutoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<ProdutoResponse> listarProdutos() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public ProdutoResponse salvarProduto(ProdutoRequest request) {
        ProdutoBancario domain = mapper.requestToDomain(request);
        ProdutoBancario saved = repository.save(domain);
        return mapper.toResponse(saved);
    }
}
