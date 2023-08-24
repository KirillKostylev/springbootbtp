package com.pet.project.springbootbtp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@Getter
@Setter
@NoArgsConstructor
public class ReportRequestDto {

    @NotBlank
    private String description;

    @NotNull
    @Positive
    @JsonProperty("employee_id")
    private Long employeeId;

    @NotNull
    @Positive
    @JsonProperty("project_id")
    private Long projectId;
}