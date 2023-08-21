package com.fernandes.commerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fernandes.commerce.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

    
}
