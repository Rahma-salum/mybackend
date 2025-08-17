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
import java.util.List;
import java.util.Optional;

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

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id){
        return orderRepository.findById(id);
    }

    public void deleteOrder(Long id){
        orderRepository.deleteById(id);
    }

    // ✅ Cancel order and restore medicine stock
    @Transactional
    public Order cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if ("Canceled".equalsIgnoreCase(order.getStatus())) {
            throw new RuntimeException("Order is already canceled");
        }

        Medicine medicine = order.getMedicine();

        // Restore medicine stock
        medicine.setQuantity(medicine.getQuantity() + order.getQuantity());
        medicineRepository.save(medicine);

        // Update order status
        order.setStatus("Canceled");
        return orderRepository.save(order);
    }


    // Approve order
    @Transactional
    public Order approveOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID " + orderId));

        if ("Approved".equalsIgnoreCase(order.getStatus())) {
            throw new RuntimeException("Order is already approved");
        }

        if ("Canceled".equalsIgnoreCase(order.getStatus())) {
            throw new RuntimeException("Cannot approve a canceled order");
        }

        // Approve order
        order.setStatus("Approved");
        return orderRepository.save(order);
    }
}
