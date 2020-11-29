package com.endeymus.weather.controllers;

import com.endeymus.weather.entities.CityTB;
import com.endeymus.weather.entities.Role;
import com.endeymus.weather.entities.User;
import com.endeymus.weather.repositories.CityTBRepository;
import com.endeymus.weather.repositories.RoleRepository;
import com.endeymus.weather.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private CityTBRepository cityTBRepository;

    private UserRepository userRepository;

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
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String adminPage() {
        return "admin/admin_page";
    }

    @GetMapping("/citys")
    public String citys(Model model){
        List<CityTB> list = cityTBRepository.findAll();
        model.addAttribute("citys", list);
        return "admin/citys";
    }

    @GetMapping("/roles")
    public String roles(Model model){
        List<Role> list = roleRepository.findAll();
        model.addAttribute("roles", list);
        return "admin/roles";
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<User> list = userRepository.findAll();
        model.addAttribute("users", list);
        return "admin/users";
    }

    @ModelAttribute("title")
    public String title() {
        return "Страница админа";
    }
}
