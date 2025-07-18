package com.cydeo.service.impl;

import com.cydeo.dto.TaskDTO;
import com.cydeo.entity.Task;
import com.cydeo.enums.Status;
import com.cydeo.mapper.TaskMapper;
import com.cydeo.repository.TaskRepository;
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

    public TaskServiceImp(TaskRepository taskRepository, TaskMapper mapper) {
        this.taskRepository = taskRepository;
        this.mapper = mapper;
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
            convertedTask.setTaskStatus(task.get().getTaskStatus());;
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
}
