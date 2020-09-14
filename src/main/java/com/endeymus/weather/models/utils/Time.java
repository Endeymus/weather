package com.endeymus.weather.models.utils;

import java.util.GregorianCalendar;

public class Time {
    public static long getCurrMinutes () {
        GregorianCalendar current = new GregorianCalendar();
        return current.getTime().getTime();
    }
}
