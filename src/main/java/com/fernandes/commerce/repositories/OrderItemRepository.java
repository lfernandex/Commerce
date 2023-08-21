package com.fernandes.commerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fernandes.commerce.entities.OrderItem;
import com.fernandes.commerce.entities.OrderItemPK;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK>{

    
}
