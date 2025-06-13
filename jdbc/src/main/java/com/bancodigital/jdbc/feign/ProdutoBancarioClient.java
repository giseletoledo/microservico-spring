package com.bancodigital.jdbc.feign;

import com.bancodigital.jdbc.dto.ProdutoBancarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "produtoBancarioClient", url = "${produtos-service.url}")
public interface ProdutoBancarioClient {

        @GetMapping("/produtos")
        List<ProdutoBancarioDTO> listarTodosProdutos();

        @GetMapping("/produtos/tipo/{tipo}")
        List<ProdutoBancarioDTO> buscarProdutosPorTipo(@PathVariable String tipo);
    }
