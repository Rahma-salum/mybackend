package com.example.medical_shop_backend.api;

import com.example.medical_shop_backend.models.Pharmacist;
import com.example.medical_shop_backend.services.PharmacistService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pharmacists")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PharmacistAPI {

    @Autowired
    private  PharmacistService pharmacistService;

    @PostMapping
    public Pharmacist createPharmacist(@RequestBody Pharmacist pharmacist) {
        return pharmacistService.createPharmacist(pharmacist);
    }

    @GetMapping
    public List<Pharmacist> getAllPharmacists() {
        return pharmacistService.getAllPharmacists();
    }

    @GetMapping("/{id}")
    public Pharmacist getPharmacistById(@PathVariable Long id) {
        return pharmacistService.getPharmacistById(id);
    }

    @PutMapping("/{id}")
    public Pharmacist updatePharmacist(@PathVariable Long id, @RequestBody Pharmacist pharmacist) {
        return pharmacistService.updatePharmacist(id, pharmacist);
    }

    @DeleteMapping("/{id}")
    public void deletePharmacist(@PathVariable Long id) {
        pharmacistService.deletePharmacist(id);
    }
}
