package org.example.employee_mangement_system.service;

import org.example.employee_mangement_system.dto.AttendanceFullSummaryResponse;
import org.example.employee_mangement_system.model.Attendance;
import org.example.employee_mangement_system.model.AttendanceStatus;
import org.example.employee_mangement_system.model.Employee;
import org.example.employee_mangement_system.repository.AttendanceRepository;
import org.example.employee_mangement_system.repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmpRepository empRepository;

    public Attendance markAttendance(int employeeId, String status) {
        LocalDate today = LocalDate.now();
        return markAttendance(employeeId, status, today);
    }

    public Attendance markAttendance(int employeeId, String status, LocalDate date) {
        Employee employee = empRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (attendanceRepository.existsByEmployeeIdAndDate(employeeId, date)) {
            throw new RuntimeException("Attendance already marked for this date");
        }

        try {
            Attendance attendance = new Attendance();
            attendance.setEmployee(employee);
            attendance.setDate(date);

            AttendanceStatus attendanceStatus;
            try {
                attendanceStatus = AttendanceStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid attendance status. Use PRESENT, ABSENT, LATE, or LEAVE");
            }

            attendance.setStatus(attendanceStatus);

            return attendanceRepository.save(attendance);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Duplicate attendance not allowed");
        }
    }

    public List<Attendance> getAttendanceByEmployee(int employeeId) {
        return attendanceRepository.findByEmployeeId(employeeId);
    }

    public List<Attendance> getAttendanceBetweenDates(int employeeId, LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findByEmployeeIdAndDateBetween(employeeId, startDate, endDate);
    }
    public AttendanceFullSummaryResponse getFullAttendanceSummaryByDate(LocalDate date) {
        long totalEmployees = empRepository.count();
        long presentCount = attendanceRepository.countByDateAndStatus(date, AttendanceStatus.PRESENT);
        long absentCount = attendanceRepository.countByDateAndStatus(date, AttendanceStatus.ABSENT);
        long lateCount = attendanceRepository.countByDateAndStatus(date, AttendanceStatus.LATE);
        long leaveCount = attendanceRepository.countByDateAndStatus(date, AttendanceStatus.LEAVE);

        long markedCount = presentCount + absentCount + lateCount + leaveCount;
        long notMarkedCount = totalEmployees - markedCount;

        return new AttendanceFullSummaryResponse(
                date,
                presentCount,
                absentCount,
                lateCount,
                leaveCount,
                notMarkedCount,
                totalEmployees
        );
    }
}