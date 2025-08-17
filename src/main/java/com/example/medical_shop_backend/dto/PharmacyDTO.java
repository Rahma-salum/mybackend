package com.example.medical_shop_backend.dto;

import lombok.Data;

@Data
public class PharmacyDTO {
    private Long id;
    private String name;
    private String longitude;
    private String latitude;
    private String address;
    private String pharmacistPhone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPharmacistPhone() {
        return pharmacistPhone;
    }

    public void setPharmacistPhone(String pharmacistPhone) {
        this.pharmacistPhone = pharmacistPhone;
    }
}
