package com.JavaProject.Javasem.service;

import com.JavaProject.Javasem.model.Report; // Assuming you placed Report.java here
import com.JavaProject.Javasem.model.Performance;
import com.JavaProject.Javasem.model.User; // Assuming User is your student entity
// CRITICAL: You will need a way to get student and attendance data
// import com.JavaProject.Javasem.model.Attendance;
// import com.JavaProject.Javasem.dto.SubjectReport; // The helper class for subject rows

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    // Inject necessary data services
    private final PerformanceService performanceService;
    private final UserService userService;
    // CRITICAL: You MUST have an AttendanceService to fetch attendance data
    // private final AttendanceService attendanceService;

    /**
     * Generates a comprehensive analytical report for a specific student.
     * * @param studentId The ID of the student to report on.
     * @return A fully populated Report DTO.
     */
    public Report generateReport(Long studentId) {

        // 1. Fetch Core Data (Error handling is simplified for demonstration)
        User student = userService.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found."));

        List<Performance> performanceRecords = performanceService.findByStudentId(studentId);

        // CRITICAL: Implement actual Attendance data fetching here
        // List<Attendance> attendanceRecords = attendanceService.findByStudentId(studentId);

        // --- Placeholder Data for Attendance (Replace with actual logic) ---
        int totalAttendanceDays = 85;
        int totalClassDays = 90;
        double attendancePercentage = ((double)totalAttendanceDays / totalClassDays) * 100;
        // ------------------------------------------------------------------

        // 2. Analyze Performance Data
        List<SubjectReport> subjectReports = analyzePerformance(performanceRecords);
        String overallGrade = calculateOverallGrade(performanceRecords);
        String suggestion = generateSuggestion(overallGrade, attendancePercentage, subjectReports);

        // 3. Construct and Return the Final Report Object
        return new Report(
                student.getUsername(), // Get student name from User entity
                overallGrade,
                attendancePercentage,
                totalAttendanceDays,
                totalClassDays,
                suggestion,
                subjectReports
        );
    }

    // --- PRIVATE ANALYSIS METHODS ---

    /**
     * Aggregates performance records into a List of SubjectReport DTOs.
     */
    private List<SubjectReport> analyzePerformance(List<Performance> records) {
        // In a real application, you might average grades per subject here.
        // For simplicity, we return a flat list mapping Performance to SubjectReport.
        return records.stream()
                .map(p -> new SubjectReport(p.getSubject(), p.getExam(), p.getMarks(), p.getGrade()))
                .collect(Collectors.toList());
    }

    /**
     * Calculates the overall average grade based on all performance records.
     */
    private String calculateOverallGrade(List<Performance> records) {
        if (records.isEmpty()) return "N/A";

        double totalMarks = records.stream()
                .mapToInt(Performance::getMarks)
                .average()
                .orElse(0.0);

        // Use the same grading scale as your controller
        if (totalMarks >= 90) return "A+";
        if (totalMarks >= 80) return "A";
        if (totalMarks >= 70) return "B+";
        if (totalMarks >= 60) return "B";
        if (totalMarks >= 50) return "C";
        return "F";
    }

    /**
     * Generates a personalized suggestion based on key metrics.
     */
    private String generateSuggestion(String overallGrade, double attendancePercent, List<SubjectReport> subjectReports) {
        StringBuilder sb = new StringBuilder();

        // 1. Attendance Check
        if (attendancePercent < 80) {
            sb.append("ATTENTION REQUIRED: Your attendance is critically low (")
                    .append(String.format("%.1f%%", attendancePercent))
                    .append("). Improving attendance is the first priority.\n");
        } else if (attendancePercent < 90) {
            sb.append("Your attendance is satisfactory, but maintaining 90%+ is recommended.\n");
        } else {
            sb.append("Excellent attendance record. Keep it up!\n");
        }

        // 2. Grade Check
        if (overallGrade.equals("F") || overallGrade.equals("C")) {
            sb.append("ACADEMIC CONCERN: Immediate action is needed to improve overall scores.\n");
        } else if (overallGrade.equals("A+") || overallGrade.equals("A")) {
            sb.append("Outstanding academic performance. Continue challenging yourself.\n");
        }

        // 3. Subject-Specific Check (Identify weakest subject)
        SubjectReport weakest = subjectReports.stream()
                .min((r1, r2) -> Integer.compare(r1.getMarks(), r2.getMarks()))
                .orElse(null);

        if (weakest != null && weakest.getMarks() < 60) {
            sb.append("Focus extra study time on **")
                    .append(weakest.getSubject())
                    .append("** where your last score was ")
                    .append(weakest.getMarks())
                    .append("%.\n");
        }

        return sb.toString().trim();
    }
}