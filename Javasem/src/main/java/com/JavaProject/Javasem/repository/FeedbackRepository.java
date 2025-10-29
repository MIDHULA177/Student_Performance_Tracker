package com.JavaProject.Javasem.repository;

import com.JavaProject.Javasem.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    /**
     * Finds all feedback records submitted by a specific student.
     */
    List<Feedback> findByStudentId(Long studentId);

    /**
     * Finds all feedback records for a specific subject.
     */
    List<Feedback> findBySubject(String subject);

    /**
     * Finds all feedback records for a specific subject submitted by a specific student.
     */
    List<Feedback> findByStudentIdAndSubject(Long studentId, String subject);
}