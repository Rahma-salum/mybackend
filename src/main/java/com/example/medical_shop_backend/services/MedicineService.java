package com.example.medical_shop_backend.service;

import com.example.medical_shop_backend.models.Medicine;

import java.util.List;
import java.util.Optional;

public interface MedicineService {
    List<Medicine> getAllMedicines();
    Optional<Medicine> getMedicineById(Long id);

    // Create medicine specifying pharmacy ID explicitly
    Medicine createMedicineForPharmacy(Long pharmacyId, Medicine medicine);

    Medicine updateMedicine(Long id, Medicine medicine);
    void deleteMedicine(Long id);


}
