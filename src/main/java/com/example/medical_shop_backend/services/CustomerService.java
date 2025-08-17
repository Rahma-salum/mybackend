package com.example.medical_shop_backend.services;

import com.example.medical_shop_backend.models.Customer;
import com.example.medical_shop_backend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        // Check if email already exists
        if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
            throw new RuntimeException("EMAIL_ALREADY_EXISTS");
        }

        return customerRepository.save(customer);
    }
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public Customer updateCustomer(Long id, Customer customer) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CUSTOMER_NOT_FOUND"));

        existingCustomer.setName(customer.getName());
        existingCustomer.setEmail(customer.getEmail());
        existingCustomer.setPhone(customer.getPhone());

        return customerRepository.save(existingCustomer);
    }

    public int countCustomer(){
        return (int) customerRepository.count();
    }
}
