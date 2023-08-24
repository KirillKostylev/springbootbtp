package com.pet.project.springbootbtp.service.impl;

import com.pet.project.springbootbtp.model.Employee;
import com.pet.project.springbootbtp.model.Project;
import com.pet.project.springbootbtp.model.Report;
import com.pet.project.springbootbtp.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final ReportRepository reportRepository;

    @Transactional
    public Report save(Project project, Employee employee, String description) {
        return reportRepository.save(new Report(project, employee, description));
    }


    public Report findById(Long id) {
        return reportRepository.findById(id).orElseThrow(() -> new RuntimeException("Report not found"));
    }

    public Collection<Report> getAll() {
        return reportRepository.findAll();
    }
}