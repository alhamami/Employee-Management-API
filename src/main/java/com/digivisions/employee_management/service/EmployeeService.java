package com.digivisions.employee_management.service;

import com.digivisions.employee_management.DTO.EmployeeDTO;
import com.digivisions.employee_management.DTO.EmployeeResponseDTO;
import com.digivisions.employee_management.entity.Employee;
import com.digivisions.employee_management.exception.EmployeeNotFoundException;
import com.digivisions.employee_management.exception.InvalidInputException;
import com.digivisions.employee_management.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepo;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private ValidationService validationService;
    @Autowired
    private EmailService emailService;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);


    public List<EmployeeResponseDTO> getAllEmployees() {

        logger.info("EmailService: retrieving all employees");

        List<Employee> employees = employeeRepo.findAll();
        List<EmployeeResponseDTO> employeeResponseDTO = employees.stream().map(employeeMapper::convertToDTO).toList();

        logger.info("Successfully retrieved employees: "+employeeResponseDTO.size());

        return employeeResponseDTO;
    }

    public EmployeeResponseDTO getEmployeeById(Long id) {
        logger.info("EmployeeService: Retrieving employee with ID: "+ id);

        try {

            Optional<Employee> employee = employeeRepo.findById(id);

            if(employee.isPresent()) {

                logger.info("Employee with id "+id+" found");

                return employeeMapper.convertToDTO(employee.get());
            }else{
                logger.error("Employee with id "+id+" not found");
                throw new EmployeeNotFoundException("Employee Not Found");
            }
        } catch (Exception exception) {
            logger.error("Error while retrieving employee with id: "+ id+" and the error message: "+ exception.getMessage());
            throw exception;
        }
    }


    public EmployeeResponseDTO createEmployee(EmployeeDTO employeeDTO) {

        logger.info("EmployeeService: Creating new employee");
        logger.info("Starting employee creation process");


        try {

            if (!validationService.validateEmail(employeeDTO.getEmail())) {

                logger.error("Invalid Email: "+ employeeDTO.getEmail());

                throw new InvalidInputException("Invalid Email");
            }

            if (!validationService.validateDepartment(employeeDTO.getDepartment())) {

                logger.error("Invalid Department: "+ employeeDTO.getDepartment());

                throw new InvalidInputException("Invalid Department");
            }

            Employee employee = employeeMapper.convertToEmployee(employeeDTO);
            Employee createdEmployee = employeeRepo.save(employee);
            logger.info("Employee created with id: "+ createdEmployee.getId());

            logger.info("Employee creation process completed successfully");
            logger.info("Employee with id "+createdEmployee.getId()+"has been created");


            emailService.sendEmail(createdEmployee.getEmail(),createdEmployee.getFirstName());

            return employeeMapper.convertToDTO(createdEmployee);

        } catch (Exception exception) {

            logger.error("Error while creating employee with first name: "+employeeDTO.getFirstName()+" and the error message: "+ exception.getMessage());
            throw exception;
        }
    }


    public EmployeeResponseDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {

        logger.info("EmployeeService: Updating employee with ID: "+ id);

        try {

            if (!validationService.validateEmail(employeeDTO.getEmail())) {

                logger.error("Invalid Email: "+ employeeDTO.getEmail());

                throw new InvalidInputException("Invalid Email");
            }

            Optional<Employee> foundEmployee = employeeRepo.findById(id);

            if(foundEmployee.isPresent()) {
                Employee employee = foundEmployee.get();
                employeeMapper.updateEmployee(employeeDTO, employee);
                Employee updatedEmployee = employeeRepo.save(employee);

                logger.info("Employee with id "+id+" updated successfully");

                return employeeMapper.convertToDTO(updatedEmployee);

            }else{

                logger.error("Employee with id "+id+" not found");

                throw new EmployeeNotFoundException("Employee Not Found");
            }

        } catch (Exception exception) {

            logger.error("Error while updating employee with id "+id+" and the error message: "+ exception.getMessage());
            throw exception;
        }

    }

    public void deleteEmployee(Long id) {

        logger.info("EmployeeService: Deleting employee with id: "+ id);

        try {

            Optional<Employee> foundEmployee = employeeRepo.findById(id);

            if(foundEmployee.isPresent()) {
                employeeRepo.delete(foundEmployee.get());
                logger.info("Employee with id "+id+" deleted successfully");

            }else{

                logger.error("Employee with id "+id+" not found");
                throw new EmployeeNotFoundException("Employee Not Found");
            }
        } catch (Exception exception) {
            logger.error("Error while deleting employee with id "+id+" and the error message: "+ exception.getMessage());
            throw exception;
        }
    }

}
