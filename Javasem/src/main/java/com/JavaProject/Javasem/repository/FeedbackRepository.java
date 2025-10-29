package com.JavaProject.Javasem.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.JavaProject.Javasem.model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByStudentId(Long studentId);
    List<Feedback> findByFacultyId(Long facultyId);
}