package com.fernandes.commerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fernandes.commerce.dto.ProductDTO;
import com.fernandes.commerce.entities.Product;
import com.fernandes.commerce.repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        Product product = repository.findById(id).get();
        return new ProductDTO(product);
    }
    
}
