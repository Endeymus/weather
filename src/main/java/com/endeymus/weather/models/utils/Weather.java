package com.endeymus.weather.models.utils;

import com.endeymus.weather.repositories.CityTBRepository;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Weather {
    private final static String imgFormat = ".jpg";
    private final static String womenFolder = "images/";

    private CityTBRepository cityTBRepository;

    @Autowired
    public void setCityTBRepository(CityTBRepository cityTBRepository) {
        this.cityTBRepository = cityTBRepository;
    }

    private String cityName;
    private int temperature;
    private String pathToImg;

    public Weather() {
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    private void setPathToImg(String pathToImg) {
        this.pathToImg = pathToImg;
    }


    /**
     * Отправляем запрос на api и получаем ответ
     * @return тело запроса
     */
    private String getUrlContent() {
        String json = "";
        String url = String.format("https://demo.thingsboard.io/api/v1/%s/attributes?clientKeys=temperature",
                cityTBRepository.findByName(cityName).getDeviceID());
        try {
            json = Jsoup.connect(url).ignoreContentType(true).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }


    public void getWeather() {
        String body = getUrlContent();

        if(!body.isEmpty()){
            JSONObject json = new JSONObject(body);
            int temp = json.getJSONObject("client").getInt("temperature");
            setTemperature(temp);
        }
    }

    /**
     * возвращает путь до картинки
     * @return String
     */
    public String getPathToImg() {
        findPathToImages(findClothes(getTemperature()));
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


    public int getTemperature() {
        return this.temperature;
    }
}
