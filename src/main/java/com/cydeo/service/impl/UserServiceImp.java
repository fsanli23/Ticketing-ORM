package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParsePosition;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final ProjectService projectService;

    private final TaskService taskService;


    public UserServiceImp(UserRepository userRepository, UserMapper userMapper, @Lazy ProjectService projectService, TaskService taskService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.projectService = projectService;
        this.taskService = taskService;
    }


    @Override
    public List<UserDTO> listAllUsers() {
        return userRepository.findAll(Sort.by("firstName")).stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String username) {
        return userMapper.convertToDto(userRepository.findByUserName(username));
    }

    @Override
    public void save(UserDTO user) {
        userRepository.save(userMapper.convertToEntity(user));
    }

    @Override
    public UserDTO update(UserDTO dto) {
        // find current user
        User user = userRepository.findByUserName(dto.getUserName());
        //Map updated userdto to entity object
        User convertedUser = userMapper.convertToEntity(dto);
        //set id to converted Object
        convertedUser.setId(user.getId());
        userRepository.save(convertedUser);

        return findByUserName(dto.getUserName());
    }

    @Override
    public void deleteByUserName(String username) {
        userRepository.deleteByUserName(username);
    }

    @Override
    public void delete(String username) {
        // i will not delete from db . trying soft delete. changing the flag and keep in the db.
        User user = userRepository.findByUserName(username);
        if (checkIfUserCanBeDeleted(user)) {
            user.setIsDelete(Boolean.TRUE);
            user.setUserName(user.getUserName() + "-" + user.getId());
            userRepository.save(user);
        }


    }

    @Override
    public List<UserDTO> listAllByRole(String role) {
        return userRepository.findAllByRoleDescriptionIgnoreCase(role).stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }

    private Boolean checkIfUserCanBeDeleted(User user) {
        switch (user.getRole().getDescription()) {
            case "Manager":
                List<ProjectDTO> projectDTOList = projectService.readAllByAssignedManager(user);
                return projectDTOList.isEmpty();
            case "Employee":
                List<TaskDTO> taskDTOList = taskService.readAllByAssignedEmployee(user);
                return taskDTOList.isEmpty();


            default:
                return true;
        }
    }

}
