package org.example.employee_mangement_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceFullSummaryResponse {
    private LocalDate date;
    private long presentCount;
    private long absentCount;
    private long lateCount;
    private long leaveCount;
    private long notMarkedCount;
    private long totalEmployees;
}