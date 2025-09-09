package com.example.hospital.Controller;

import com.example.hospital.DAO.SickLeaveDAO;
import com.example.hospital.DTO.SickLeaveFilterDTO;
import com.example.hospital.Entities.SickLeave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/sick-leaves")
public class SickLeaveController {

    private final SickLeaveDAO sickLeaveDAO;

    @Autowired
    public SickLeaveController(SickLeaveDAO sickLeaveDAO) {
        this.sickLeaveDAO = sickLeaveDAO;
    }

    // Статический запрос - страница со всеми больничными листами
    @GetMapping
    public String getAllSickLeaves(Model model) {
        List<SickLeave> sickLeaves = sickLeaveDAO.getAllSickLeaves();
        model.addAttribute("sickLeaves", sickLeaves);
        return "sick-leaves/list";
    }

    // Параметрический запрос - больничные по диагнозу
    @GetMapping("/by-diagnosis")
    public String getSickLeavesByDiagnosis(@RequestParam("diagnosis") String diagnosis, Model model) {
        List<SickLeave> sickLeaves = sickLeaveDAO.getSickLeavesByDiagnosis(diagnosis);
        model.addAttribute("sickLeaves", sickLeaves);
        model.addAttribute("diagnosis", diagnosis);
        return "sick-leaves/list";
    }

    // Динамический запрос - обработка фильтра
    @PostMapping("/filter")
    @ResponseBody
    public List<SickLeave> filterSickLeaves(@RequestBody SickLeaveFilterDTO filter) {
        return sickLeaveDAO.filterSickLeaves(filter);
    }

    // Добавление нового больничного листа
    @PostMapping
    public String addSickLeave(
            @RequestParam("patientId") Integer patientId,
            @RequestParam("doctorId") Integer doctorId,
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate,
            @RequestParam("diagnosis") String diagnosis,
            @RequestParam("status") String status) {

        sickLeaveDAO.addSickLeave(patientId, doctorId, startDate, endDate, diagnosis, status);
        return "redirect:/sick-leaves";
    }
}