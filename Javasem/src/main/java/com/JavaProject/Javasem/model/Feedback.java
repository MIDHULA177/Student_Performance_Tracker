package com.JavaProject.Javasem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student_feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link back to the student who provided the feedback
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    // The specific subject the feedback is about
    @Column(nullable = false)
    private String subject;

    // Numeric rating (e.g., 1 to 5)
    @Column(nullable = true) // Optional rating
    private Integer rating;

    // The detailed comment from the student
    @Column(length = 2000, nullable = false)
    private String comment;

    // Record the date the feedback was submitted
    @Column(name = "date_submitted", nullable = false)
    private LocalDate dateSubmitted = LocalDate.now();
}