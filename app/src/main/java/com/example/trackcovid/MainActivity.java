package com.example.trackcovid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemReselectedListener {

    private BottomNavigationView theNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        theNavigation = (BottomNavigationView) findViewById(R.id.main_menu);
        theNavigation.setOnNavigationItemSelectedListener(this);
        theNavigation.setOnNavigationItemReselectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_changer, new DailyStatistics()).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_page: {
                getSupportFragmentManager().beginTransaction().replace(R.id.menu_changer, new DailyStatistics()).commit();
                return true;
            }
            case R.id.global_page: {
                getSupportFragmentManager().beginTransaction().replace(R.id.menu_changer, new GlobalDailyStatistics()).commit();
                return true;
            }
            case R.id.hospital_page:{
                getSupportFragmentManager().beginTransaction().replace(R.id.menu_changer,new HospitalInformationSriLanka()).commit();
                return true;
            }
        }
        return false;
    }

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_page:
            case R.id.hospital_page:
            case R.id.global_page: {
                //do nothing if re-selected
                break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        theNavigation.setSelectedItemId(R.id.home_page);
    }
}