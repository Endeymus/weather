package com.endeymus.weather.controllers;

import com.endeymus.weather.entities.Role;
import com.endeymus.weather.entities.User;
import com.endeymus.weather.models.utils.Weather;
import com.endeymus.weather.repositories.CityTBRepository;
import com.endeymus.weather.repositories.RoleRepository;
import com.endeymus.weather.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class MainController {

    private Weather weather;

    private CityTBRepository cityTBRepository;

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setCityTBRepository(CityTBRepository cityTBRepository) {
        this.cityTBRepository = cityTBRepository;
    }

    @Autowired
    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    @GetMapping("/")
    public String home(Model model, Principal principal) {

        User user = userRepository.findUserByUsername(principal.getName());
        Role admin = roleRepository.getByName("ROLE_ADMIN");

        boolean permission = user.getRoles().contains(admin);

        weather.setCityName(user.getCity_name());
        weather.getWeather();

        model.addAttribute("weather", (weather.getTemperature() > 0 ? "+" : "")
                + weather.getTemperature() + "°");
        model.addAttribute("title", weather.getCityName());
        model.addAttribute("srcImage", weather.getPathToImg());
        model.addAttribute("permission", permission);
        return "weather";
    }

}
