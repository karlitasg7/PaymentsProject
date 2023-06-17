package com.ks.product.controller;

import com.ks.product.dto.ProductRequest;
import com.ks.product.dto.ProductResponse;
import com.ks.product.entities.Product;
import com.ks.product.mapper.ProductRequestMapper;
import com.ks.product.mapper.ProductResponseMapper;
import com.ks.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductRestController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductResponseMapper productResponseMapper;

    @Autowired
    private ProductRequestMapper productRequestMapper;

    @GetMapping
    public List<ProductResponse> findAll() {
        return productResponseMapper.fromList(productRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> get(@PathVariable Long id) {
        return productRepository
                .findById(id)
                .map(value -> ResponseEntity.ok(productResponseMapper.fromEntity(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> put(@PathVariable Long id, @RequestBody ProductRequest input) {
        Product product = productRepository.save(productRequestMapper.fromRequest(input));
        return ResponseEntity.ok(productResponseMapper.fromEntity(product));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> post(@RequestBody ProductRequest input) {

        Product newProduct = productRepository.save(productRequestMapper.fromRequest(input));

        return ResponseEntity.ok(productResponseMapper.fromEntity(newProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponse> delete(@PathVariable Long id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            productRepository.delete(product.get());
        }

        return ResponseEntity.ok().build();
    }

}
