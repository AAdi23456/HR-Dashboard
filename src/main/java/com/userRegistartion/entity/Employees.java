package com.userRegistartion.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
public class Employees {

	public Employees() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;
	private String employeePost;
	private String salary;
	private String experiance;

	@Column(unique = true)
	private String email;

	public Employees(Integer id, String name, String employeePost, String salary, String experiance, String email) {
		super();
		this.id = id;
		this.name = name;
		this.employeePost = employeePost;
		this.salary = salary;
		this.experiance = experiance;
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmployeePost() {
		return employeePost;
	}

	public void setEmployeePost(String employeePost) {
		this.employeePost = employeePost;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getExperiance() {
		return experiance;
	}

	public void setExperiance(String experiance) {
		this.experiance = experiance;
	}

}
