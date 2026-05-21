package com.klef.fsad.sdp.service;

import com.klef.fsad.sdp.model.Attendance;
import com.klef.fsad.sdp.model.Employee;
import com.klef.fsad.sdp.repository.AttendanceRepository;
import com.klef.fsad.sdp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // 1. Fixes: Cannot resolve method 'getAllAttendance'
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    // 2. Fixes: Dashboard & Controller date methods
    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return attendanceRepository.findByDate(date);
    }

    // 3. Clock In Logic
    public Attendance clockIn(Long employeeId, String markedBy, String type) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        List<Attendance> existing = attendanceRepository.findByEmployeeAndDate(employee, LocalDate.now());
        if (!existing.isEmpty()) {
            return existing.get(0);
        }

        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setDate(LocalDate.now());
        attendance.setClockInTime(LocalTime.now());
        attendance.setStatus("PRESENT");
        attendance.setMarkedBy(markedBy);
        attendance.setType(type);
        attendance.setLastModified(LocalDate.now());

        return attendanceRepository.save(attendance);
    }

    // 4. Fixes: Cannot resolve method 'clockOut'
    public Attendance clockOut(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        List<Attendance> list = attendanceRepository.findByEmployeeAndDate(employee, LocalDate.now());
        if (list.isEmpty()) {
            throw new RuntimeException("No clock-in record found for today.");
        }

        Attendance attendance = list.get(0);
        attendance.setClockOutTime(LocalTime.now());
        return attendanceRepository.save(attendance);
    }

    // 5. Fixes: Cannot resolve method 'getEmployeeAttendance'
    public List<Attendance> getEmployeeAttendance(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return attendanceRepository.findByEmployee(employee);
    }

    // 6. Fixes: Cannot resolve method 'getAttendanceById'
    public Attendance getAttendanceById(Long id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance record not found"));
    }

    // 7. Fixes: Cannot resolve method 'updateAttendance'
    public Attendance updateAttendance(Long id, Attendance updatedData, String updatedBy) {
        Attendance existing = getAttendanceById(id);

        existing.setStatus(updatedData.getStatus());
        existing.setRemarks(updatedData.getRemarks());
        existing.setType(updatedData.getType());
        existing.setCourse(updatedData.getCourse());
        existing.setMarkedBy(updatedBy);
        existing.setLastModified(LocalDate.now());

        return attendanceRepository.save(existing);
    }
}