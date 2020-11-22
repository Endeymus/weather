package com.endeymus.weather;

import com.endeymus.weather.entities.CityTB;
import com.endeymus.weather.models.dao.CityDao;
import com.endeymus.weather.models.utils.Time;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@SpringBootTest
@Component
class WeatherApplicationTests {
	private final static String APIKEY = "d9ed7ab531dcaa535085c39bb498d0b4";

	@Autowired
	CityDao cityDao;

	@Test
	void contextLoads() {
		String json = "";
		String url = "http://api.openweathermap.org/data/2.5/weather?q=" + "москва" +  "&appid=" + APIKEY;
		try {
			json = Jsoup.connect(url).ignoreContentType(true).execute().body();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		System.out.println(json);
		JSONObject body = new JSONObject(json);
		System.out.println(body.getJSONObject("main").getDouble("temp"));
	}
	@Test
	void HibernateTest() {
		CityTB notExist = cityDao.findByCity("НетТакого");
		System.out.println("====================NOTEXIST===============");
		System.out.println(notExist);
		CityTB cityTB = new CityTB();
		cityTB.setName("Москва");
		cityTB.setTemperature(14.00);
		cityTB.setMinutes(Time.getCurrMinutes());
		cityDao.save(cityTB);
		System.out.println(cityDao.findByCity("Москва"));
		cityDao.delete(cityTB);
	}
	
	@Test
	void save() {
		CityTB cityTB = new CityTB();
		cityTB.setName("Москва");
		cityTB.setTemperature(14.00);
		cityTB.setMinutes(Time.getCurrMinutes());
		cityDao.save(cityTB);
		assert cityDao.findByCity("Москва") == cityTB;
	}

	@Test
	void delete() {
		CityTB cityTB = cityDao.findByCity("Москва");
		System.out.println(cityTB);
		cityDao.delete(cityTB);
		assert null == cityDao.findByCity("Москва");
	}

}
