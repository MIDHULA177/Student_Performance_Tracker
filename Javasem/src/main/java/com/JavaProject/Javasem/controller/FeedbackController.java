package com.JavaProject.Javasem.controller;

import com.JavaProject.Javasem.model.Feedback;
import com.JavaProject.Javasem.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping
    public String showFeedbackPage(Model model) {
        model.addAttribute("feedbackList", feedbackService.getAllFeedback());
        model.addAttribute("newFeedback", new Feedback());
        model.addAttribute("subjectsList", List.of("Math", "Science", "English", "AI", "Data Structures"));
        return "feedback";
    }

    @PostMapping("/submit")
    public String submitFeedback(@ModelAttribute Feedback newFeedback) {
        feedbackService.saveFeedback(newFeedback);
        return "redirect:/feedback";
    }

    @PostMapping("/delete/{id}")
    public String deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        return "redirect:/feedback";
    }

    @GetMapping("/edit/{id}")
    public String editFeedback(@PathVariable Long id, Model model) {
        Feedback feedback = feedbackService.getFeedbackById(id);
        model.addAttribute("feedback", feedback);
        return "edit-feedback";
    }
    @PostMapping("/update/{id}")
    public String updateFeedback(@PathVariable Long id, @ModelAttribute Feedback updatedFeedback) {
        Feedback existing = feedbackService.getFeedbackById(id);
        if (existing != null) {
            existing.setSubject(updatedFeedback.getSubject());
            existing.setRating(updatedFeedback.getRating());
            existing.setComment(updatedFeedback.getComment());
            feedbackService.saveFeedback(existing);
        }
        return "redirect:/feedback";
    }

}
