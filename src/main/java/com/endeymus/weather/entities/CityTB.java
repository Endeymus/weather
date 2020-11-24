package com.endeymus.weather.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@ToString
@Data
@Table(name = "citytb")
public class CityTB implements Serializable {

    @Id
    @Column(name = "city")
    private String name;

    private String deviceID;


}
