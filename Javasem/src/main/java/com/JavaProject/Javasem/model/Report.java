package com.JavaProject.Javasem.model; // Or com.JavaProject.Javasem.dto

import java.util.List;

public class Report {

    // --- Overall Student Information ---
    private String studentName;
    private String overallGrade;
    private String suggestion;

    // --- Attendance Analysis ---
    private double attendancePercentage;
    private int totalAttendanceDays;
    private int totalClassDays;

    // --- Detailed Subject Performance (Requires a separate SubjectReport class) ---
    private List<SubjectReport> subjectReports;

    // --- Constructor (Required for easy object creation in the service) ---
    // You must also define a SubjectReport class somewhere in your code.
    public Report(String studentName, String overallGrade, double attendancePercentage, int totalAttendanceDays, int totalClassDays, String suggestion, List<SubjectReport> subjectReports) {
        this.studentName = studentName;
        this.overallGrade = overallGrade;
        this.attendancePercentage = attendancePercentage;
        this.totalAttendanceDays = totalAttendanceDays;
        this.totalClassDays = totalClassDays;
        this.suggestion = suggestion;
        this.subjectReports = subjectReports;
    }

    // --- Getters and Setters (REQUIRED BY THYMELEAF) ---

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getOverallGrade() {
        return overallGrade;
    }

    public void setOverallGrade(String overallGrade) {
        this.overallGrade = overallGrade;
    }

    public double getAttendancePercentage() {
        return attendancePercentage;
    }

    public void setAttendancePercentage(double attendancePercentage) {
        this.attendancePercentage = attendancePercentage;
    }

    public int getTotalAttendanceDays() {
        return totalAttendanceDays;
    }

    public void setTotalAttendanceDays(int totalAttendanceDays) {
        this.totalAttendanceDays = totalAttendanceDays;
    }

    public int getTotalClassDays() {
        return totalClassDays;
    }

    public void setTotalClassDays(int totalClassDays) {
        this.totalClassDays = totalClassDays;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public List<SubjectReport> getSubjectReports() {
        return subjectReports;
    }

    public void setSubjectReports(List<SubjectReport> subjectReports) {
        this.subjectReports = subjectReports;
    }
}