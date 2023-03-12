/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paymentchain.transactions.repository;

import com.paymentchain.transactions.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author jonat
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
}
