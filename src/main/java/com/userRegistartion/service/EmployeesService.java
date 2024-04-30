package com.userRegistartion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userRegistartion.Exception.ResourceNotFoundException;
import com.userRegistartion.entity.Employees;
import com.userRegistartion.reposatry.EmployeesReposatry;
@Service
public class EmployeesService {

    @Autowired
    private EmployeesReposatry employeesRepository;

    
    public Employees createEmployee(Employees employee) {
        String email = employee.getEmail();
        if (email != null && !email.isEmpty()) {
            Optional<Employees> existingEmployeeOptional = employeesRepository.findByEmail(email);
            if (existingEmployeeOptional.isPresent()) {
                throw new RuntimeException("Employee with email " + email + " already exists");
            }
        }
        return employeesRepository.save(employee);
    }

    
    public Employees getEmployeeById(Integer id) {
        Optional<Employees> employee = employeesRepository.findById(id);
        if (employee.isPresent()) {
            return employee.get();
        } else {
            throw new ResourceNotFoundException("Employee", "id", id);
        }
    }

    public List<Employees> getAllEmployees() {
        return employeesRepository.findAll();
    }

    
    public Employees updateEmployee(int id, Employees updatedEmployee) {
        Optional<Employees> employee = employeesRepository.findById(id);
        if (employee.isPresent()) {
            Employees existingEmployee = employee.get();
            existingEmployee.setName(updatedEmployee.getName());
            existingEmployee.setEmployeePost(updatedEmployee.getEmployeePost());
            existingEmployee.setSalary(updatedEmployee.getSalary());
            existingEmployee.setExperiance(updatedEmployee.getExperiance());
            return employeesRepository.save(existingEmployee);
        } else {
            throw new ResourceNotFoundException("Employee", "id", id);
        }
    }

    
    public void deleteEmployee(Integer id) {
        Optional<Employees> employee = employeesRepository.findById(id);
        if (employee.isPresent()) {
            employeesRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Employee", "id", id);
        }
    }
}