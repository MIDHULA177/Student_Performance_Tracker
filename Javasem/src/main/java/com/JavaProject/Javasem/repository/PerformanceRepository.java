package com.JavaProject.Javasem.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.JavaProject.Javasem.model.Performance;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {

    List<Performance> findByStudentId(Long studentId);

    // ✅ FIX: Custom JPQL Query for Filtering
    // The logic is: (:param IS NULL OR p.field = :param)
    // If the parameter is null, the condition is always TRUE (ignored).
    @Query("SELECT p FROM Performance p WHERE " +
            "(:exam IS NULL OR p.exam = :exam) AND " +
            "(:subject IS NULL OR p.subject = :subject) AND " +
            "(:grade IS NULL OR p.grade = :grade)")
    List<Performance> findFilteredPerformances(
            @Param("exam") String exam,
            @Param("subject") String subject,
            @Param("grade") String grade
    );
}