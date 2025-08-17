package com.example.medical_shop_backend.api;

import com.example.medical_shop_backend.models.Order;
import com.example.medical_shop_backend.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Allow frontend calls
public class OrderAPI {
    @Autowired
    private  OrderService orderService;

    // ✅ Get all orders
    @GetMapping
    public ResponseEntity<?> getAllOrders() {
//        return orderService.getAllOrders();
        List<Order> orderList = orderService.getAllOrders();
        try {
            if (orderList.isEmpty()){
                return new ResponseEntity<>("No Order Found", HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity<>(orderList,HttpStatus.OK);
            }
        }catch (Exception exception){
            return new ResponseEntity<>("Some errors occur", HttpStatus.BAD_REQUEST);
        }
    }

    // ✅ Get order by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
//        return orderService.getOrderById(id);
        Optional<Order> orderOptional = orderService.getOrderById(id);

        try {
            if (orderOptional.isEmpty()){
                return new ResponseEntity<>("No order Found", HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity<>(orderOptional,HttpStatus.OK);
            }
        }catch (Exception exception){
            return new ResponseEntity<>("Some errors occur", HttpStatus.BAD_REQUEST);
        }
    }

    // ✅ Create new order
    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }



//    // ✅ Delete order
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
//        orderService.deleteOrder(id);
        Optional<Order> optionalOrder = orderService.getOrderById(id);

       try {
           if (optionalOrder.isEmpty()){
               return new ResponseEntity<>("No order Found",HttpStatus.NOT_FOUND);
           }else {
               orderService.deleteOrder(id);
               return new ResponseEntity<>("Order deleted Successfull", HttpStatus.OK);
           }
       }catch (Exception exception){
           return new ResponseEntity<>("Some erorrs occur", HttpStatus.BAD_REQUEST);
       }
    }

    // Cancel order API
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long id) {
        Order canceledOrder = orderService.cancelOrder(id);
        return ResponseEntity.ok(canceledOrder);
    }

    // Approve order API
    @PutMapping("/{id}/approve")
    public ResponseEntity<Order> approveOrder(@PathVariable Long id) {
        Order approvedOrder = orderService.approveOrder(id);
        return ResponseEntity.ok(approvedOrder);
    }
}
