package com.example.hospital.DAO;

import com.example.hospital.DTO.DoctorFilterDTO;
import com.example.hospital.Entities.Doctor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DoctorDAO {
    private final JdbcTemplate jdbcTemplate;

    public DoctorDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Doctor> getAllDoctors() {
        String sql = "SELECT * FROM get_all_doctors()";
        return jdbcTemplate.query(sql, doctorRowMapper);
    }

    // 2. Параметрический запрос - врачи по специализации
    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        String sql = "SELECT * FROM get_doctors_by_specialization(?)";
        return jdbcTemplate.query(sql, new Object[]{specialization}, doctorRowMapper);
    }

    // 3. Динамический запрос - фильтрация врачей
    public List<Doctor> filterDoctors(DoctorFilterDTO filter) {
        String sql = "SELECT * FROM filter_doctors(?, ?, ?)";
        return jdbcTemplate.query(
                sql,
                new Object[]{
                        filter.getFullName(),
                        filter.getSpecialization(),
                        filter.getLicenseNumber()
                },
                doctorRowMapper
        );
    }

    // 4. Добавление нового врача
    public void addDoctor(Doctor doctor) {
        jdbcTemplate.update(
                "CALL add_doctor(?, ?, ?)",
                doctor.getFullName(),
                doctor.getSpecialization(),
                doctor.getLicenseNumber()
        );
    }

    // Удаление врача по ID
    public boolean deleteDoctor(Long id) {
        int affectedRows = jdbcTemplate.update(
                "CALL delete_doctor(?)",
                id
        );
        return affectedRows > 0;
    }

    private final RowMapper<Doctor> doctorRowMapper = (rs, rowNum) -> new Doctor(
            rs.getLong("r_id"),
            rs.getString("r_full_name"),
            rs.getString("r_specialization"),
            rs.getString("r_license_number")
    );
}