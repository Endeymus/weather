package com.endeymus.weather.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@ToString
@Table
@NoArgsConstructor
public class CityTB implements Serializable {

    @Id
    @Column(name = "city")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private double temperature;

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    private long minutes;

    public long getMinutes() {
        return minutes;
    }

    public void setMinutes(long minutes) {
        this.minutes = minutes;
    }


}
