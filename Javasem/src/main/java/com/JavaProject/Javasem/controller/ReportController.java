package com.JavaProject.Javasem.controller;

import com.JavaProject.Javasem.model.Report;
import com.JavaProject.Javasem.model.User;
import com.JavaProject.Javasem.repository.ReportRepository;
import com.JavaProject.Javasem.repository.UserRepository;
import com.JavaProject.Javasem.service.ReportService;
import com.JavaProject.Javasem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReportRepository reportRepository;

    /**
     * ✅ Main dashboard — with optional filtering by student name.
     */
    @GetMapping
    public String showReportDashboard(@RequestParam(required = false) String studentName, Model model) {

        List<Report> reports;

        // ✅ Filter if student selected
        if (studentName != null && !studentName.isEmpty()) {
            reports = reportService.getReportsByStudentName(studentName);
            model.addAttribute("selectedStudent", studentName);
        } else {
            reports = reportService.getAllReports();
        }

        // ✅ Fetch users (for dropdown)
        List<User> users = userService.findAll();

        // ✅ Chart Data
        List<String> studentNames = reports.stream()
                .map(Report::getStudentName)
                .distinct()
                .toList();

        List<Double> marksList = studentNames.stream()
                .map(name -> reports.stream()
                        .filter(r -> r.getStudentName().equalsIgnoreCase(name))
                        .mapToDouble(Report::getMarks)
                        .average()
                        .orElse(0))
                .toList();

        List<Double> attendanceList = studentNames.stream()
                .map(name -> reports.stream()
                        .filter(r -> r.getStudentName().equalsIgnoreCase(name))
                        .mapToDouble(Report::getAttendance)
                        .average()
                        .orElse(0))
                .toList();

        // ✅ Stats & Dashboard Summary
        double avgMarks = reports.isEmpty() ? 0 : reports.stream().mapToDouble(Report::getMarks).average().orElse(0);
        double avgAttendance = reports.isEmpty() ? 0 : reports.stream().mapToDouble(Report::getAttendance).average().orElse(0);
        String overall = reportService.getOverallPerformance(avgMarks, avgAttendance);

        // ✅ Add to model
        model.addAttribute("reportList", reports);
        model.addAttribute("users", users);
        model.addAttribute("subjectsList", List.of("Math", "Science", "English", "AI", "Data Structures"));
        model.addAttribute("newReport", new Report());
        model.addAttribute("averageMarks", avgMarks);
        model.addAttribute("averageAttendance", avgAttendance);
        model.addAttribute("overallPerformance", overall);
        model.addAttribute("studentNames", studentNames);
        model.addAttribute("marksList", marksList);
        model.addAttribute("attendanceList", attendanceList);

        return "report";
    }

    /**
     * ✅ Save new report
     */
    @PostMapping("/submit")
    public String submitReport(@ModelAttribute Report report) {
        reportService.saveReport(report);
        return "redirect:/report";
    }

    /**
     * ✅ Edit existing report
     */
    @GetMapping("/edit/{id}")
    public String editReport(@PathVariable Long id, Model model) {
        Optional<Report> report = reportService.getReportById(id);
        if (report.isEmpty()) return "redirect:/report";

        model.addAttribute("report", report.get());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("subjectsList", List.of("Math", "Science", "English", "AI", "Data Structures"));
        return "report_edit";
    }

    /**
     * ✅ Update report
     */
    @PostMapping("/update/{id}")
    public String updateReport(@PathVariable Long id, @ModelAttribute Report updated) {
        Optional<Report> existing = reportService.getReportById(id);
        if (existing.isPresent()) {
            Report r = existing.get();
            r.setStudentName(updated.getStudentName());
            r.setSubject(updated.getSubject());
            r.setMarks(updated.getMarks());
            r.setAttendance(updated.getAttendance());
            r.setSuggestion(updated.getSuggestion());
            reportService.saveReport(r);
        }
        return "redirect:/report";
    }

    /**
     * ✅ Delete report
     */
    @PostMapping("/delete/{id}")
    public String deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return "redirect:/report";
    }
}
