# Employee Management System

A secure Spring Boot REST API project for managing employees, attendance, and authentication using JWT and role-based access control.



## Features

- Employee CRUD Operations
- JWT Authentication & Authorization
- Role-based access (ADMIN / EMPLOYEE)
- Attendance Management
- Attendance Summary Reports
- Swagger API Documentation
- Spring Security Integration
- MySQL Database Integration
- Exception Handling
- RESTful APIs


## Technologies Used

- Java 17
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- Hibernate
- MySQL
- Lombok
- Swagger / OpenAPI
- Maven

## Roles

### ADMIN
- Add Employee
- Update Employee
- Delete Employee
- View all employees
- Mark attendance
- View reports

### EMPLOYEE
- View own profile
- View own attendance



## Authentication

This project uses JWT Token based authentication.

### Login APIhttp
POST /auth/login


### Header Format
```http
Authorization: Bearer <token>

## API Endpoints

### Public APIs
- GET `/api/greet`
- POST `/auth/login`

### Admin APIs
- GET `/api/admin/employees`
- POST `/api/admin/employees`
- PUT `/api/admin/employees/{id}`
- DELETE `/api/admin/employees/{id}`

### Employee APIs
- GET `/api/employee/me`
- GET `/api/employee/my-attendance`

### Attendance APIs
- POST `/api/admin/attendance/{employeeId}`
- GET `/api/admin/attendance/{employeeId}`
- GET `/api/admin/attendance/report/full-summary`

---

## Swagger UI

http://localhost:8082/swagger-ui/index.html



## Project Highlights

- Implemented JWT filter using `OncePerRequestFilter`
- Used `SecurityFilterChain`
- Stateless Session Management
- Role based authorization
- Custom UserDetailsService
- BCrypt Password Encryption

## Author

**Thiru Selvam**
