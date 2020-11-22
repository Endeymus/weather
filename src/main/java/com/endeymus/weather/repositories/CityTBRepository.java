package com.endeymus.weather.repositories;

import com.endeymus.weather.entities.CityTB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityTBRepository extends JpaRepository<CityTB, String> {
    CityTB findByName(String name);
    List<CityTB> findAll();
}
