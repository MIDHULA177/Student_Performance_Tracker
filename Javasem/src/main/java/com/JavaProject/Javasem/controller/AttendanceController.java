package com.JavaProject.Javasem.controller;

import com.JavaProject.Javasem.model.Attendance;
import com.JavaProject.Javasem.model.AttendanceForm;
import com.JavaProject.Javasem.model.User;
import com.JavaProject.Javasem.service.AttendanceService;
import com.JavaProject.Javasem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final UserService userService;

    @GetMapping
    public String viewAttendance(Model model) {
        List<User> students = userService.findAllStudents();
        model.addAttribute("students", students);
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("attendanceList", attendanceService.getAllAttendance());
        return "attendance";
    }

    @PostMapping("/markAll")
    public String markAllAttendance(@ModelAttribute AttendanceForm attendanceForm,
                                    RedirectAttributes redirectAttributes) {
        for (AttendanceForm.AttendanceEntry entry : attendanceForm.getAttendanceList()) {
            Attendance attendance = new Attendance();
            attendance.setStudent(userService.getUserById(entry.getStudentId()));
            attendance.setDate(entry.getDate());
            attendance.setPresent(entry.isPresent());
            attendance.setNotes(entry.getNotes());
            attendanceService.saveAttendance(attendance);
        }
        redirectAttributes.addFlashAttribute("successMessage", "Attendance marked successfully for all students!");
        return "redirect:/attendance";
    }

}
