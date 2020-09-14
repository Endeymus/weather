package com.endeymus.weather.models.dao;

import com.endeymus.weather.models.CityTB;
import com.endeymus.weather.models.utils.HibernateUtil;
import org.hibernate.Session;

public class CityDao {
    public CityTB findByCity(String name) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            CityTB cityTB = (CityTB) session.get(CityTB.class, name);
            session.close();
            return cityTB;
        } catch (Exception ignored){
            return null;
        }
    }
    public void save(CityTB city) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.saveOrUpdate(city);
            session.getTransaction().commit();
            session.close();
        }catch (Exception ignored){}
    }

    public void update(CityTB city) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(city);
            session.getTransaction().commit();
            session.close();
        }catch (Exception ignored){}
    }

    public void delete(CityTB city) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                session.delete(city);
                session.getTransaction().commit();
                session.close();
            }catch (Exception ignored){}
    }
}
