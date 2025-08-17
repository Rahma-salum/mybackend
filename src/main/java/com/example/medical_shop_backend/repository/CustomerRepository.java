package com.example.medical_shop_backend.repository;

import com.example.medical_shop_backend.models.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Object> findByEmail(String email);
}
