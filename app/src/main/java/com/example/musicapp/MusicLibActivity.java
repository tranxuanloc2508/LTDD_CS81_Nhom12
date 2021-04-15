package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MusicLibActivity extends AppCompatActivity {

    ImageView img_MusicLib,img_SubLib,img_play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_lib);

        img_MusicLib = (ImageView)findViewById(R.id.musicLib);
        img_SubLib =(ImageView)findViewById(R.id.subIV);
        img_play =(ImageView)findViewById(R.id.playmussic);

        img_MusicLib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MusicLibActivity.this,PlayerActivity.class);
                startActivity(i);
            }
        });
        img_SubLib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MusicLibActivity.this,UploadSongsActivity.class);
                startActivity(i);
            }
        });
        img_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MusicLibActivity.this,AlbumActivity.class);
                startActivity(i);
            }
        });
    }
}