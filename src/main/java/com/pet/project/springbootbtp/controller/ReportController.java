package com.pet.project.springbootbtp.controller;

import com.pet.project.springbootbtp.dto.ReportDto;
import com.pet.project.springbootbtp.dto.ReportRequestDto;
import com.pet.project.springbootbtp.model.Employee;
import com.pet.project.springbootbtp.model.Project;
import com.pet.project.springbootbtp.model.Report;
import com.pet.project.springbootbtp.service.impl.EmployeeService;
import com.pet.project.springbootbtp.service.impl.ProjectService;
import com.pet.project.springbootbtp.service.impl.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/reports")
public class ReportController {

    private final EmployeeService employeeService;
    private final ProjectService projectService;
    private final ReportService reportService;

    @PostMapping
    @PreAuthorize("hasAuthority('Display')")
    public ReportDto create(@RequestBody ReportRequestDto reportRequestDto) {
        Employee employee = employeeService.getEmployee(reportRequestDto.getEmployeeId());
        Project project = projectService.getProject(reportRequestDto.getProjectId());
        Report report = reportService.save(project, employee, reportRequestDto.getDescription());
        return convertToReportDto(report);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('Display')")
    public ReportDto getById(@PathVariable Long id) {
        return convertToReportDto(reportService.findById(id));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('Display')")
    public List<ReportDto> getById() {
        return reportService.getAll().stream()
                .map(ReportController::convertToReportDto)
                .collect(Collectors.toList());
    }

    private static ReportDto convertToReportDto(Report report) {
        return new ReportDto(report.getId(), report.getDescription(), report.getCreatedAt(),
                report.getEmployee().getId(), report.getProject().getId());
    }
}