/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/SpringFramework/Repository.java to edit this template
 */
package com.paymentchain.customer.repository;

import com.paymentchain.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author jonat
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    @Query("SELECT c FROM Customer c WHERE c.code = ?1")
    public Customer findByCode(String code);
    
    @Query("SELECT c FROM Customer c WHERE c.iban = ?1")
    public Customer findByAccount(String iban);
    
}
