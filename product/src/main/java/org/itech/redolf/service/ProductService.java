package org.itech.redolf.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.itech.redolf.dto.ProductRequest;
import org.itech.redolf.dto.ProductResponse;
import org.itech.redolf.model.Product;
import org.itech.redolf.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ModelMapper modelmapper;
    private final ProductRepository repository;

    public void createProduct(ProductRequest productRequest){
        Product product = modelmapper.map(productRequest, Product.class);
        repository.save(product);
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = repository.findAll();
       return products .stream().map(this::mapToResponse).toList();
    }

    private ProductResponse mapToResponse(Product product){
        return modelmapper.map(product,ProductResponse.class);
    }

    public void  deleteProductByName(String productName){
        repository.deleteProductByName(productName);
    }
}
