package com.example.hospital.Entities;

public class Doctor {
    private Long id;
    private String fullName;
    private String specialization;
    private String licenseNumber;

    public Doctor() {}

    public Doctor(Long id, String fullName, String specialization, String licenseNumber) {
        this.id = id;
        this.fullName = fullName;
        this.specialization = specialization;
        this.licenseNumber = licenseNumber;
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getSpecialization() { return specialization; }
    public String getLicenseNumber() { return licenseNumber; }

    public void setId(Long id) { this.id = id; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
}