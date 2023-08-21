package com.fernandes.commerce.dto;

import com.fernandes.commerce.entities.Category;

public class CategoryDTO {

    private Long id;
    private String name;

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDTO(Category cat) {
        id = cat.getId();
        name = cat.getName();
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    
}
