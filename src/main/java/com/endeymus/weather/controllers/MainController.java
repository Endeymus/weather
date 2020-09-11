package com.endeymus.weather.controllers;

import com.endeymus.weather.models.Weather;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная Страница");
        return "home";
    }

    @PostMapping("/")
    public String weatherPost(@RequestParam String search_box, Model model) {
        Weather weather = new Weather(search_box);
        weather.getWeather();
        String pathToImage = weather.getPathToImg();
        model.addAttribute("weather", weather.getTemperature());
        model.addAttribute("title", search_box);
        model.addAttribute("srcImage", pathToImage);
        return "weather";
    }

}
