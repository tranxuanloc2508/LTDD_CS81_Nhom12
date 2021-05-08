package com.example.musicapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MenuLibActivity extends AppCompatActivity {

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_lib);

        BottomNavigationView navView = findViewById(R.id.bottom_navigation);
        navView.setSelectedItemId(R.id.navigation_home);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        startActivity(new Intent(getApplicationContext(), ListSonggsActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return false;
                    case R.id.navigation_dashboard:
                        startActivity(new Intent(getApplicationContext(), AlbumActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return false;
                    case R.id.navigation_notifications:
                        startActivity(new Intent(getApplicationContext(), UploadSongsActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return false;

                }

                return true;
            }
        });

    }


}