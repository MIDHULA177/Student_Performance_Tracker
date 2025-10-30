package com.JavaProject.Javasem.repository;

import com.JavaProject.Javasem.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByStudentNameIgnoreCase(String studentName);

    List<Report> findByStudentName(String studentName);
}
