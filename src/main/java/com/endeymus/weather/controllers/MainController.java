package com.endeymus.weather.controllers;

import com.endeymus.weather.entities.CityTB;
import com.endeymus.weather.models.dao.CityDao;
import com.endeymus.weather.models.utils.Time;
import com.endeymus.weather.models.utils.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    private Weather weather;

    private CityDao cityDao;

    @Autowired
    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    @Autowired
    public void setCityDao(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная Страница");
        return "home";
    }

    @PostMapping("/")
    public String weatherPost(@RequestParam String search_box, Model model) {

        CityTB city = cityDao.findByCity(search_box);
//        System.out.println(search_box);

        weather.setCityName(search_box);

        String pathToImage;
//        System.out.println(city == null);
        if (city == null){
            weather.getWeather();
            city = new CityTB(/*weather.getCityName(), weather.getTemperature(), Time.getCurrMinutes()*/);
            city.setName(weather.getCityName());
            city.setTemperature(weather.getTemperature());
            city.setMinutes(Time.getCurrMinutes());
            cityDao.save(city);
        } else if (Time.getCurrMinutes() - city.getMinutes() > 300 * 1000) {
            weather.getWeather();
            city.setTemperature(weather.getTemperature());
            cityDao.update(city);
        } else {
            weather.setTemperature(city.getTemperature());
        }
            pathToImage = weather.getPathToImg();

        model.addAttribute("weather", ((int)city.getTemperature() > 0 ? "+" : "") + (int)city.getTemperature() + "°");
        model.addAttribute("title", search_box);
        model.addAttribute("srcImage", pathToImage);
        return "weather";
    }

}
