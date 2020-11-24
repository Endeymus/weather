package com.endeymus.weather.repositories;

import com.endeymus.weather.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByUsername(String username);

    User findUserById(int id);

    List<User> findAll();
}
