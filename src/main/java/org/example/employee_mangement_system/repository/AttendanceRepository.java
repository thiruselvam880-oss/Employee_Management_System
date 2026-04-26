package org.example.employee_mangement_system.repository;

import org.example.employee_mangement_system.model.Attendance;
import org.example.employee_mangement_system.model.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance,Integer> {
    boolean existsByEmployeeIdAndDate(int employeeId, LocalDate date);

    List<Attendance> findByEmployeeId(int employeeId);

    List<Attendance> findByEmployeeIdAndDateBetween(int employeeId, LocalDate startDate, LocalDate endDate);

    long countByDateAndStatus(LocalDate date, AttendanceStatus attendanceStatus);
}
