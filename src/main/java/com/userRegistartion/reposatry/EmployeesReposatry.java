package com.userRegistartion.reposatry;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.userRegistartion.entity.Employees;

@Repository
public interface EmployeesReposatry extends JpaRepository<Employees, Integer> {
	Optional<Employees> findByEmail(String email);
}
