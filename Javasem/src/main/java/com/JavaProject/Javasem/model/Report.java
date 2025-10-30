package com.JavaProject.Javasem.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentName;
    private String subject;
    private double marks;
    private double attendance;
    private String suggestion;
    private LocalDate dateCreated = LocalDate.now();

    // Getters and Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getStudentName() { return studentName; }

    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getSubject() { return subject; }

    public void setSubject(String subject) { this.subject = subject; }

    public double getMarks() { return marks; }

    public void setMarks(double marks) { this.marks = marks; }

    public double getAttendance() { return attendance; }

    public void setAttendance(double attendance) { this.attendance = attendance; }

    public String getSuggestion() { return suggestion; }

    public void setSuggestion(String suggestion) { this.suggestion = suggestion; }

    public LocalDate getDateCreated() { return dateCreated; }

    public void setDateCreated(LocalDate dateCreated) { this.dateCreated = dateCreated; }
}
