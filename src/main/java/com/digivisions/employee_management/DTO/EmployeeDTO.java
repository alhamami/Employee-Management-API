package com.digivisions.employee_management.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;


public class EmployeeDTO {

    @NotBlank(message = "First Name is mandatory field")
    private String firstName;

    @NotBlank(message = "Last Name is mandatory field")
    private String lastName;

    @Email(message = "Invalid Email")
    @NotBlank(message = "Email is mandatory field")
    private String email;

    @NotBlank(message = "Department is mandatory field")
    private String department;

    @NotNull(message = "Salary is mandatory field")
    @DecimalMin(value = "0.0", inclusive = false, message = "The Salary value must be greater than zero")
    private BigDecimal salary;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}
