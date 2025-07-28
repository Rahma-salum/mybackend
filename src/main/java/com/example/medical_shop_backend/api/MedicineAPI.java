package com.example.medical_shop_backend.api;

import com.example.medical_shop_backend.models.Medicine;
import com.example.medical_shop_backend.models.Pharmacy;

import com.example.medical_shop_backend.repository.MedicineRepository;
import com.example.medical_shop_backend.repository.PharmacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/medicines")
@CrossOrigin(origins = "*")
public class MedicineAPI {

    @Autowired
    private  MedicineRepository medicineRepository;

    @Autowired
    private  PharmacyRepository pharmacyRepository;


    @GetMapping
    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicine> getMedicineById(@PathVariable Long id) {
        return medicineRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/pharmacy/{pharmacyId}")
    public ResponseEntity<Medicine> createMedicineForPharmacy(
            @PathVariable Long pharmacyId,
            @RequestBody Medicine medicine) {

        Optional<Pharmacy> pharmacyOpt = pharmacyRepository.findById(pharmacyId);
        if (pharmacyOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        medicine.setPharmacy(pharmacyOpt.get());
        Medicine savedMedicine = medicineRepository.save(medicine);
        return ResponseEntity.ok(savedMedicine);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medicine> updateMedicine(@PathVariable Long id, @RequestBody Medicine medicineDetails) {
        Optional<Medicine> medicineOpt = medicineRepository.findById(id);
        if (medicineOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Medicine medicine = medicineOpt.get();

        medicine.setName(medicineDetails.getName());
        medicine.setDescription(medicineDetails.getDescription());
        medicine.setQuantity(medicineDetails.getQuantity());
        medicine.setPrice(medicineDetails.getPrice());
        medicine.setCategory(medicineDetails.getCategory());

        if (medicineDetails.getPharmacy() != null) {
            Long newPharmacyId = medicineDetails.getPharmacy().getId();
            Optional<Pharmacy> pharmacyOpt = pharmacyRepository.findById(newPharmacyId);
            pharmacyOpt.ifPresent(medicine::setPharmacy);
        }

        Medicine updatedMedicine = medicineRepository.save(medicine);
        return ResponseEntity.ok(updatedMedicine);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicine(@PathVariable Long id) {
        if (!medicineRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        medicineRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
