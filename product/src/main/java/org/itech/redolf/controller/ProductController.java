package org.itech.redolf.controller;

import lombok.RequiredArgsConstructor;
import org.itech.redolf.dto.ProductRequest;
import org.itech.redolf.dto.ProductResponse;
import org.itech.redolf.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    private void createProduct(@RequestBody ProductRequest productRequest) {
        service.createProduct(productRequest);
    }

    @GetMapping("/findAll")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return service.getAllProducts();
    }

    @DeleteMapping("/delete/{name}")
    @ResponseStatus(HttpStatus.OK)
    private void deleteProductByName(@PathVariable(required = true) String name) {
        service.deleteProductByName(name);
    }
}
