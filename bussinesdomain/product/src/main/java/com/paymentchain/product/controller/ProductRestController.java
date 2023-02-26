/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/RestController.java to edit this template
 */
package com.paymentchain.product.controller;

import com.paymentchain.product.entity.Product;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import com.paymentchain.product.repository.ProductRepository;

@RestController
@RequestMapping("/product")
public class ProductRestController {
    
    @Autowired
    ProductRepository productRepository;
    
    @GetMapping()
    public List<Product> findAll() {
        return productRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Optional<Product> get(@PathVariable String id) {
        return productRepository.findById(Long.valueOf(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable String id, @RequestBody Product input) {
        Product findById = productRepository.findById(Long.valueOf(id)).get();
        
        if (findById != null) {
            findById.setCode(input.getCode());
            findById.setName(input.getName());         
        }
        
        Product save = productRepository.save(findById);
        
        return ResponseEntity.ok(save);
    }
    
    @PostMapping
    public ResponseEntity<?> post(@RequestBody Product input) {
        Product save = productRepository.save(input);
        return ResponseEntity.ok(save);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        
        Product product = productRepository.findById(Long.valueOf(id)).get();
        
        if (product != null)
            productRepository.delete(product);
        
        return ResponseEntity.ok().build();
    }
    
}
