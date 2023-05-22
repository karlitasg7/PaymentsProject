package com.ks.product.controller;

import com.ks.product.entities.Product;
import com.ks.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductRestController {

    @Autowired
    ProductRepository productRepository;

//    @Value("${spring.datasource.username}")
//    private String test;

    @GetMapping
    public List<Product> findAll() {
//        System.out.println(test);
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable Long id) {
        return productRepository.findById(id).get();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> put(@PathVariable Long id, @RequestBody Product input) {
        Product product = productRepository.save(input);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> post(@RequestBody Product input) {

        Product newProduct = productRepository.save(input);

        return ResponseEntity.ok(newProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable Long id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            productRepository.delete(product.get());
        }

        return ResponseEntity.ok().build();
    }

}
