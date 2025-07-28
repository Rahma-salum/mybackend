package com.example.medical_shop_backend.api;

import com.example.medical_shop_backend.models.Order;
import com.example.medical_shop_backend.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Allow frontend calls
public class OrderAPI {
    @Autowired
    private  OrderService orderService;

    // ✅ Get all orders
//    @GetMapping
//    public List<Order> getAllOrders() {
//        return orderService.getAllOrders();
//    }

    // ✅ Get order by ID
//    @GetMapping("/{id}")
//    public Order getOrderById(@PathVariable Long id) {
//        return orderService.getOrderById(id);
//    }

    // ✅ Create new order
    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }



//    // ✅ Delete order
//    @DeleteMapping("/{id}")
//    public void deleteOrder(@PathVariable Long id) {
//        orderService.deleteOrder(id);
//    }
}
