package com.userRegistartion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userRegistartion.Exception.ResourceNotFoundException;
import com.userRegistartion.entity.Employees;
import com.userRegistartion.service.EmployeesService;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

	@Autowired
	private EmployeesService employeesService;

	@PostMapping
	public ResponseEntity<?> createEmployee(@RequestBody(required = false) Employees employee) {
		try {
			if (employee == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Input is not provided");
			}

			Employees createdEmployee = employeesService.createEmployee(employee);
			return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to create employee: " + e.getMessage());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Employees> getEmployeeById(@PathVariable("id") Integer id) {
		try {
			Employees employee = employeesService.getEmployeeById(id);
			return new ResponseEntity<>(employee, HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping
	public ResponseEntity<List<Employees>> getAllEmployees() {
		List<Employees> employees = employeesService.getAllEmployees();
		return new ResponseEntity<>(employees, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Employees> updateEmployee(@PathVariable("id") Integer id,
			@RequestBody Employees updatedEmployee) {
		try {
			Employees employee = employeesService.updateEmployee(id, updatedEmployee);
			return new ResponseEntity<>(employee, HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Integer id) {
		try {
			employeesService.deleteEmployee(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
