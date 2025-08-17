package com.example.medical_shop_backend.api;

import com.example.medical_shop_backend.models.Customer;

import com.example.medical_shop_backend.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerAPI {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        try {
            Customer savedCustomer = customerService.createCustomer(customer);
            return ResponseEntity.ok(savedCustomer);
        } catch (RuntimeException e) {
            if ("EMAIL_ALREADY_EXISTS".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "Customer deleted successfully";
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        try {
            Customer updatedCustomer = customerService.updateCustomer(id, customer);
            return ResponseEntity.ok(updatedCustomer);
        } catch (RuntimeException e) {
            if ("CUSTOMER_NOT_FOUND".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }

    @GetMapping("/count")
    public int getTotalCustomer(){
        return customerService.countCustomer();
    }
}
