package com.endeymus.weather.models;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Weather {
//    private int id;
    private final String imgFormat = ".jpg";
    private final String womenFolder = "images/";
    private String cityName;
    private String temperature;
    private String pathToImg;

    public Weather() {
    }

    private void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    private void setPathToImg(String pathToImg) {
        this.pathToImg = pathToImg;
    }

    public Weather(String cityName) {
        this.cityName = cityName;
    }

    public void getWeather() {
        String url = "https://yandex.ru/search/?text=погода%20в%20" + this.cityName + "&lr=213&clid=1882628";
        String classId = "weather-forecast__current-temp";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
            for (Element element : doc.getAllElements()) {
                System.out.println(element.text());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (doc != null) {
            String textTemp = doc.getElementsByClass(classId).text();
            setTemperature(textTemp);
        }
    }


    public String getPathToImg() {
        findPathToImages(findClothes(getValueOfTemp(getTemperature())));
        System.out.println(pathToImg);
        return this.pathToImg;
    }

    private void findPathToImages(Temperature temperature) {
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

    private int getValueOfTemp(String temperature) {
        System.out.println(temperature);
        Matcher matcher = Pattern.compile("\\d+").matcher(temperature);
        if (matcher.find()) {
            return Integer.parseInt(temperature.substring(matcher.start(), matcher.end()));
        }
        return 0;
    }
    public String getTemperature() {
        return this.temperature;
    }
}
