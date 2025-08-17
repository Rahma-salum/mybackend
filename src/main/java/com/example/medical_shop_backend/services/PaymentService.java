package com.example.medical_shop_backend.services;

import com.example.medical_shop_backend.models.Order;
import com.example.medical_shop_backend.models.Payment;
import com.example.medical_shop_backend.repository.OrderRepository;
import com.example.medical_shop_backend.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderRepository orderRepository;



    public ResponseEntity<?> generatePayment(Long orderId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Order not found");
        }

        Order order = orderOpt.get();

        if (paymentRepository.existsByOrder(order)) {
            return ResponseEntity.badRequest().body("Payment already generated for this order");
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotalPrice());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setControlNumber(generateControlNumber());
        payment.setPayedAmount("0");
        payment.setRemainingBalance(order.getTotalPrice());
        payment.setStatus("Incomplete");

        Payment saved = paymentRepository.save(payment);
        return ResponseEntity.ok(saved);
    }

    public ResponseEntity<?> processPaymentByControlNumber(String controlNumber, double payedAmount) {
        Optional<Payment> paymentOpt = paymentRepository.findByControlNumber(controlNumber);

        if (paymentOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid control number.");
        }

        Payment payment = paymentOpt.get();
        double currentPaid = Double.parseDouble(payment.getPayedAmount());
        double totalAmount = payment.getAmount();

        if (payedAmount <= 0) {
            return ResponseEntity.badRequest().body("Payment amount must be greater than 0.");
        }

        if (currentPaid + payedAmount > totalAmount) {
            return ResponseEntity.badRequest()
                    .body("This control number is for " + totalAmount + ". You cannot pay more.");
        }

        double updatedPaid = currentPaid + payedAmount;
        double remaining = totalAmount - updatedPaid;

        payment.setPayedAmount(String.valueOf(updatedPaid));
        payment.setRemainingBalance(remaining);

        if (updatedPaid == totalAmount) {
            payment.setStatus("Complete");
            paymentRepository.save(payment);
            return ResponseEntity.ok("Payment complete. Thank you!");
        } else {
            payment.setStatus("Incomplete");
            paymentRepository.save(payment);
            return ResponseEntity.ok("You have paid " + updatedPaid + ". Remaining balance: " + remaining);
        }
    }

    private String generateControlNumber() {
        return "CTRL" + (100000 + new Random().nextInt(900000));
    }


    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

}