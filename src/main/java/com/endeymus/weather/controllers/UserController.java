package com.endeymus.weather.controllers;

import com.endeymus.weather.entities.CityTB;
import com.endeymus.weather.entities.Role;
import com.endeymus.weather.entities.User;
import com.endeymus.weather.repositories.CityTBRepository;
import com.endeymus.weather.repositories.RoleRepository;
import com.endeymus.weather.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {
    private UserRepository userRepository;
    private CityTBRepository cityTBRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setCityTBRepository(CityTBRepository cityTBRepository) {
        this.cityTBRepository = cityTBRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("citys", cityTBRepository.findAll());
        return "/login/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute User user) {
        Set<Role> set = new HashSet<>();
        set.add(roleRepository.getOne(1));
        user.setRoles(set);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "/login/auth";
    }
}
