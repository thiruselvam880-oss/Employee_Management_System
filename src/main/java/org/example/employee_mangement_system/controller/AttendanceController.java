package org.example.employee_mangement_system.controller;

import org.example.employee_mangement_system.dto.AttendanceFullSummaryResponse;
import org.example.employee_mangement_system.model.Attendance;
import org.example.employee_mangement_system.model.Employee;
import org.example.employee_mangement_system.service.AttendanceService;
import org.example.employee_mangement_system.service.EmpServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private EmpServices empServices;

    @PostMapping("/admin/attendance/{employeeId}")
    public ResponseEntity<Attendance> markAttendance(
            @PathVariable int employeeId,
            @RequestParam String status,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        Attendance saved;

        if (date != null) {
            saved = attendanceService.markAttendance(employeeId, status, date);
        } else {
            saved = attendanceService.markAttendance(employeeId, status);
        }

        return ResponseEntity.ok(saved);
    }

    @GetMapping("/employee/my-attendance")
    public ResponseEntity<List<Attendance>> getMyAttendance(Authentication authentication) {
        String username = authentication.getName();
        Employee employee = empServices.getMyProfile(username);

        List<Attendance> attendanceList = attendanceService.getAttendanceByEmployee(employee.getId());
        return ResponseEntity.ok(attendanceList);
    }

    @GetMapping("/admin/attendance/{employeeId}")
    public ResponseEntity<List<Attendance>> getAttendanceByEmployee(@PathVariable int employeeId) {
        return ResponseEntity.ok(attendanceService.getAttendanceByEmployee(employeeId));
    }

    @GetMapping("/admin/attendance/{employeeId}/range")
    public ResponseEntity<List<Attendance>> getAttendanceByDateRange(
            @PathVariable int employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return ResponseEntity.ok(
                attendanceService.getAttendanceBetweenDates(employeeId, startDate, endDate)
        );
    }
    @GetMapping("/admin/attendance/report/full-summary")
    public ResponseEntity<AttendanceFullSummaryResponse> getAttendanceSummaryByDate(
            @RequestParam("date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return ResponseEntity.ok(attendanceService.getFullAttendanceSummaryByDate(date));
    }
}