package com.pet.project.springbootbtp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportDto {

    private Long id;
    private String description;
    private LocalDateTime createdAt;
    private Long employeeId;
    private Long projectId;
}