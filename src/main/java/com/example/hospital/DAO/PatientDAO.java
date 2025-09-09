package com.example.hospital.DAO;

import com.example.hospital.DTO.PatientFilterDTO;
import com.example.hospital.Entities.Patient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PatientDAO {
    private final JdbcTemplate jdbcTemplate;

    public PatientDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 1. Статический запрос - список всех пациентов
    // Фиксированный запрос без параметров  Всегда возвращает полный набор данных
    //Нет условий фильтрации
    public List<Patient> getAllPatients() {
        String sql = "SELECT * FROM patients";
        return jdbcTemplate.query(sql, patientRowMapper);
    }

    // 2. Параметрический запрос - пациенты по диагнозу
    // Принимает один параметр фильтрации запрос предопределен и
    //Фиксированная структура запроса  Условие WHERE с конкретным полем
    public List<Patient> getPatientsByDiagnosis(String diagnosis) {
        String sql = "SELECT * FROM patients WHERE diagnosis = ?";
        return jdbcTemplate.query(sql, new Object[]{diagnosis}, patientRowMapper);
    }

    // 3. Динамический запрос - фильтрация пациентов
    // Принимает объект с несколькими параметрами фильтрации
    //Условия WHERE формируются динамически Может комбинировать несколько критериев
    public List<Patient> filterPatients(PatientFilterDTO filter) {
        StringBuilder sql = new StringBuilder("SELECT * FROM patients WHERE 1=1");
        List<Object> params = new java.util.ArrayList<>();

        if (filter.getDiagnosis() != null && !filter.getDiagnosis().isEmpty()) {
            sql.append(" AND diagnosis = ?");
            params.add(filter.getDiagnosis());
        }
        if (filter.getMinAge() != null) {
            sql.append(" AND age >= ?");
            params.add(filter.getMinAge());
        }
        if (filter.getMaxAge() != null) {
            sql.append(" AND age <= ?");
            params.add(filter.getMaxAge());
        }
        if (filter.getWardType() != null && !filter.getWardType().isEmpty()) {
            sql.append(" AND ward_type = ?");
            params.add(filter.getWardType());
        }
        if (filter.getPhysician() != null && !filter.getPhysician().isEmpty()) {
            sql.append(" AND attending_physician = ?");
            params.add(filter.getPhysician());
        }
        if (filter.getAdmissionDateFrom() != null) {
            sql.append(" AND admission_date >= ?");
            params.add(filter.getAdmissionDateFrom());
        }
        if (filter.getAdmissionDateTo() != null) {
            sql.append(" AND admission_date <= ?");
            params.add(filter.getAdmissionDateTo());
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), patientRowMapper);
    }

    private final RowMapper<Patient> patientRowMapper = (rs, rowNum) -> new Patient(
            rs.getLong("id"),
            rs.getString("full_name"),
            rs.getInt("age"),
            rs.getString("diagnosis"),
            rs.getString("ward_number"),
            rs.getString("ward_type"),
            rs.getString("attending_physician"),
            rs.getDate("admission_date") != null ? rs.getDate("admission_date").toLocalDate() : null,
            rs.getDate("discharge_date") != null ? rs.getDate("discharge_date").toLocalDate() : null
    );
}