package com.pet.project.springbootbtp.repository;

import com.pet.project.springbootbtp.model.Project;
import com.pet.project.springbootbtp.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
