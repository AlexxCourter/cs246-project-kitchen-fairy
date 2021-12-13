package com.example.prove06;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //button handlers

    //temperature handler
    public void tempHandler(View view) {
        final String TAG = "MainActivity";
        //get the city name entered by the user
        EditText cityName = (EditText) findViewById(R.id.editTextCityName);
        String cityString = cityName.getText().toString();
        Log.d(TAG, "About to process city temperature for:" + cityString);
        //create the background thread that runs the getTemp command
        new Thread( new GetTemp(this, cityString)).start();

    }

    //forecast handler
    public void forecastHandler(View view) {
        final String TAG = "MainActivity";
        //get the city name entered by the user
        EditText cityName = (EditText) findViewById(R.id.editTextCityName);
        String cityString = cityName.getText().toString();
        Log.d(TAG, "About to process city forecast for:" + cityString);
        new Thread( new GetForecast(this, cityString)).start();
    }


} // end of class