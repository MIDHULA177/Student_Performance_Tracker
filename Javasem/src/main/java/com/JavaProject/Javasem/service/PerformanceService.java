package com.JavaProject.Javasem.service;

import com.JavaProject.Javasem.model.Performance;
import com.JavaProject.Javasem.repository.PerformanceRepository;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@Service
public class PerformanceService {

    private final PerformanceRepository repository;

    public PerformanceService(PerformanceRepository repository) {
        this.repository = repository;
    }

    public List<Performance> getAllPerformance() {
        return repository.findAll();
    }

    public void savePerformance(Performance performance) {
        repository.save(performance);
    }

    public void saveAll(List<Performance> performances) {
        repository.saveAll(performances);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<Performance> filterPerformances(String exam, String subject, String grade) {
        return repository.findFilteredPerformances(exam, subject, grade);
    }

    // --- NEW / CORRECTED REPORTING METHODS ---

    /**
     * Used by ReportService to generate analytical reports.
     * Assumes a method exists in the PerformanceRepository to query by student ID.
     */
    public List<Performance> findByStudentId(Long studentId) {
        // ✅ CRITICAL: Implement the actual repository call here.
        return repository.findByStudentId(studentId);
    }

    /**
     * Logic for updating an existing record based on data from the HTML form.
     */
    public void updatePerformance(Performance performanceDetails) {
        // Ensure the record exists before updating
        Optional<Performance> existingRecord = repository.findById(performanceDetails.getId());

        if (existingRecord.isPresent()) {
            Performance record = existingRecord.get();

            // Note: The student relationship should not change during an update.
            record.setSubject(performanceDetails.getSubject());
            record.setMarks(performanceDetails.getMarks());
            record.setGrade(performanceDetails.getGrade());
            record.setExam(performanceDetails.getExam());

            repository.save(record);
        } else {
            // Throw a runtime exception if the record is not found
            throw new RuntimeException("Performance record not found for ID: " + performanceDetails.getId());
        }
    }

    /**
     * Writes all performance records directly to a CSV output stream for export.
     */
    public void exportAllRecords(PrintWriter writer) throws IOException {
        List<Performance> records = repository.findAll();

        // Write CSV Header Row
        writer.println("ID,StudentName,Subject,Marks,Grade,Exam");

        // Write Data Rows
        for (Performance record : records) {
            // ✅ Ensure record.getStudent() is not null and has a getUsername() method.
            writer.printf("%d,%s,%s,%d,%s,%s\n",
                    record.getId(),
                    record.getStudent().getUsername(),
                    record.getSubject(),
                    record.getMarks(),
                    record.getGrade(),
                    record.getExam()
            );
        }
        writer.flush(); // Ensure the data is immediately sent to the response
    }
}