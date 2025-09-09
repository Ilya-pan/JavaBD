package com.example.hospital.DAO;

import com.example.hospital.DTO.SickLeaveFilterDTO;
import com.example.hospital.Entities.SickLeave;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class SickLeaveDAO {
    private final JdbcTemplate jdbcTemplate;

    public SickLeaveDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 1. Статический запрос - список всех больничных листов
    public List<SickLeave> getAllSickLeaves() {
        String sql = "SELECT * FROM get_all_sick_leaves()";
        return jdbcTemplate.query(sql, sickLeaveRowMapper);
    }

    // 2. Параметрический запрос - больничные по диагнозу
    public List<SickLeave> getSickLeavesByDiagnosis(String diagnosis) {
        String sql = "SELECT * FROM get_sick_leaves_by_diagnosis(?)";
        return jdbcTemplate.query(sql, new Object[]{diagnosis}, sickLeaveRowMapper);
    }

    // 3. Динамический запрос - фильтрация больничных листов
    public List<SickLeave> filterSickLeaves(SickLeaveFilterDTO filter) {
        String sql = "SELECT * FROM filter_sick_leaves(?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.query(
                sql,
                new Object[]{
                        filter.getPatientName(),
                        filter.getDoctorName(),
                        filter.getDiagnosis(),
                        filter.getStatus(),
                        filter.getStartDateFrom(),
                        filter.getStartDateTo()
                },
                sickLeaveRowMapper
        );
    }

    // 4. Добавление нового больничного листа
    public void addSickLeave(Integer patientId, Integer doctorId, LocalDate startDate,
                             LocalDate endDate, String diagnosis, String status) {
        jdbcTemplate.update(
                "CALL add_sick_leave(?, ?, ?, ?, ?, ?)",
                patientId,
                doctorId,
                startDate,
                endDate,
                diagnosis,
                status
        );
    }

    private final RowMapper<SickLeave> sickLeaveRowMapper = (rs, rowNum) -> new SickLeave(
            rs.getLong("r_id"),
            rs.getString("r_patient_name"),
            rs.getString("r_doctor_name"),
            rs.getDate("r_start_date").toLocalDate(),
            rs.getDate("r_end_date") != null ? rs.getDate("r_end_date").toLocalDate() : null,
            rs.getString("r_diagnosis"),
            rs.getString("r_status")
    );
}