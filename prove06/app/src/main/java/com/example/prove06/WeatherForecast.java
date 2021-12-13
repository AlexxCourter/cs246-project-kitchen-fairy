package com.example.prove06;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WeatherForecast {

    @SerializedName("list")
    List<WeatherForecastItem> weatherItemList;

    public void displayForecast() {
        for (WeatherForecastItem i : weatherItemList) {
            System.out.println(i.toString());
        }

    }

    @Override
    public String toString() {
        return "WeatherForecast{" +
                "weatherItemList=" + weatherItemList +
                '}';
    }

    public List<String> processWeather() {
        List<String> result = new ArrayList<String>();
        for (WeatherForecastItem i : weatherItemList) {
            result.add(i.toString());
        }
        return result;
    }

}
