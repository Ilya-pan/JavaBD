package com.example.hospital.DTO;

import java.time.LocalDate;

public class PatientFilterDTO {
    private String diagnosis;
    private Integer minAge;
    private Integer maxAge;
    private String wardType;
    private String physician;
    private LocalDate admissionDateFrom;
    private LocalDate admissionDateTo;
    // Геттеры и сеттеры
    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public String getWardType() {
        return wardType;
    }

    public void setWardType(String wardType) {
        this.wardType = wardType;
    }
    public String getPhysician() {
        return physician;
    }

    public void setPhysician(String physician) {
        this.physician = physician;
    }
    public LocalDate getAdmissionDateFrom() {
        return admissionDateFrom;
    }

    public void setAdmissionDateFrom(LocalDate admissionDateFrom) {
        this.admissionDateFrom = admissionDateFrom;
    }

    public LocalDate getAdmissionDateTo() {
        return admissionDateTo;
    }

    public void setAdmissionDateTo(LocalDate admissionDateTo) {
        this.admissionDateTo = admissionDateTo;
    }
}