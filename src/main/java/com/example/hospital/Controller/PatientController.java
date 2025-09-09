package com.example.hospital.Controller;

import com.example.hospital.DAO.PatientDAO;
import com.example.hospital.DTO.PatientFilterDTO;
import com.example.hospital.Entities.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientDAO patientDAO;

    @Autowired
    public PatientController(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    // Статический запрос - страница со всеми пациентами
    @GetMapping
    public String getAllPatients(Model model) {
        List<Patient> patients = patientDAO.getAllPatients();
        model.addAttribute("patients", patients);
        return "patients/list";
    }

    // Параметрический запрос - страница с пациентами по диагнозу
    @GetMapping("/by-diagnosis")
    public String getPatientsByDiagnosis(@RequestParam("diagnosis") String diagnosis, Model model) {
        List<Patient> patients = patientDAO.getPatientsByDiagnosis(diagnosis);
        model.addAttribute("patients", patients);
        model.addAttribute("diagnosis", diagnosis);
        return "patients/list";
    }

    // Динамический запрос - обработка фильтра
    @PostMapping("/filter")
    @ResponseBody
    public List<Patient> filterPatients(@RequestBody PatientFilterDTO filter) {
        return patientDAO.filterPatients(filter);
    }
}