package com.JavaProject.Javasem.service;

import com.JavaProject.Javasem.model.Report;
import com.JavaProject.Javasem.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public Optional<Report> getReportById(Long id) {
        return reportRepository.findById(id);
    }

    public void saveReport(Report report) {
        // Auto-generate suggestion if blank
        if (report.getSuggestion() == null || report.getSuggestion().isBlank()) {
            report.setSuggestion(generateSuggestion(report.getMarks(), report.getAttendance()));
        }
        reportRepository.save(report);
    }

    public void deleteReport(Long id) {
        reportRepository.deleteById(id);
    }

    // ✅ Helper: Auto-generate teacher suggestion
    private String generateSuggestion(double marks, double attendance) {
        if (marks >= 85 && attendance >= 90)
            return "Excellent performance! Keep up the great consistency.";
        else if (marks >= 70 && attendance >= 75)
            return "Good progress. Maintain focus and consistency.";
        else if (marks >= 50)
            return "Average performance. Needs more attention on weak areas.";
        else
            return "Below average. Focus on improving concepts and attendance.";
    }

    // ✅ Dashboard calculations
    public double calculateAverageMarks() {
        List<Report> reports = reportRepository.findAll();
        if (reports.isEmpty()) return 0;
        return reports.stream().mapToDouble(Report::getMarks).average().orElse(0);
    }

    public double calculateAverageAttendance() {
        List<Report> reports = reportRepository.findAll();
        if (reports.isEmpty()) return 0;
        return reports.stream().mapToDouble(Report::getAttendance).average().orElse(0);
    }

    public String getOverallPerformance(double avgMarks, double avgAttendance) {
        if (avgMarks >= 85 && avgAttendance >= 90) return "Outstanding 🌟";
        if (avgMarks >= 70 && avgAttendance >= 75) return "Good 👍";
        if (avgMarks >= 50 && avgAttendance >= 60) return "Average ⚙️";
        return "Needs Improvement ⚠️";
    }


    public double calculateAverageMarksForReports(List<Report> reports) {
        if (reports.isEmpty()) return 0;
        return reports.stream().mapToDouble(Report::getMarks).average().orElse(0);
    }

    public double calculateAverageAttendanceForReports(List<Report> reports) {
        if (reports.isEmpty()) return 0;
        return reports.stream().mapToDouble(Report::getAttendance).average().orElse(0);
    }

    public List<Report> getReportsByStudentName(String studentName) {
        return reportRepository.findByStudentName(studentName);
    }


}
