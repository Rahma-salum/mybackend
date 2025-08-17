package com.example.medical_shop_backend.repository;

import com.example.medical_shop_backend.models.Order;
import com.example.medical_shop_backend.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsByOrder(Order order);

    Optional<Payment> findByControlNumber(String controlNumber);
}
