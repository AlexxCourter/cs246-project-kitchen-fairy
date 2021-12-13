package com.example.prove06;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class GetTemp implements Runnable {
    public String cityName;
    String appID = "appid=2f35ec7ffdcd2042fc4fe8fc077b764f";
    String cityURL;
    Activity mainActivity;


    public GetTemp(Activity mainActivity, String cityName) {
        this.cityName = cityName;
        this.cityURL = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&units=imperial&" + appID;
        this.mainActivity = mainActivity;
    }

    public static WeatherCondition getWeather(String cityURL) {
        Gson gson = new Gson();

        String responseBody = getResponse(cityURL);
        WeatherCondition cityWeather = gson.fromJson(responseBody, WeatherCondition.class);
        return cityWeather;
    }

    @Override
    public void run() {
        final String TAG = "GetTemp";
        Log.d(TAG, "Getting temperature for " + cityName + " on background thread");
        WeatherCondition weather = getWeather(cityURL);
        Float temp = weather.getTemp();
        Log.d("GetTemp", "Temperature in " + cityName + " is: " + temp);

        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Context context = mainActivity.getApplicationContext();
                CharSequence text = "Temperature: " + temp + "F";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }

    public static String getResponse(String cityURL) {
        Scanner scanner;
        try {
            URLConnection connection = new URL(cityURL).openConnection();
            InputStream response = connection.getInputStream();

            scanner = new Scanner(response);
            String responseBody = scanner.useDelimiter("\\A").next();
            return responseBody;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
