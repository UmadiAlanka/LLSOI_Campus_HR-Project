package com.klef.fsad.sdp.service;

import com.klef.fsad.sdp.dto.AttendanceDTO;
import com.klef.fsad.sdp.model.Attendance;
import com.klef.fsad.sdp.model.Employee;
import com.klef.fsad.sdp.repository.AttendanceRepository;
import com.klef.fsad.sdp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    public Attendance clockIn(Long employeeId, String markedBy) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Optional<Attendance> existing = attendanceRepository.findByEmployeeAndDate(employee, LocalDate.now());
        if (existing.isPresent()) {
            throw new RuntimeException("Attendance already marked for today");
        }

        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setDate(LocalDate.now());
        attendance.setClockInTime(LocalTime.now());
        attendance.setStatus("PRESENT");
        attendance.setMarkedBy(markedBy);
        attendance.setLastModified(LocalDate.now());

        return attendanceRepository.save(attendance);
    }

    public Attendance clockOut(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Attendance attendance = attendanceRepository.findByEmployeeAndDate(employee, LocalDate.now())
                .orElseThrow(() -> new RuntimeException("No clock-in record found for today"));

        if (attendance.getClockOutTime() != null) {
            throw new RuntimeException("Already clocked out for today");
        }

        LocalTime now = LocalTime.now();
        attendance.setClockOutTime(now);
        
        // Calculate Working Hours
        if (attendance.getClockInTime() != null) {
            double hours = java.time.Duration.between(attendance.getClockInTime(), now).toMinutes() / 60.0;
            attendance.setWorkingHours(Math.round(hours * 100.0) / 100.0); // Round to 2 decimal places
            
            // Auto-update status based on hours
            if (hours >= 8) {
                attendance.setStatus("PRESENT");
            } else if (hours >= 4) {
                attendance.setStatus("HALF_DAY");
            } else {
                // As per your rule: less than 4 hours is considered ABSENT
                attendance.setStatus("ABSENT");
            }
        }
        
        return attendanceRepository.save(attendance);
    }

    public Attendance updateAttendance(Long id, Attendance newData, String updatedBy) {
        Attendance existing = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance record not found"));

        if(newData.getStatus() != null) existing.setStatus(newData.getStatus());
        if(newData.getClockInTime() != null) existing.setClockInTime(newData.getClockInTime());
        if(newData.getClockOutTime() != null) existing.setClockOutTime(newData.getClockOutTime());
        existing.setMarkedBy(updatedBy);
        existing.setLastModified(LocalDate.now());

        // Recalculate Working Hours if both times are present
        if (existing.getClockInTime() != null && existing.getClockOutTime() != null) {
            double hours = java.time.Duration.between(existing.getClockInTime(), existing.getClockOutTime()).toMinutes() / 60.0;
            existing.setWorkingHours(Math.round(hours * 100.0) / 100.0);
            
            // If user didn't explicitly set a status, auto-update it
            if (newData.getStatus() == null) {
                if (hours >= 8) {
                    existing.setStatus("PRESENT");
                } else if (hours >= 4) {
                    existing.setStatus("HALF_DAY");
                } else {
                    existing.setStatus("ABSENT");
                }
            }
        }

        return attendanceRepository.save(existing);
    }

    public Attendance getAttendanceById(Long id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance record not found"));
    }

    public List<Attendance> getEmployeeAttendance(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return attendanceRepository.findByEmployee(employee);
    }

    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return attendanceRepository.findByDate(date);
    }

    public List<AttendanceDTO> getFilteredAttendance(LocalDate date, String dept, String type) {
        List<Attendance> attendanceList = attendanceRepository.findByFilters(date, dept, type);
        return attendanceList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private AttendanceDTO convertToDTO(Attendance attendance) {
        AttendanceDTO dto = new AttendanceDTO();
        dto.setId(attendance.getId());
        dto.setDate(attendance.getDate());
        dto.setClockInTime(attendance.getClockInTime());
        dto.setClockOutTime(attendance.getClockOutTime());
        dto.setStatus(attendance.getStatus());
        dto.setWorkingHours(attendance.getWorkingHours());
        dto.setRemarks(attendance.getRemarks());

        if (attendance.getEmployee() != null) {
            dto.setEmployeeId(attendance.getEmployee().getId());
            dto.setEmployeeName(attendance.getEmployee().getName());
            dto.setDepartment(attendance.getEmployee().getDepartment());
            dto.setJobType(attendance.getEmployee().getJobType());
        }
        return dto;
    }
}