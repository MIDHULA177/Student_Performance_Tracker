package com.JavaProject.Javasem.controller;

import com.JavaProject.Javasem.model.Performance;
import com.JavaProject.Javasem.model.User;
import com.JavaProject.Javasem.service.PerformanceService;
import com.JavaProject.Javasem.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    // ✅ Main page — shows all or filtered performance data
    @GetMapping
    public String showPerformancePage(@RequestParam(required = false) String exam,
                                      @RequestParam(required = false) String subject,
                                      @RequestParam(required = false) String grade,
                                      Model model) {

        // --- Controller Sanitization Logic (CRITICAL FIX) ---
        // Converts empty strings ("") or strings with only spaces to null.
        // This ensures the service's custom JPA query handles the optional filters correctly.
        String filterExam = (exam != null && !exam.trim().isEmpty()) ? exam.trim() : null;
        String filterSubject = (subject != null && !subject.trim().isEmpty()) ? subject.trim() : null;
        String filterGrade = (grade != null && !grade.trim().isEmpty()) ? grade.trim() : null;
        // ---------------------------------------------------

        if (filterExam != null || filterSubject != null || filterGrade != null) {
            // Use the efficient filter method with sanitized (potentially null) values
            model.addAttribute("performanceList", performanceService.filterPerformances(filterExam, filterSubject, filterGrade));
        } else {
            // No filters applied, show all
            model.addAttribute("performanceList", performanceService.getAllPerformance());
        }

        model.addAttribute("students", userService.getAllStudents());
        return "performance";
    }

    // ✅ Save all performance records for selected exam & subject
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

    // ✅ Grade calculation helper
    private String calculateGrade(int marks) {
        if (marks >= 90) return "A+";
        if (marks >= 80) return "A";
        if (marks >= 70) return "B+";
        if (marks >= 60) return "B";
        if (marks >= 50) return "C";
        return "F";
    }

    // ✅ Delete a record
    @PostMapping("/delete/{id}")
    public String deletePerformance(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        performanceService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "🗑️ Record deleted successfully!");
        return "redirect:/performance";
    }
}