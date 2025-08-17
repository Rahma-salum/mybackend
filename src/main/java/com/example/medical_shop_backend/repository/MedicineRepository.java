package com.example.medical_shop_backend.repository;

import com.example.medical_shop_backend.models.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine,Long> {

    List<Medicine> findByNameIgnoreCase(String name);
}
