package com.JavaProject.Javasem.service;

import com.JavaProject.Javasem.model.Report; // ✅ CORRECTED: Importing from model
import com.JavaProject.Javasem.model.Report; // ✅ CORRECTED: Importing from model
import com.JavaProject.Javasem.model.Performance;
import com.JavaProject.Javasem.model.User;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final PerformanceService performanceService;
    private final UserService userService;

    public Report generateReport(Long studentId) {

        // 1. Fetch Core Data
        User student = userService.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found."));

        List<Performance> performanceRecords = performanceService.findByStudentId(studentId);

        // --- Attendance Placeholder ---
        int totalAttendanceDays = 85;
        int totalClassDays = 90;
        double attendancePercentage = totalClassDays == 0 ? 0 : ((double)totalAttendanceDays / totalClassDays) * 100;
        // ------------------------------

        // 2. Analyze Data
        List<Report> subjectReports = analyzePerformance(performanceRecords);
        String overallGrade = calculateOverallGrade(performanceRecords);
        String suggestion = generateSuggestion(overallGrade, attendancePercentage, subjectReports);

        // 3. Construct the Final Report
        return new Report(
                student.getUsername(),
                overallGrade,
                attendancePercentage,
                totalAttendanceDays,
                totalClassDays,
                suggestion,
                subjectReports
        );
    }

    // --- Helper Methods ---

    private List<Report> analyzePerformance(List<Performance> records) {
        return records.stream()
                .map(p -> new Report(p.getSubject(), p.getExam(), p.getMarks(), p.getGrade()))
                .collect(Collectors.toList());
    }

    private String calculateOverallGrade(List<Performance> records) {
        if (records.isEmpty()) return "N/A";
        double avg = records.stream().mapToInt(Performance::getMarks).average().orElse(0.0);

        if (avg >= 90) return "A+";
        if (avg >= 80) return "A";
        if (avg >= 70) return "B+";
        if (avg >= 60) return "B";
        return "F";
    }

    private String generateSuggestion(String overallGrade, double attendancePercent, List<Report> subjectReports) {
        StringBuilder sb = new StringBuilder();

        if (attendancePercent < 80) {
            sb.append("Attendance is critically low. Focus on attending all classes immediately. ");
        } else if (overallGrade.equals("A+") || overallGrade.equals("A")) {
            sb.append("Outstanding performance. Continue excelling! ");
        }

        subjectReports.stream()
                .min((r1, r2) -> Integer.compare(r1.getMarks(), r2.getMarks()))
                .filter(weakest -> weakest.getMarks() < 60)
                .ifPresent(weakest -> sb.append("Improvement is needed in **")
                        .append(weakest.getSubject())
                        .append("** where the score was ")
                        .append(weakest.getMarks())
                        .append("%."));

        return sb.length() > 0 ? sb.toString().trim() : "Performance is satisfactory. Maintain consistency.";
    }
}