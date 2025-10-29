package com.JavaProject.Javasem.controller;

import com.JavaProject.Javasem.dto.Report; // Adjust import path
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

    // Endpoint to generate and show a report for a specific student ID
    @GetMapping("/{studentId}")
    public String generateReport(@PathVariable Long studentId, Model model) {

        Report report = reportService.generateReport(studentId);

        model.addAttribute("report", report);

        // Returns the report.html template
        return "report";
    }

    // You might also add a default endpoint to show a list of all student IDs to select from
}