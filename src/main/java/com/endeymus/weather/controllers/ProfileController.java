package com.endeymus.weather.controllers;

import com.endeymus.weather.entities.CityTB;
import com.endeymus.weather.entities.User;
import com.endeymus.weather.repositories.CityTBRepository;
import com.endeymus.weather.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private UserRepository userRepository;
    private CityTBRepository cityTBRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setCityTBRepository(CityTBRepository cityTBRepository) {
        this.cityTBRepository = cityTBRepository;
    }

    @GetMapping("/")
    public String redirectToProfile(Principal principal) {
        return String.format("redirect:/profile/%d", userRepository.findUserByUsername(principal.getName()).getId());
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable int id, Model model) {
        User user = userRepository.findUserById(id);
        model.addAttribute("user", user);
        return "/profile/profile";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        User user = userRepository.findUserById(id);
        List<CityTB> list = cityTBRepository.findAll();
        model.addAttribute("user", user);
        model.addAttribute("citys", list);
        return "/profile/edit";
    }

    @PostMapping("/{id}/update")
    public String update(@ModelAttribute User user, @PathVariable int id) {
        User updatedUser = userRepository.findUserById(id);
        updatedUser.setCity_name(user.getCity_name());
        updatedUser.setEmail(user.getEmail());
        userRepository.save(updatedUser);
        return String.format("redirect:/profile/%d", id);
    }

    @ModelAttribute("title")
    public String title(){
        return "Страница профиля";
    }

}
