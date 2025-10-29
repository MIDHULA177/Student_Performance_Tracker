package com.JavaProject.Javasem.model;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class AttendanceForm {
    private List<AttendanceEntry> attendanceList;

    @Data
    public static class AttendanceEntry {
        private Long studentId;
        private LocalDate date;
        private boolean present;
        private String notes;
    }
}
