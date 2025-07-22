package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Task;
import com.cydeo.entity.User;
import com.cydeo.enums.Status;
import com.cydeo.mapper.TaskMapper;
import com.cydeo.repository.TaskRepository;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImp implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper mapper;

    private final UserRepository userRepository;

    public TaskServiceImp(TaskRepository taskRepository, TaskMapper mapper, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Override
    public TaskDTO findById(Long id) {

        return taskRepository.findById(id).map(mapper::ConvertToDTO).orElse(null);
    }

    @Override
    public List<TaskDTO> listAllTasks() {
        return taskRepository.findAll().stream().map(mapper::ConvertToDTO).collect(Collectors.toList());
    }

    @Override
    public void save(TaskDTO taskDTO) {
        taskDTO.setTaskStatus(Status.OPEN);
        taskDTO.setAssignedDate(LocalDate.now());
        Task task = mapper.ConvertToEntity(taskDTO);
        taskRepository.save(task);
    }

    @Override
    public void update(TaskDTO taskDTO) {

        Optional<Task> task = taskRepository.findById(taskDTO.getId());
        Task convertedTask = mapper.ConvertToEntity(taskDTO);

        if (task.isPresent()) {
            convertedTask.setId(task.get().getId());
            convertedTask.setTaskStatus(taskDTO.getTaskStatus() == null ? task.get().getTaskStatus() : taskDTO.getTaskStatus());
            convertedTask.setAssignedDate(task.get().getAssignedDate());
            taskRepository.save(convertedTask);
        }


    }

    @Override
    public void delete(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            task.get().setIsDelete(Boolean.TRUE);
            taskRepository.save(task.get());
        }
    }

    @Override
    public int totalNonCompletedTask(String projectCode) {
        return taskRepository.totalNonCompletedTask(projectCode);
    }

    @Override
    public int totalCompleted(String projectCode) {
        return taskRepository.totalCompleted(projectCode);
    }

    @Override
    public void deleteByProject(ProjectDTO projectDTO) {


        listAllByProject(projectDTO).forEach(each -> delete(projectDTO.getId()));


    }


    private List<TaskDTO> listAllByProject(ProjectDTO projectDTO) {
        List<Task> list = taskRepository.findAllByProject(projectDTO);
        return list.stream().map(mapper::ConvertToDTO).collect(Collectors.toList());
    }

    @Override
    public void completeByProject(ProjectDTO projectDTO) {
        listAllByProject(projectDTO).forEach(taskDTO -> {
            taskDTO.setTaskStatus(Status.COMPLETE);
            update(taskDTO);
        });
    }

    @Override
    public List<TaskDTO> listAllTasksByStatusIsNot(Status status) {
        User user = userRepository.findByUserName("Meda.Bednar");
        List<Task> list = taskRepository.findAllByTaskStatusIsNotAndAssignedEmployee(status, user);
        return list.stream().map(mapper::ConvertToDTO).collect(Collectors.toList());

    }

    @Override
    public void updateStatus(TaskDTO taskDTO) {
        Optional<Task> task = taskRepository.findById(taskDTO.getId());

        if (task.isPresent()){
            task.get().setTaskStatus(taskDTO.getTaskStatus());
            taskRepository.save(task.get());
        }



    }

    @Override
    public List<TaskDTO> listAllTasksByStatus(Status status) {
        User user = userRepository.findByUserName("Meda.Bednar");
        List<Task> list = taskRepository.findAllByTaskStatusIsAndAssignedEmployee(status, user);
        return list.stream().map(mapper::ConvertToDTO).collect(Collectors.toList());

    }

    @Override
    public List<TaskDTO> readAllByAssignedEmployee(User assignedEmployee) {
       List<Task> taskList= taskRepository.findAllByAssignedEmployee(assignedEmployee);
       return taskList.stream().map(mapper::ConvertToDTO).collect(Collectors.toList());
    }
}
