package com.example.medical_shop_backend.api;

import com.example.medical_shop_backend.models.Payment;
import com.example.medical_shop_backend.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentAPI {

    @Autowired
    private  PaymentService paymentService;



    // Generate control number & payment record
    @PostMapping("/generate/{orderId}")
    public ResponseEntity<?> generateControlNumber(@PathVariable Long orderId) {
        return paymentService.generatePayment(orderId);
    }

    @PostMapping("/pay/by-control-number/{controlNumber}")
    public ResponseEntity<?> makePaymentByControlNumber(@PathVariable String controlNumber,
                                                        @RequestParam double payedAmount) {
        return paymentService.processPaymentByControlNumber(controlNumber, payedAmount);
    }


    @GetMapping
    public ResponseEntity<?> getAllPayments(){
        List<Payment> paymentList = paymentService.getAllPayments();

        try {
            if (paymentList.isEmpty()){
                return new ResponseEntity<>("No Payment Found", HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity<>(paymentList, HttpStatus.OK);
            }
        }catch (Exception exception){
            return new ResponseEntity<>("Some Errors Occur", HttpStatus.BAD_REQUEST);
        }
    }
}
