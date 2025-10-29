package com.JavaProject.Javasem.controller;

import com.JavaProject.Javasem.model.Performance;
import com.JavaProject.Javasem.model.User;
import com.JavaProject.Javasem.service.PerformanceService;
import com.JavaProject.Javasem.service.UserService;
import jakarta.servlet.http.HttpServletResponse; // Import for Export
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/performance")
public class PerformanceController {

    private final PerformanceService performanceService;
    private final UserService userService;

    public PerformanceController(PerformanceService performanceService, UserService userService) {
        this.performanceService = performanceService;
        this.userService = userService;
    }

    // --- 1. Main Page (Filtering and Listing) ---
    @GetMapping
    public String showPerformancePage(@RequestParam(required = false) String exam,
                                      @RequestParam(required = false) String subject,
                                      @RequestParam(required = false) String grade,
                                      Model model) {

        // Sanitize parameters: convert empty strings to null for efficient JPA querying
        String filterExam = (exam != null && !exam.trim().isEmpty()) ? exam.trim() : null;
        String filterSubject = (subject != null && !subject.trim().isEmpty()) ? subject.trim() : null;
        String filterGrade = (grade != null && !grade.trim().isEmpty()) ? grade.trim() : null;

        if (filterExam != null || filterSubject != null || filterGrade != null) {
            model.addAttribute("performanceList", performanceService.filterPerformances(filterExam, filterSubject, filterGrade));
        } else {
            model.addAttribute("performanceList", performanceService.getAllPerformance());
        }

        model.addAttribute("students", userService.getAllStudents());
        return "performance";
    }

    // --- 2. Save All Records (Batch Submission) ---
    @PostMapping("/saveAll")
    public String saveAllPerformances(@RequestParam String subject,
                                      @RequestParam String exam,
                                      @RequestParam List<Long> studentIds,
                                      @RequestParam List<Integer> marks,
                                      RedirectAttributes redirectAttributes) {

        List<Performance> performances = new ArrayList<>();

        for (int i = 0; i < studentIds.size(); i++) {
            User student = userService.findById(studentIds.get(i)).orElse(null);
            if (student != null) {
                Performance p = new Performance();
                p.setStudent(student);
                p.setSubject(subject);
                p.setExam(exam);
                p.setMarks(marks.get(i));
                p.setGrade(calculateGrade(marks.get(i)));
                performances.add(p);
            }
        }

        performanceService.saveAll(performances);
        redirectAttributes.addFlashAttribute("successMessage", "✅ Performance records added successfully!");
        return "redirect:/performance";
    }

    // --- 3. Update Single Record (FIX for HTML's Update Form) ---
    // The HTML form POSTs to /performance/update
    @PostMapping("/update")
    public String updatePerformance(@ModelAttribute Performance performance,
                                    RedirectAttributes redirectAttributes) {
        try {
            // The @ModelAttribute automatically binds the necessary fields (id, subject, marks, grade, exam)
            performanceService.updatePerformance(performance);
            redirectAttributes.addFlashAttribute("successMessage", "✅ Performance record updated successfully!");
        } catch (RuntimeException e) {
            // Catches exceptions thrown by the service if the record wasn't found
            redirectAttributes.addFlashAttribute("errorMessage", "❌ Error updating record: " + e.getMessage());
        }
        return "redirect:/performance";
    }

    // --- 4. Export to CSV (FIX for Whitelabel Error Page 404) ---
    // The HTML link points to /performance/export, which is a GET request.
    @GetMapping("/export")
    public void exportToCsv(HttpServletResponse response) {
        try {
            // Set File Headers for CSV Download
            response.setContentType("text/csv");
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=performance_data.csv";
            response.setHeader(headerKey, headerValue);

            // Call Service to write data directly to the response stream
            performanceService.exportAllRecords(response.getWriter());

        } catch (IOException e) {
            // Log the exception and prevent the Whitelabel error for the client
            e.printStackTrace();
            // In a production system, you might redirect to an error view here.
        }
    }

    // --- 5. Delete a record ---
    @PostMapping("/delete/{id}")
    public String deletePerformance(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        performanceService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "🗑️ Record deleted successfully!");
        return "redirect:/performance";
    }

    

    // --- 6. Grade calculation helper ---
    private String calculateGrade(int marks) {
        if (marks >= 90) return "A+";
        if (marks >= 80) return "A";
        if (marks >= 70) return "B+";
        if (marks >= 60) return "B";
        if (marks >= 50) return "C";
        return "F";
    }
}