package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.enums.Status;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.service.ProjectService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper mapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper mapper) {
        this.projectRepository = projectRepository;
        this.mapper = mapper;
    }

    @Override
    public ProjectDTO getByProjectCode(String code) {
        return mapper.convertToDTO(projectRepository.findByProjectCode(code));
    }

    @Override
    public List<ProjectDTO> listAllProjects() {
        return projectRepository.findAll().stream().map(mapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void save(ProjectDTO projectDTO) {
        projectDTO.setProjectStatus(Status.OPEN);
        projectRepository.save(mapper.convertToEntity(projectDTO));

    }

    @Override
    public void update(ProjectDTO projectDTO) {

    }

    @Override
    public void delete(String projectCode) {

    }
}
