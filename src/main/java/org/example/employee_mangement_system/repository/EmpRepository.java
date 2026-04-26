package org.example.employee_mangement_system.repository;

import org.example.employee_mangement_system.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpRepository extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByUserUsername(String username);
}