package com.JavaProject.Javasem.service;

import com.JavaProject.Javasem.model.Feedback;
import com.JavaProject.Javasem.model.User;
import com.JavaProject.Javasem.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserService userService; // Assuming you have a UserService to find students

    /**
     * Saves a single feedback object to the database.
     */
    public void saveFeedback(Feedback feedback) {
        // Ensure dateSubmitted is set before saving
        if (feedback.getDateSubmitted() == null) {
            feedback.setDateSubmitted(LocalDate.now());
        }
        feedbackRepository.save(feedback);
    }

    /**
     * Saves multiple feedback objects (e.g., from a multi-subject form submission).
     */
    public void saveAllFeedback(List<Feedback> feedbackList) {
        feedbackList.forEach(feedback -> {
            if (feedback.getDateSubmitted() == null) {
                feedback.setDateSubmitted(LocalDate.now());
            }
        });
        feedbackRepository.saveAll(feedbackList);
    }

    /**
     * Finds all feedback records for display/reporting.
     */
    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    /**
     * Finds all feedback records submitted by a specific student ID.
     */
    public List<Feedback> getFeedbackByStudentId(Long studentId) {
        return feedbackRepository.findByStudentId(studentId);
    }


    // You can add more complex logic here, like calculating average subject ratings.
}