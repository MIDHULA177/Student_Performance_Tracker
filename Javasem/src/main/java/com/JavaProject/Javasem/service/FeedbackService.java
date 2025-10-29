package com.JavaProject.Javasem.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.JavaProject.Javasem.model.Feedback;
import com.JavaProject.Javasem.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository repo;

    // 1. Save/Update Feedback (Used by /add and /update)
    public Feedback saveFeedback(Feedback feedback) {
        if (feedback.getId() == null && feedback.getDate() == null) {
            feedback.setDate(LocalDate.now());
        }
        return repo.save(feedback);
    }

    // 2. Retrieve All Feedback (Used for the list view)
    public List<Feedback> getAllFeedback() {
        return repo.findAll();
    }

    // 3. Find Feedback by ID (Used by /edit/{id})
    public Optional<Feedback> findById(Long id) {
        return repo.findById(id);
    }

    // 4. Delete Feedback by ID
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    // --- Specific Retrieval Methods (Optional but kept for completeness) ---

    public List<Feedback> byStudent(Long sid) {
        return repo.findByStudentId(sid);
    }

    public List<Feedback> byFaculty(Long fid) {
        return repo.findByFacultyId(fid);
    }
}