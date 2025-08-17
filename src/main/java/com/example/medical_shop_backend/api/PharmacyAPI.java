package com.example.medical_shop_backend.api;

import com.example.medical_shop_backend.models.Pharmacy;

import com.example.medical_shop_backend.services.PharmacyServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pharmacies")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PharmacyAPI {

    @Autowired
    private PharmacyServices pharmacyService;


    @PutMapping("/update-by-pharmacist/{pharmacistId}")
    public ResponseEntity<?> updatePharmacyByPharmacist(
            @PathVariable Long pharmacistId,
            @RequestBody Pharmacy updatedPharmacy
    ) {
        try {
            Pharmacy pharmacy = pharmacyService.updatePharmacyByPharmacistId(pharmacistId, updatedPharmacy);
            return ResponseEntity.ok(pharmacy);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/by-pharmacist/{pharmacistId}")
    public ResponseEntity<?> getPharmacyByPharmacist(@PathVariable Long pharmacistId) {
        try {
            Pharmacy pharmacy = pharmacyService.getPharmacyByPharmacistId(pharmacistId);
            return ResponseEntity.ok(pharmacy);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public Pharmacy createPharmacy(@RequestBody Pharmacy pharmacy) {
        return pharmacyService.createPharmacy(pharmacy);
    }

    @GetMapping
    public ResponseEntity<?> getAllPharmacies() {
        List<Pharmacy> pharmacyList = pharmacyService.getAllPharmacies();
        try {
            if (pharmacyList.isEmpty()){
                return new ResponseEntity<>("No Pharmacy Found", HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity<>(pharmacyList,HttpStatus.OK);
            }
        }catch (Exception exception){
            return new ResponseEntity<>("Some Error Occurs", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public Pharmacy PharmacyById(@PathVariable Long id) {
        return pharmacyService.getPharmacyById(id);
    }

    @PutMapping("/{id}")
    public Pharmacy updatePharmacy(@PathVariable Long id, @RequestBody Pharmacy pharmacy) {
        return pharmacyService.updatePharmacy(id, pharmacy);
    }

    @DeleteMapping("/{id}")
    public void deletePharmacy(@PathVariable Long id) {
        pharmacyService.deletePharmacy(id);
    }

    @GetMapping("/count")
    public int getTotalPharmacy() {
        return pharmacyService.countPharmacy();
    }
}
