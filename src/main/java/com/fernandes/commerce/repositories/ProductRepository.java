package com.fernandes.commerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fernandes.commerce.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
