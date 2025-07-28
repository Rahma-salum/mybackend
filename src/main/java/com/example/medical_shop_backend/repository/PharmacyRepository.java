package com.example.medical_shop_backend.repository;

import com.example.medical_shop_backend.models.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
}
