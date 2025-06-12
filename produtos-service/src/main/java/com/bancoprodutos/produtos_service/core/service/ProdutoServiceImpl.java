package com.bancoprodutos.produtos_service.core.service;

import com.bancoprodutos.produtos_service.core.usecase.ProdutoService;
import com.bancoprodutos.produtos_service.dto.ProdutoDTO;
import com.bancoprodutos.produtos_service.mapper.ProdutoMapper;
import com.bancoprodutos.produtos_service.repository.ProdutoRepository;
import java.util.List;
import java.util.stream.Collectors;

public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository repository;
    private final ProdutoMapper mapper;

    public ProdutoServiceImpl(ProdutoRepository repository, ProdutoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ProdutoDTO> listarProdutos() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProdutoDTO salvarProduto(ProdutoDTO dto) {
        var entity = mapper.toEntity(dto);
        var salvo = repository.save(entity);
        return mapper.toDTO(salvo);
    }
}