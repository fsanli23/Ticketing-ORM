package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String name);

    @Modifying
    @Transactional
    void deleteByUserName(String username);

    List<User> findAllByRoleDescriptionIgnoreCase(String description);







}
