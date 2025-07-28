package com.example.medical_shop_backend.services;

import com.example.medical_shop_backend.models.Medicine;
import com.example.medical_shop_backend.models.Order;
import com.example.medical_shop_backend.repository.CustomerRepository;
import com.example.medical_shop_backend.repository.MedicineRepository;
import com.example.medical_shop_backend.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private  OrderRepository orderRepository;

    @Autowired
    private  CustomerRepository customerRepository;

    @Autowired
    private  MedicineRepository medicineRepository;

    @Transactional
    public Order createOrder(Order order) {
        // Fetch customer
        var customer = customerRepository.findById(order.getCustomer().getId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        order.setCustomer(customer);

        // Fetch medicine
        Medicine medicine = medicineRepository.findById(order.getMedicine().getId())
                .orElseThrow(() -> new RuntimeException("Medicine not found"));

        int requestedQty = order.getQuantity();
        int availableQty = medicine.getQuantity();

        if (availableQty <= 0) {
            throw new RuntimeException("No " + medicine.getName() + " available in stock.");
        }

        if (requestedQty > availableQty) {
            throw new RuntimeException("Cannot order more than available stock. Available: " + availableQty);
        }

        // Update medicine quantity
        int newQty = availableQty - requestedQty;
        medicine.setQuantity(newQty);
        medicineRepository.save(medicine);

        // Set order fields
        order.setMedicine(medicine);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(order.getStatus() != null ? order.getStatus() : "Pending");
        order.setTotalPrice(medicine.getPrice() * requestedQty);

        return orderRepository.save(order);
    }
}
