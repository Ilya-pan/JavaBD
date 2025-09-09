package com.example.hospital.Controller;

import com.example.hospital.DAO.DoctorDAO;
import com.example.hospital.DTO.DoctorFilterDTO;
import com.example.hospital.Entities.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorDAO doctorDAO;

    @Autowired
    public DoctorController(DoctorDAO doctorDAO) {
        this.doctorDAO = doctorDAO;
    }

    // Статический запрос - страница со всеми врачами
    @GetMapping
    public String getAllDoctors(Model model) {
        List<Doctor> doctors = doctorDAO.getAllDoctors();
        model.addAttribute("doctors", doctors);
        return "doctors/list";
    }

    // Параметрический запрос - врачи по специализации
    @GetMapping("/by-specialization")
    public String getDoctorsBySpecialization(@RequestParam("specialization") String specialization, Model model) {
        List<Doctor> doctors = doctorDAO.getDoctorsBySpecialization(specialization);
        model.addAttribute("doctors", doctors);
        model.addAttribute("specialization", specialization);
        return "doctors/list";
    }

    // Динамический запрос - обработка фильтра
    @PostMapping("/filter")
    @ResponseBody
    public List<Doctor> filterDoctors(@RequestBody DoctorFilterDTO filter) {
        return doctorDAO.filterDoctors(filter);
    }

    // Добавление нового врача
    @PostMapping
    public String addDoctor(@ModelAttribute Doctor doctor) {
        doctorDAO.addDoctor(doctor);
        return "redirect:/doctors";
    }

    // Удаление врача
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteDoctor(@PathVariable Long id) {
        boolean deleted = doctorDAO.deleteDoctor(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}