package com.example.medical_shop_backend.services;

import com.example.medical_shop_backend.models.Pharmacist;
import com.example.medical_shop_backend.models.Pharmacy;
import com.example.medical_shop_backend.repository.PharmacistRepository;
import com.example.medical_shop_backend.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PharmacyServices {

    @Autowired
    private  PharmacyRepository pharmacyRepository;


    @Autowired
    private PharmacistRepository pharmacistRepository;

    public Pharmacy updatePharmacyByPharmacistId(Long pharmacistId, Pharmacy updatedPharmacy) throws Exception {
        Optional<Pharmacist> pharmacistOpt = pharmacistRepository.findById(pharmacistId);

        if (!pharmacistOpt.isPresent()) {
            throw new Exception("Pharmacist not found with ID: " + pharmacistId);
        }

        Pharmacist pharmacist = pharmacistOpt.get();
        Pharmacy pharmacy = pharmacist.getPharmacy();

        if (pharmacy == null) {
            throw new Exception("No pharmacy associated with this pharmacist.");
        }

        // Update fields
        pharmacy.setName(updatedPharmacy.getName());
        pharmacy.setLongitude(updatedPharmacy.getLongitude());
        pharmacy.setLatitude(updatedPharmacy.getLatitude());
        pharmacy.setAddress(updatedPharmacy.getAddress());

        return pharmacyRepository.save(pharmacy);
    }

    public Pharmacy getPharmacyByPharmacistId(Long pharmacistId) throws Exception {
        Optional<Pharmacist> pharmacistOpt = pharmacistRepository.findById(pharmacistId);

        if (!pharmacistOpt.isPresent()) {
            throw new Exception("Pharmacist not found with ID: " + pharmacistId);
        }

        Pharmacist pharmacist = pharmacistOpt.get();
        Pharmacy pharmacy = pharmacist.getPharmacy();

        if (pharmacy == null) {
            throw new Exception("No pharmacy associated with this pharmacist.");
        }

        return pharmacy;
    }


    // ✅ Create new pharmacy
    public Pharmacy createPharmacy(Pharmacy pharmacy) {
        pharmacy.setId(null); // Ensure new
        return pharmacyRepository.save(pharmacy);
    }

    // ✅ Get all pharmacies
    public List<Pharmacy> getAllPharmacies() {
        return pharmacyRepository.findAll();
    }

    // ✅ Get pharmacy by ID
    public Pharmacy getPharmacyById(Long id) {
        return pharmacyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pharmacy not found"));
    }

    // ✅ Update pharmacy
    public Pharmacy updatePharmacy(Long id, Pharmacy updatedPharmacy) {
        Pharmacy pharmacy = getPharmacyById(id);
        pharmacy.setName(updatedPharmacy.getName());
        pharmacy.setLongitude(updatedPharmacy.getLongitude());
        pharmacy.setLatitude(updatedPharmacy.getLatitude());
        pharmacy.setAddress(updatedPharmacy.getAddress());
        return pharmacyRepository.save(pharmacy);
    }

    // ✅ Delete pharmacy
    public void deletePharmacy(Long id) {
        pharmacyRepository.deleteById(id);
    }


    public int countPharmacy() {
        return (int) pharmacyRepository.count();
    }

}
