package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public UserServiceImp(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
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
        user.setIsDelete(Boolean.TRUE);
        userRepository.save(user);
    }

    @Override
    public List<UserDTO> listAllByRole(String role) {
     return userRepository.findAllByRoleDescriptionIgnoreCase(role).stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }
}
