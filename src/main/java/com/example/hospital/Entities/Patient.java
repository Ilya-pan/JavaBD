package com.example.hospital.Entities;

import java.time.LocalDate;

public class Patient {
    private Long id;
    private String fullName;
    private Integer age;
    private String diagnosis;
    private String wardNumber;
    private String wardType;
    private String attendingPhysician;
    private LocalDate admissionDate;
    private LocalDate dischargeDate;

    public Patient() {}

    public Patient(Long id, String fullName, Integer age, String diagnosis,
                   String wardNumber, String wardType, String attendingPhysician, LocalDate admissionDate, LocalDate dischargeDate) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.diagnosis = diagnosis;
        this.wardNumber = wardNumber;
        this.wardType = wardType;
        this.attendingPhysician = attendingPhysician;
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public Integer getAge() { return age; }
    public String getDiagnosis() { return diagnosis; }
    public String getWardNumber() { return wardNumber; }
    public String getWardType() { return wardType; }
    public String getAttendingPhysician() { return attendingPhysician; }
    public LocalDate getAdmissionDate() { return admissionDate; }
    public LocalDate getDischargeDate() { return dischargeDate; }

    public void setId(Long id) { this.id = id; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setAge(Integer age) { this.age = age; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public void setWardNumber(String wardNumber) { this.wardNumber = wardNumber; }
    public void setWardType(String wardType) { this.wardType = wardType; }
    public void setAttendingPhysician(String attendingPhysician) { this.attendingPhysician = attendingPhysician; }
    public void setAdmissionDate(LocalDate admissionDate) { this.admissionDate = admissionDate; }
    public void setDischargeDate(LocalDate dischargeDate) { this.dischargeDate = dischargeDate; }
}