package com.example.medical_shop_backend.dto;

import com.example.medical_shop_backend.models.Medicine;

public class DTOMapper {

    public static MedicineDTO toMedicineDTO(Medicine medicine) {
        MedicineDTO dto = new MedicineDTO();
        dto.setId(medicine.getId());
        dto.setName(medicine.getName());
        dto.setDescription(medicine.getDescription());
        dto.setQuantity(medicine.getQuantity());
        dto.setPrice(medicine.getPrice());
        dto.setCategory(medicine.getCategory());

        if (medicine.getPharmacy() != null) {
            PharmacyDTO pharmacyDTO = new PharmacyDTO();
            pharmacyDTO.setId(medicine.getPharmacy().getId());
            pharmacyDTO.setName(medicine.getPharmacy().getName());
            pharmacyDTO.setLongitude(medicine.getPharmacy().getLongitude());
            pharmacyDTO.setLatitude(medicine.getPharmacy().getLatitude());
            pharmacyDTO.setAddress(medicine.getPharmacy().getAddress());
            pharmacyDTO.setPharmacistPhone(
                    medicine.getPharmacy().getPharmacist() != null
                            ? medicine.getPharmacy().getPharmacist().getPhone()
                            : ""
            );
            dto.setPharmacy(pharmacyDTO);
        }

        return dto;
    }
}
