package com.example.kitchenfairyprototype;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME = 2000;
    ImageView myImage;
    ObjectAnimator objectAnimator;

    /**
     * onCreate, the mainactivity acts as a splash page which shows the app logo, which will move by an animation. After the
     * Splash timer is up, sends to the HomeActivity which acts as the app home screen.
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;

        myImage= (ImageView)findViewById(R.id.logo);

        //This variable is to solve the margin error in different screens
        int error_margin = ((height/150)/3)*5;

        //Animation to move the logo in the Home Activity position
        objectAnimator=ObjectAnimator.ofFloat(myImage,"y",height/3 + error_margin, height/6 - error_margin);
        objectAnimator.setDuration(800);
        objectAnimator.setStartDelay(1200);
        objectAnimator.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        },SPLASH_TIME);
    }
}