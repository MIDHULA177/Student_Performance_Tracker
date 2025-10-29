package com.JavaProject.Javasem.repository;

import com.JavaProject.Javasem.model.ReportSnapshot; // Must be a JPA @Entity
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<ReportSnapshot, Long> {

    // Find all historical reports for a specific student
    List<ReportSnapshot> findByStudentId(Long studentId);

    // Find the most recent report for a student
    Optional<ReportSnapshot> findTopByStudentIdOrderByDateGeneratedDesc(Long studentId);
}