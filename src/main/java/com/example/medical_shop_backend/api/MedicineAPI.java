package com.example.medical_shop_backend.api;

import com.example.medical_shop_backend.dto.DTOMapper;
import com.example.medical_shop_backend.dto.MedicineDTO;
import com.example.medical_shop_backend.models.Medicine;
import com.example.medical_shop_backend.models.Pharmacy;

import com.example.medical_shop_backend.repository.MedicineRepository;
import com.example.medical_shop_backend.repository.PharmacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/medicines")
@CrossOrigin(origins = "*")
public class MedicineAPI {

    @Autowired
    private  MedicineRepository medicineRepository;

    @Autowired
    private  PharmacyRepository pharmacyRepository;


    // Get all medicines
    @GetMapping("/wewe")
    public ResponseEntity<List<MedicineDTO>> getAllMedicinesa() {
        List<Medicine> medicines = medicineRepository.findAll();
        List<MedicineDTO> dtoList = medicines.stream()
                .map(DTOMapper::toMedicineDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    // Search medicines by name (case-insensitive)
    @GetMapping("/search")
    public ResponseEntity<List<MedicineDTO>> searchMedicine(@RequestParam String name) {
        List<Medicine> medicines = medicineRepository.findByNameIgnoreCase(name);
        List<MedicineDTO> dtoList = medicines.stream()
                .map(DTOMapper::toMedicineDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }


    @GetMapping
    public ResponseEntity<?> getAllMedicines() {
        try {
            List<Medicine> medicineList = medicineRepository.findAll();
            if(medicineList.isEmpty()){
                return new ResponseEntity<>("No Medicine Available", HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity<>(medicineList, HttpStatus.OK);
            }
        }catch (Exception exception){
            return new ResponseEntity<>("Some errors occur", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMedicineById(@PathVariable Long id) {
        Optional<Medicine> medicineOptional = medicineRepository.findById(id);
        try {
            if (medicineOptional.isEmpty()){
                return new ResponseEntity<>("No medicine available",HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity<>(medicineOptional,HttpStatus.OK);
            }
        }catch (Exception exception){
            return new ResponseEntity<>("Some errors occur", HttpStatus.BAD_REQUEST);
        }

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
