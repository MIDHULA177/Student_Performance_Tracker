package com.JavaProject.Javasem.service;

import com.JavaProject.Javasem.model.Performance;
import com.JavaProject.Javasem.repository.PerformanceRepository;
import org.springframework.stereotype.Service;
import java.util.List;
// Removed: import java.util.stream.Collectors;

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

    // ✅ FIX: Now uses the efficient database query instead of in-memory filtering.
    public List<Performance> filterPerformances(String exam, String subject, String grade) {
        // The service layer passes the sanitized (potentially null) values
        // directly to the custom repository query.
        return repository.findFilteredPerformances(exam, subject, grade);
    }
}