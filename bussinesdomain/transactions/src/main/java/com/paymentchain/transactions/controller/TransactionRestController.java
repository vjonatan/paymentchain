/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paymentchain.transactions.controller;

import com.paymentchain.transactions.entity.Transaction;
import com.paymentchain.transactions.repository.TransactionRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jonat
 */
@RestController
@RequestMapping("/transaction")
public class TransactionRestController {
    
    @Autowired
    TransactionRepository transactionRepository;
    
    @GetMapping()
    public List<Transaction> findAll(){
        return transactionRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Optional<Transaction> get(@PathVariable String id){
        return transactionRepository.findById(Long.valueOf(id));
    }
    
    //put
    @PutMapping("/{id}")
    public ResponseEntity<?> put (@PathVariable String id, @RequestBody Transaction input){
        
        Transaction transaction = transactionRepository.findById(Long.valueOf(id)).get();
        
        if (transaction != null){
            
            transaction.setReference(input.getReference());
            transaction.setAccountIban(input.getAccountIban());
            transaction.setAmount(input.getAmount());
            transaction.setFee(input.getFee());
            
            Transaction tr = transactionRepository.save(transaction);
            
            return ResponseEntity.ok(tr);

        }
        
        return null;
    }
    
    //post
    @PostMapping
    public ResponseEntity<?> post(@RequestBody Transaction input){
        
       Transaction response = transactionRepository.save(input);
        
        return ResponseEntity.ok(response);
    }
    
    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        
        Transaction transaction = transactionRepository.findById(Long.valueOf(id)).get();
        
        if (transaction != null) {
            transactionRepository.delete(transaction);
        }
        
        return ResponseEntity.ok().build();
    }
   
}
