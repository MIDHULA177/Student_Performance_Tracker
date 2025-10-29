package com.JavaProject.Javasem.repository;

import com.JavaProject.Javasem.model.ReportSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// This repository is for the PERSISTENT historical report, not the transient DTO.
public interface ReportRepository extends JpaRepository<ReportSnapshot, Long> {

    // Custom query to find all past reports for a student
    List<ReportSnapshot> findByStudentId(Long studentId);
}