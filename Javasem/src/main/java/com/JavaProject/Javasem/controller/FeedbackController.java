package com.JavaProject.Javasem.controller;

import com.JavaProject.Javasem.model.Feedback;
import com.JavaProject.Javasem.model.User; // Ensure you have this import
import com.JavaProject.Javasem.service.FeedbackService;
import com.JavaProject.Javasem.service.UserService; // CRITICAL: Import UserService
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final UserService userService; // CRITICAL: Inject UserService

    public FeedbackController(FeedbackService feedbackService, UserService userService) {
        this.feedbackService = feedbackService;
        this.userService = userService; // Initialize UserService
    }

    // 1. Display all feedback
    @GetMapping
    public String viewFeedback(Model model) {
        model.addAttribute("feedbackList", feedbackService.getAllFeedback());
        // Add User lists for the Add form dropdowns (assuming these methods exist in UserService)
        model.addAttribute("allStudents", userService.getAllStudents());
        model.addAttribute("allFaculty", userService.getAllFaculty());

        return "feedback-list";
    }

    // 2. ✅ FIX: Handle adding new feedback by accepting IDs and fetching Users
    @PostMapping("/add")
    public String addFeedback(@RequestParam Long studentId, // Accept the ID
                              @RequestParam Long facultyId, // Accept the ID
                              @RequestParam String comments, // Accept comments
                              RedirectAttributes redirectAttributes) {

        // 1. Fetch the actual User entities
        User student = userService.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Student ID: " + studentId));
        User faculty = userService.findById(facultyId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Faculty ID: " + facultyId));

        // 2. Create the Feedback object
        Feedback feedback = new Feedback();
        feedback.setStudent(student);
        feedback.setFaculty(faculty);
        feedback.setComments(comments);

        // 3. Save the complete object
        feedbackService.saveFeedback(feedback);
        redirectAttributes.addFlashAttribute("successMessage", "✅ Feedback added successfully!");
        return "redirect:/feedback";
    }

    // In FeedbackController.java

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Feedback> feedbackOptional = feedbackService.findById(id);

        if (feedbackOptional.isPresent()) {
            model.addAttribute("feedback", feedbackOptional.get());
            // FIX: Return the name of the combined template
            return "feedback";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ Error: Feedback record not found.");
            return "redirect:/feedback";
        }
    }

    // 4. POST Mapping: Handle form submission for updating a record
    @PostMapping("/update")
    public String updateFeedback(@ModelAttribute Feedback feedback, RedirectAttributes redirectAttributes) {
        // Since the User entities are loaded via the HTML hidden fields,
        // the save method works directly for updates.
        feedbackService.saveFeedback(feedback);
        redirectAttributes.addFlashAttribute("successMessage", "✅ Feedback updated successfully!");
        return "redirect:/feedback";
    }

    // 5. POST Mapping: Handle deleting a record
    @PostMapping("/delete/{id}")
    public String deleteFeedback(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        feedbackService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "🗑️ Feedback deleted successfully!");
        return "redirect:/feedback";
    }
}