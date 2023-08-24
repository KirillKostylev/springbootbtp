package com.pet.project.springbootbtp.repository;

import com.pet.project.springbootbtp.model.Employee;
import com.pet.project.springbootbtp.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
