/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/SpringFramework/Repository.java to edit this template
 */
package com.paymentchain.product.repository;

import com.paymentchain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author jonat
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
