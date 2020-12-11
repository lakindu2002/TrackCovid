package com.example.trackcovid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

public class SplashScreen extends AppCompatActivity {

    private ProgressBar theProgress;
    private static int SPLASH_TIME = 750;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); //disable dark mode for the application

        theProgress = (ProgressBar) findViewById(R.id.splash_loading);
        theProgress.setVisibility(View.VISIBLE);

        Handler theHandler = new Handler();

        theHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent theIntent = new Intent(SplashScreen.this,MainActivity.class);
                theIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(theIntent);
                finish();
            }
        },SPLASH_TIME);
    }
}