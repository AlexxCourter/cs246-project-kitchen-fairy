package com.example.prove06;

import androidx.annotation.NonNull;

import java.util.Map;



public class WeatherCondition {

        int id;
        String name;
        Map<String, Float> main;

        public WeatherCondition(Map<String, Float> map) {
            this.main = map;
        }

        @NonNull
        @Override
        public String toString() {
            return "WeatherConditions{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", measurements=" + main +
                    '}';
        }

        public Float getTemp() {
            Float temp = this.main.get("temp");
            return temp;
        }
    }
