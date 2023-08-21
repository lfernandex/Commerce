package com.fernandes.commerce.dto;

import java.time.Instant;

import com.fernandes.commerce.entities.Payment;


public class PaymentDTO {
    
    private Long id;
    private Instant moment;


    public PaymentDTO(Long id, Instant moment) {
        this.id = id;
        this.moment = moment;
    }
    
     public PaymentDTO(Payment entity) {
        id = entity.getId();
        moment = entity.getMoment();
    }

    public Long getId() {
        return id;
    }

    public Instant getMoment() {
        return moment;
    }

    
}
