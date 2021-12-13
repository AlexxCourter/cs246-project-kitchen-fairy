package com.example.prove06;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class GetForecast implements Runnable {
    String cityName;
    String appID = "appid=2f35ec7ffdcd2042fc4fe8fc077b764f";
    String forecastURL;
    Activity mainActivity;

    /**
     * Class constructor
     * */
    public GetForecast(Activity mainActivity, String cityName) {
        this.forecastURL = "https://api.openweathermap.org/data/2.5/forecast?q=" + cityName + "&units=imperial&" + appID;
        this.cityName = cityName;
        this.mainActivity = mainActivity;
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

    public static WeatherForecast getForecast(String cityURL) {
        Gson gson = new Gson();

        String responseBody = getResponse(cityURL);
        WeatherForecast forecast = gson.fromJson(responseBody, WeatherForecast.class);
        return forecast;
    }

    @Override
    public void run() {
        final String TAG = "GetForecast";
        Log.d(TAG, "Getting forecast for " + cityName + " on background thread");
        WeatherForecast weather = getForecast(forecastURL);
        Log.d(TAG, "Forecast information:");
        weather.displayForecast();

        //convert the weatherForecastItem list to a string list
        List<String> weatherList = weather.processWeather();

        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Context context = mainActivity.getApplicationContext();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, weatherList);
                ListView listView = (ListView) mainActivity.findViewById(R.id.listView);
                listView.setAdapter(adapter);
            }
        });
    }
}
