package com.endeymus.weather.models.utils;

import com.endeymus.weather.entities.CityTB;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


@Component
public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public SessionFactory getSessionFactory () {
        return sessionFactory;
    }

    @PostConstruct
    private void init() {
        System.out.println("init");
        try {
            sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(CityTB.class)
                    .buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @PreDestroy
    private void destr(){
        System.out.println("desrt");
        sessionFactory.close();
    }
}
