package com.bancoprodutos.produtos_service.adapter.in;

import com.bancoprodutos.produtos_service.core.usecase.ProdutoService;
import com.bancoprodutos.produtos_service.dto.request.ProdutoRequest;
import com.bancoprodutos.produtos_service.dto.response.ProdutoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> listar() {
        return ResponseEntity.ok(produtoService.listarProdutos());
    }

    @PostMapping
    public ResponseEntity<ProdutoResponse> criar(@RequestBody ProdutoRequest request) {
        return ResponseEntity.ok(produtoService.salvarProduto(request));
    }
}