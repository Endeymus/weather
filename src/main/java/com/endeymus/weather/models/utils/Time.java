package com.endeymus.weather.models.utils;

import org.springframework.stereotype.Component;

import java.util.GregorianCalendar;

public class Time {
    public static long getCurrMinutes () {
        GregorianCalendar current = new GregorianCalendar();
        return current.getTime().getTime();
    }
}
