package com.JavaProject.Javasem.model; // Or com.JavaProject.Javasem.dto

import java.util.List;

public class Report {

    private String subject;
    private String exam;
    private int marks;
    private String grade;

    public Report(String subject, String exam, int marks, String grade) {
        this.subject = subject;
        this.exam = exam;
        this.marks = marks;
        this.grade = grade;
    }

    public Report(String username, String overallGrade, double attendancePercentage, int totalAttendanceDays, int totalClassDays, String suggestion, List<Report> subjectReports) {
    }

    // --- Getters and Setters (Required) ---
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getExam() { return exam; }
    public void setExam(String exam) { this.exam = exam; }
    public int getMarks() { return marks; }
    public void setMarks(int marks) { this.marks = marks; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
}
