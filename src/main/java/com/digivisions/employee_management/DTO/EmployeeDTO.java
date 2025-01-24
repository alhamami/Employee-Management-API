package com.digivisions.employee_management.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateEmployeeDTO {

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
}
