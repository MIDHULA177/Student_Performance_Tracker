package com.JavaProject.Javasem.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "report_history")
public class ReportSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link back to the student (assuming User is your student entity)
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @Column(name = "overall_grade")
    private String overallGrade;

    @Column(name = "attendance_percent")
    private Double attendancePercentage;

    @Column(length = 2000) // Large column for the full text suggestion
    private String suggestion;

    @Column(name = "date_generated")
    private LocalDate dateGenerated = LocalDate.now();

    // The subject details (List<SubjectReport>) would typically be stored
    // in a separate JSON column or in another linked table for complex data.

    // --- Constructor, Getters, and Setters must be added here ---
    // ...
}