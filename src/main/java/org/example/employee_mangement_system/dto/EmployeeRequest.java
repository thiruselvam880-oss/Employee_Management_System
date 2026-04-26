package org.example.employee_mangement_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {

    private String name;
    private String gender;
    private String phoneNo;
    private String department;
    private String designation;
    private float salary;
    private String status;

    private String username;
    private String password;
}