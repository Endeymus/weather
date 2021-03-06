package com.endeymus.weather.models.utils;

public enum Temperature {
    FROST   (-30),
    VERYCOLD(-15),
    COLD    (0  ),
    COOL    (10 ),
    WARMLY  (20 ),
    HOT     (30 )
    ;
    public int getMinTemperature() {
        return this.minTemperature;
    }
    private final int minTemperature;
    Temperature(int minTemperature) {
        this.minTemperature = minTemperature;
    }
}
