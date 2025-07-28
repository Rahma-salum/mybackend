package com.example.medical_shop_backend.api;

import com.example.medical_shop_backend.models.Pharmacy;

import com.example.medical_shop_backend.services.PharmacyServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pharmacies")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PharmacyAPI {

    @Autowired
    private PharmacyServices pharmacyService;

    @PostMapping
    public Pharmacy createPharmacy(@RequestBody Pharmacy pharmacy) {
        return pharmacyService.createPharmacy(pharmacy);
    }

    @GetMapping
    public List<Pharmacy> getAllPharmacies() {
        return pharmacyService.getAllPharmacies();
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
}
