package com.endeymus.weather.controllers;

import com.endeymus.weather.models.CityTB;
import com.endeymus.weather.models.dao.CityDao;
import com.endeymus.weather.models.utils.Time;
import com.endeymus.weather.models.utils.Weather;
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
        CityDao cityDao = new CityDao();

        CityTB city = cityDao.findByCity(search_box);
//        System.out.println(city);
        Weather weather = new Weather(search_box);
        String pathToImage;
        if ( (city == null) || Time.getCurrMinutes() - city.getMinutes() > 300 * 1000){

            weather.getWeather();

            pathToImage = weather.getPathToImg();

            city = new CityTB(/*weather.getCityName(), weather.getTemperature(), Time.getCurrMinutes()*/);
            city.setName(weather.getCityName());
            city.setTemperature(weather.getTemperature());
            city.setMinutes(Time.getCurrMinutes());
            System.out.println("====================AFTER SET=========================");
            System.out.println(city);
            cityDao.save(city);
        } else {

            weather.setTemperature(city.getTemperature());
            pathToImage = weather.getPathToImg();
        }
        model.addAttribute("weather", ((int)city.getTemperature() > 0 ? "+" : "") + (int)city.getTemperature() + "°");
        model.addAttribute("title", search_box);
        model.addAttribute("srcImage", pathToImage);
        return "weather";
    }

}
