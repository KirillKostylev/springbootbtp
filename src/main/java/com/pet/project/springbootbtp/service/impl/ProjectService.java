package com.pet.project.springbootbtp.service.impl;

import com.pet.project.springbootbtp.model.Project;
import com.pet.project.springbootbtp.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;

    public Project getProject(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, "Project"));
    }
}