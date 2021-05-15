package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainAdminActivity extends AppCompatActivity {

    Button eAlbum,eArtits,eSong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        eAlbum = findViewById(R.id.btn_ablum);
        eArtits = findViewById(R.id.btn_artist);
        eSong = findViewById(R.id.btn_song);

        eAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainAdminActivity.this,UploadAlbummActivity.class);
                startActivity(i);
            }
        });
        eArtits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainAdminActivity.this,UpArtistActivity.class);
                startActivity(i);
            }
        });
        eSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainAdminActivity.this,UploadSongsActivity.class);
                startActivity(i);
            }
        });
    }
}