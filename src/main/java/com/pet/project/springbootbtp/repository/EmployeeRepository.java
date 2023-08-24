package com.pet.project.springbootbtp.repository;

import com.pet.project.springbootbtp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
