package com.endeymus.weather.repositories;

import com.endeymus.weather.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role getByName(String name);
    List<Role> findAll();
}
