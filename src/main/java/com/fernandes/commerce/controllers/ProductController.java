package com.fernandes.commerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fernandes.commerce.dto.ProductDTO;
import com.fernandes.commerce.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
    
    @Autowired
    private ProductService service;

    @GetMapping(value = "{id}")
    public ProductDTO findById(@PathVariable Long id){
        return service.findById(id);
    }

}
