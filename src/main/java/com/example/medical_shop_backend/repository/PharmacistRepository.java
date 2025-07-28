package com.example.medical_shop_backend.repository;

import com.example.medical_shop_backend.models.Pharmacist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacistRepository extends JpaRepository<Pharmacist,Long> {
}
