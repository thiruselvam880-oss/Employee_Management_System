package org.example.employee_mangement_system.service;

import org.example.employee_mangement_system.model.Employee;
import org.example.employee_mangement_system.model.Role;
import org.example.employee_mangement_system.model.User;
import org.example.employee_mangement_system.repository.EmpRepository;
import org.example.employee_mangement_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpServices {

    @Autowired
    private EmpRepository repo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Employee> getAllDetails() {
        return repo.findAll();
    }

    public Employee getEmployeeById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Employee addEmployee(Employee e, String username, String rawPassword) {

        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(Role.EMPLOYEE);

        User savedUser = userRepository.save(user);

        e.setUser(savedUser);

        return repo.save(e);
    }

    public Employee updateEmployee(int id, Employee updatedEmployee) {
        Employee existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        existing.setName(updatedEmployee.getName());
        existing.setGender(updatedEmployee.getGender());
        existing.setPhoneNo(updatedEmployee.getPhoneNo());
        existing.setDepartment(updatedEmployee.getDepartment());
        existing.setDesignation(updatedEmployee.getDesignation());
        existing.setSalary(updatedEmployee.getSalary());
        existing.setStatus(updatedEmployee.getStatus());

        return repo.save(existing);
    }

    public String deleteEmployee(int id) {
        Employee emp = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        User user = emp.getUser();

        repo.delete(emp);

        if (user != null) {
            userRepository.delete(user);
        }

        return "Deleted Successfully";
    }

    public Employee getMyProfile(String username) {
        return repo.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }
}