package com.JavaProject.Javasem.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import com.JavaProject.Javasem.model.Attendance;
import com.JavaProject.Javasem.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository repo;
    public Attendance save(Attendance a) { if (a.getDate()==null) a.setDate(LocalDate.now()); return repo.save(a); }
    public List<Attendance> byStudent(Long sid) { return repo.findByStudentId(sid); }
    public List<Attendance> byDate(LocalDate date) { return repo.findByDate(date); }
    private final AttendanceRepository attendanceRepository;

    public List<Attendance> getAllAttendance() {
            return attendanceRepository.findAll();
    }

    public Attendance saveAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }
    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }

}

