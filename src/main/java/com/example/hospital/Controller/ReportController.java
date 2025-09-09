package com.example.hospital.Controller;

import com.example.hospital.Birt;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReportController {
    @Autowired
    private Birt birt;
    @GetMapping("/report")
    public String report() {
        return "report";
    }
    @GetMapping("/report2")
    public String report2() {
        return "report2";
    }
    @PostMapping("/report")
    public void report(@RequestParam String id, HttpServletResponse response, HttpServletRequest
            request) {
        birt.generatePDF(id, response, request);
    }
    @PostMapping("/report2")
    public void report2(@RequestParam String id, HttpServletResponse response, HttpServletRequest
            request) {
        birt.generatePDF2(id, response, request);
    }
}