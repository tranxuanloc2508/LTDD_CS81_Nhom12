package com.example.musicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.example.musicapp.Adapter.JSongsApdaterPlayer;
import com.example.musicapp.Model.GetSongs;
import com.example.musicapp.Model.UpLoadSong;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListSonggsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    Boolean checkin =  false;
    List<UpLoadSong> mUpload;
    JSongsApdaterPlayer adapter;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;
    JcPlayerView jcPlayerView;
    ArrayList<JcAudio> jcAudios = new ArrayList<>();
    Integer currentIndex = 0;

    ArrayAdapter<String> arrayAdapter;

    ArrayList<String> listurl = new ArrayList<>();
    ArrayList<String> listsong = new ArrayList<>();
    ArrayList<String> artists = new ArrayList<>();
    ArrayList<String> cover_image = new ArrayList<>();
    List<GetSongs> getSongs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_songgs);

        recyclerView = findViewById(R.id.recycleview);
        progressBar = findViewById(R.id.progressbarshowSong);
        jcPlayerView = findViewById(R.id.jcplayer);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mUpload = new ArrayList<>();
        recyclerView.setAdapter(adapter);

        currentIndex = 0;

        adapter = new JSongsApdaterPlayer(getApplicationContext(), mUpload, new JSongsApdaterPlayer.RecyclerItemClickListener() {
            @Override
            public void onClickListener(UpLoadSong songs, int postion) {
                changSelectedSong(postion);
                jcPlayerView.playAudio(jcAudios.get(postion));
                jcPlayerView.setVisibility(View.VISIBLE);
                jcPlayerView.createNotification();
                currentIndex=postion;

            }
        });
        BottomNavigationView navView = findViewById(R.id.bottom_navigation);
        navView.setSelectedItemId(R.id.navigation_home);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:

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
                    case R.id.artist:
                        startActivity(new Intent(getApplicationContext(), ListSongActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return false;
                }

                return true;
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("songs");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUpload.clear();
                for (DataSnapshot dss: snapshot.getChildren()){
                    UpLoadSong getSongs = dss.getValue(UpLoadSong.class);

                    getSongs.setmKey(dss.getKey());

                    mUpload.add(getSongs);
                    checkin=true;
                    jcAudios.add(JcAudio.createFromURL(getSongs.getSongTitle(),getSongs.getSongLink()));

                    //  }
                }

                adapter.setSelectedPosition(currentIndex);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

                if(checkin){
                    jcPlayerView.initPlaylist(jcAudios,null);

                }else {
                    Toast.makeText(ListSonggsActivity.this, "there is no songs!!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                progressBar.setVisibility(View.GONE);
            }
        });

    }
    public void changSelectedSong(int index){
        adapter.notifyItemChanged(adapter.getSelectedPosition());
        currentIndex = index;
        adapter.setSelectedPosition(currentIndex);
        adapter.notifyItemChanged(currentIndex);
    }
}