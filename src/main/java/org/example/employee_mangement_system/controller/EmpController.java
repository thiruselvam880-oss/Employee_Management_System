package org.example.employee_mangement_system.controller;

import org.example.employee_mangement_system.dto.EmployeeRequest;
import org.example.employee_mangement_system.model.Employee;
import org.example.employee_mangement_system.service.EmpServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class EmpController {

    @Autowired
    private EmpServices services;

    @GetMapping("/greet")
    public String greet() {
        return "Welcome to Thiru Selvam Project";
    }

    @GetMapping("/admin/employees")
    public List<Employee> getAllEmployees() {
        return services.getAllDetails();
    }

    @GetMapping("/admin/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        Employee employee = services.getEmployeeById(id);
        if (employee == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping("/admin/employees")
    public ResponseEntity<Employee> addEmployee(@RequestBody EmployeeRequest request) {
        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setGender(request.getGender());
        employee.setPhoneNo(request.getPhoneNo());
        employee.setDepartment(request.getDepartment());
        employee.setDesignation(request.getDesignation());
        employee.setSalary(request.getSalary());
        employee.setStatus(request.getStatus());

        Employee saved = services.addEmployee(employee, request.getUsername(), request.getPassword());

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/admin/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id, @RequestBody Employee e) {
        Employee updated = services.updateEmployee(id, e);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/admin/employees/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        return new ResponseEntity<>(services.deleteEmployee(id), HttpStatus.OK);
    }

    @GetMapping("/employee/me")
    public ResponseEntity<Employee> getMyProfile(Authentication authentication) {
        String username = authentication.getName();
        Employee employee = services.getMyProfile(username);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
}