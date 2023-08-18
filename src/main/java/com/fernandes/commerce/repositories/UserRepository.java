package com.fernandes.commerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fernandes.commerce.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

    User findByEmail(String email);
    
}
