package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Project;
import com.cydeo.entity.Task;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.mapper.ProjectMapper;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.ProjectRepository;
import com.cydeo.repository.TaskRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final TaskService taskService;
    private final MapperUtil mapperUtil;


    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, UserService userService, UserMapper userMapper, TaskService taskService, MapperUtil mapperUtil) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.userService = userService;
        this.userMapper = userMapper;
        this.taskService = taskService;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public ProjectDTO getByProjectCode(String code) {
        return projectMapper.convertToDTO(projectRepository.findByProjectCode(code));
    }

    @Override
    public List<ProjectDTO> listAllProjects() {
        return projectRepository.findAll().stream().map(projectMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void save(ProjectDTO projectDTO) {
        projectDTO.setProjectStatus(Status.OPEN);
        projectRepository.save(projectMapper.convertToEntity(projectDTO));

    }

    @Override
    public void update(ProjectDTO projectDTO) {

        Project project = projectRepository.findByProjectCode(projectDTO.getProjectCode());
        Project convertedProject = projectMapper.convertToEntity(projectDTO);
        convertedProject.setProjectStatus(project.getProjectStatus());
        convertedProject.setId(project.getId());
        projectRepository.save(convertedProject);


    }

    @Override
    public void delete(String projectCode) {

        Project project = projectRepository.findByProjectCode(projectCode);
        project.setIsDelete(Boolean.TRUE);
        project.setProjectCode(project.getProjectCode() + "-" + project.getId());
        projectRepository.save(project);
        taskService.deleteByProject(projectMapper.convertToDTO(project));

    }

    @Override
    public void complete(String projectCode) {
        var project = projectRepository.findByProjectCode(projectCode);
        project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);
        taskService.completeByProject(projectMapper.convertToDTO(project));


    }

    @Override
    public List<ProjectDTO> listAllProjectDetails() {
        UserDTO currentUserDTO = userService.findByUserName("Ciara21");
        var list = projectRepository.findAllByAssignedManager(userMapper.convertToEntity(currentUserDTO));
        return list.stream().map(project -> {
            ProjectDTO projectDTO = projectMapper.convertToDTO(project);
            projectDTO.setUnfinishedTaskCounts(taskService.totalNonCompletedTask(project.getProjectCode()));
            projectDTO.setCompleteTaskCounts(taskService.totalCompleted(project.getProjectCode()));
            return projectDTO;
        }).collect(Collectors.toList());

    }

    @Override
    public List<ProjectDTO> readAllByAssignedManager(User assignedManager) {
        List<Project> list = projectRepository.findAllByAssignedManager(assignedManager);
        return list.stream().map(projectMapper::convertToDTO).collect(Collectors.toList());
    }
}
