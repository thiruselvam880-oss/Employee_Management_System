package org.example.employee_mangement_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String gender;
    private String phoneNo;
    private String department;
    private String designation;
    private float salary;
    private String status;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;
}