package com.example.medical_shop_backend.services;

import com.example.medical_shop_backend.models.Pharmacist;
import com.example.medical_shop_backend.models.Pharmacy;
import com.example.medical_shop_backend.repository.PharmacistRepository;
import com.example.medical_shop_backend.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PharmacistService {

    @Autowired
    private  PharmacistRepository pharmacistRepository;

    @Autowired
    private PharmacyRepository pharmacyRepository;

    // ✅ Create Pharmacist
    public Pharmacist createPharmacist(Pharmacist pharmacist) {
        Pharmacy pharmacy = pharmacyRepository.findById(pharmacist.getPharmacy().getId())
                .orElseThrow(() -> new RuntimeException("Pharmacy not found"));

        pharmacist.setId(null); // Ensure new
        pharmacist.setPharmacy(pharmacy);

        return pharmacistRepository.save(pharmacist);
    }

    // ✅ Get All Pharmacists
    public List<Pharmacist> getAllPharmacists() {
        return pharmacistRepository.findAll();
    }

    // ✅ Get Pharmacist by ID
    public Pharmacist getPharmacistById(Long id) {
        return pharmacistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pharmacist not found"));
    }

    // ✅ Update Pharmacist
    public Pharmacist updatePharmacist(Long id, Pharmacist updated) {
        Pharmacist pharmacist = getPharmacistById(id);

        pharmacist.setName(updated.getName());
        pharmacist.setEmail(updated.getEmail());
        pharmacist.setPhone(updated.getPhone());

        if (updated.getPharmacy() != null) {
            Pharmacy pharmacy = pharmacyRepository.findById(updated.getPharmacy().getId())
                    .orElseThrow(() -> new RuntimeException("Pharmacy not found"));
            pharmacist.setPharmacy(pharmacy);
        }

        return pharmacistRepository.save(pharmacist);
    }

    // ✅ Delete Pharmacist
    public void deletePharmacist(Long id) {
        pharmacistRepository.deleteById(id);
    }
}
