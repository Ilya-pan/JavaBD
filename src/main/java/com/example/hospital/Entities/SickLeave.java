package com.example.hospital.Entities;

import java.time.LocalDate;

public class SickLeave {
    private Long id;
    private String patientName;
    private String doctorName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String diagnosis;
    private String status;

    public SickLeave() {}

    public SickLeave(Long id, String patientName, String doctorName, LocalDate startDate,
                     LocalDate endDate, String diagnosis, String status) {
        this.id = id;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.diagnosis = diagnosis;
        this.status = status;
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public String getPatientName() { return patientName; }
    public String getDoctorName() { return doctorName; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public String getDiagnosis() { return diagnosis; }
    public String getStatus() { return status; }

    public void setId(Long id) { this.id = id; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public void setStatus(String status) { this.status = status; }
}