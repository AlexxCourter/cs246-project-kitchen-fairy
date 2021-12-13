package com.example.prove06;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

public class WeatherForecastItem {

    String dt_txt;

    @SerializedName("main")
    Map<String, String> measurements;

    ArrayList<Weather> weather;

    //contains a map for the wind values.
    Map<String, String> wind;

    private String setWind() {
        String result = "\n";
        for (Map.Entry<String, String> i : wind.entrySet()) {
            result = result + "| " + i.getKey() + " : " +  i.getValue() + "\n";
        }
        return result;
    }

    @Override
    public String toString() {
        return  dt_txt + ":" +
                "\n|Temperature: " + measurements.get("temp") + "F" +
                weather.get(0) +
                "|Wind speed:" + wind.get("speed") + "mph\n";
    }

    public String getSpeed() {
        return this.wind.get("speed");

    }
    public String getTemp() {
        return this.measurements.get("temp");
    }
}
