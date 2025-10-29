package com.JavaProject.Javasem.controller;

import com.JavaProject.Javasem.model.Feedback;
import com.JavaProject.Javasem.model.User;
import com.JavaProject.Javasem.service.FeedbackService;
import com.JavaProject.Javasem.service.UserService;
import lombok.RequiredArgsConstructor;
// CRITICAL FIX: Use the correct Spring Security Authentication import
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final UserService userService; // Used to mock/fetch the current student

    // Sample list of subjects for the dropdown (replace with dynamic fetching if needed)
    private final List<String> SUBJECTS_LIST = Arrays.asList("Math", "Science", "History", "English");

    /**
     * Displays the feedback submission form and the list of past feedback.
     */
    @GetMapping
    public String showFeedbackPage(Model model) {

        // 1. Setup form for submission (using a new Feedback object)
        model.addAttribute("newFeedback", new Feedback());

        // 2. Pass the list of subjects for the dropdown
        model.addAttribute("subjectsList", SUBJECTS_LIST);

        // 3. Display all past feedback records
        model.addAttribute("feedbackList", feedbackService.getAllFeedback());

        return "feedback"; // Assumes you have a Thymeleaf template named 'feedback.html'
    }


    /**
     * Handles the submission of new feedback.
     * It relies on Spring Security's Authentication object to fetch the logged-in student.
     */
    @PostMapping("/submit")
    public String submitFeedback(@ModelAttribute("newFeedback") Feedback feedback,
                                 Authentication authentication,
                                 RedirectAttributes redirectAttributes) {

        // 1. Get the username/email (principal name) from the logged-in user
        // FIX: Use the standard .getName() method from Spring Security Authentication
        String username = authentication.getName();

        // 2. Load the existing, managed Student/User entity from the database
        // ASSUMPTION: The UserService method findByUsername is implemented and returns an Optional<User>
        User existingStudent = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Logged-in student user not found with username: " + username));

        // 3. Set the LOADED entity on the new feedback object
        feedback.setStudent(existingStudent);

        // 4. Save the new Feedback, correctly linked to the existing student.
        try {
            feedbackService.saveFeedback(feedback);
            redirectAttributes.addFlashAttribute("successMessage", "✅ Feedback submitted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ Error saving feedback: " + e.getMessage());
        }

        return "redirect:/feedback";
    }
}