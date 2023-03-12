/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.paymentchain.transactions.entity;

/**
 *
 * @author jonat
 */
enum Status {
    
    Pendiente("01"),
    Liquidada("02"),
    Rechazada("03"),
    Cancelada("04");
    
    private String status;
    
    private Status(String param){
        this.status = param;
    }
    
    public String getStatus(){
        return this.status;
    }
    
}
