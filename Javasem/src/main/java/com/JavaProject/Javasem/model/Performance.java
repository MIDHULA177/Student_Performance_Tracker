package com.JavaProject.Javasem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Performance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    private String subject;
    private String exam;
    private int marks;
    private String grade;

    // ✅ FIX: Method now returns the actual value of the 'exam' field.
    // This allows your HTML template to continue using 'p.examName'.
    public String getExamName() {
        return this.exam;
    }
}