package com.JavaProject.Javasem.controller;

import com.JavaProject.Javasem.model.Report; // ✅ CORRECTED: Importing from model
import com.JavaProject.Javasem.service.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // Maps to: GET /reports/{studentId}
    @GetMapping("/{studentId}")
    public String generateReport(@PathVariable Long studentId, Model model) {

        Report report = reportService.generateReport(studentId);

        model.addAttribute("report", report);

        return "report";
    }
}