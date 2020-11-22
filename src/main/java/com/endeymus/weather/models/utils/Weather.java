package com.endeymus.weather.models.utils;

//import com.sun.istack.NotNull;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.GregorianCalendar;

@Component
public class Weather {
//    private int id;
    @Value("${spring.api}")
    private String APIKEY;

    private final static String imgFormat = ".jpg";
    private final static String womenFolder = "images/";
    private final static double K = 273.15;

    private String cityName;
    private double temperature;
    private String pathToImg;
    private long minutes;

    public Weather() {
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    private void setPathToImg(String pathToImg) {
        this.pathToImg = pathToImg;
    }


    /**
     * Отправляем запрос на api и получаем ответ
     * @param city
     * @return тело запроса
     */
    private String getUrlContent(String city) {
        String json = "";
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city +  "&appid=" + APIKEY;
//        System.out.println(url);
        try {
            json = Jsoup.connect(url).ignoreContentType(true).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
//            System.out.println("Неверный город");
        }
        return json;
    }


    public void getWeather() {
        minutes = Time.getCurrMinutes();

        String body = getUrlContent(this.cityName);

        if(!body.isEmpty()){
            JSONObject json = new JSONObject(body);
            double temp = json.getJSONObject("main").getDouble("temp");
//            System.out.println(temp - 273.15);
            setTemperature(temp - 273.15);
        }
    }

    /**
     * возвращает путь до картинки
     * @return String
     */
    public String getPathToImg() {
        findPathToImages(findClothes(getIntOfTemp()));
//        System.out.println(pathToImg);
        return this.pathToImg;
    }

    /**
     * устанавливает путь для указанной температуры
     * @param temperature
     */
    private void findPathToImages( Temperature temperature) {
        String folder = womenFolder;
        switch (temperature) {
            case HOT:
                setPathToImg(folder + "hot" + imgFormat);
                break;
            case WARMLY:
                setPathToImg(folder + "warmly" + imgFormat);
                break;
            case COOL:
                setPathToImg(folder +"cool" + imgFormat);
                break;
            case COLD:
                setPathToImg(folder + "cold" + imgFormat);
                break;
            case VERYCOLD:
                setPathToImg(folder + "verycold" + imgFormat);
                break;
            case FROST:
                setPathToImg(folder + "frost" + imgFormat);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + temperature);
        }
    }

    /**
     * возвращает enum Temperature для указанной температуры
     * @param temperature
     * @return enum Temperature
     */
    private Temperature findClothes (int temperature){
        if (temperature > Temperature.HOT.getMinTemperature()) {                //30
            return Temperature.HOT;
        } else if (temperature > Temperature.WARMLY.getMinTemperature()) {      //20
            return Temperature.WARMLY;
        } else if (temperature > Temperature.COOL.getMinTemperature()) {        //10
            return Temperature.COOL;
        } else if (temperature > Temperature.COLD.getMinTemperature()) {        //0
            return Temperature.COLD;
        } else if (temperature > Temperature.VERYCOLD.getMinTemperature()) {    //-15
            return Temperature.VERYCOLD;
        } else return Temperature.FROST;                                        //-30
    }

    /**
     * возвращает int значение параметра температуры
     * @return int
     */
    private int getIntOfTemp() {
        return (int) this.temperature;
    }

    public double getTemperature() {
        return this.temperature;
    }
}
