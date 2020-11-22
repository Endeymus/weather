package com.endeymus.weather.models.dao;

import com.endeymus.weather.entities.CityTB;
import com.endeymus.weather.models.utils.HibernateUtil;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
//@Scope("prototype")
public class CityDao {

    @Autowired
    private HibernateUtil util;

    public CityTB findByCity(String name) {
        try (Session session = util.getSessionFactory().openSession();) {
            CityTB cityTB = session.get(CityTB.class, name);
//            session.close();
            return cityTB;
        } catch (Exception ignored){
            System.out.println("catch: findByCity");
            return null;
        }
    }
    public void save(CityTB city) {
        try (Session session = util.getSessionFactory().openSession();) {
//            System.out.println("save");
            session.beginTransaction();
//            System.out.println("after transaction");
            session.save(city);
//            System.out.println("after save");
            session.getTransaction().commit();
//            System.out.println("after commit");
//            session.close();
        }catch (Exception ignored){
            ignored.printStackTrace();
            System.out.println("catch: save");
        }
    }

    public void update(CityTB city) {
        try (Session session = util.getSessionFactory().openSession();) {
            session.beginTransaction();
            session.update(city);
            session.getTransaction().commit();
//            session.close();
        }catch (Exception ignored){
            System.out.println("catch: update");
        }
    }

    public void delete(CityTB city) {
        try (Session session = util.getSessionFactory().openSession();) {
                session.beginTransaction();
                session.delete(city);
                session.getTransaction().commit();
//                session.close();
            }catch (Exception ignored){
            System.out.println("catch: delete");
        }
    }
}
