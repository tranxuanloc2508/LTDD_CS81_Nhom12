package com.example.musicapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.PermissionRequest;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
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

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

public class ListSongActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private  boolean checkPermission = false;
    Uri uri;
    String songName,songUrl;
    Boolean checkin =  false;
    JSongsApdaterPlayer adapter;
    private int currentIndex;
    ProgressBar progressBar;

    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;
    List<GetSongs> mUpload;
    ListView listView;
    TextView tv_songname,tv_songArtists;



    ArrayList<String> arrayListSongsName = new ArrayList<>();
    ArrayList<String> arrayListSongsUrl = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;


    JcPlayerView jcPlayerView;
    ArrayList<JcAudio> jcAudios = new ArrayList<>();
    ArrayList<String> url = new ArrayList<>();
    ArrayList<String> songCategory = new ArrayList<>();
    ArrayList<String> artists = new ArrayList<>();
    ArrayList<String> album_art = new ArrayList<>();

//    ArrayAdapter<String> arrayAdapter;
//    JcPlayerView jcPlayerView;
//    ArrayList<JcAudio> jcAudios = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_song);
        listView = (ListView) findViewById(R.id.myListView);

        jcPlayerView = findViewById(R.id.jcplayer);
        retrieveSongs();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                jcPlayerView.playAudio(jcAudios.get(position));
                jcPlayerView.setVisibility(View.VISIBLE);
                jcPlayerView.createNotification();
            }
        });

        BottomNavigationView navView = findViewById(R.id.bottom_navigation);
        navView.setSelectedItemId(R.id.artist);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        startActivity(new Intent(getApplicationContext(), UploadSongsActivity.class));
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
                    case R.id.artist:

                }

                return true;
            }
        });
    }

    private void retrieveSongs() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("songs");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    GetSongs songObj = ds.getValue(GetSongs.class);
                    arrayListSongsName.add(songObj.getSong());
                    arrayListSongsUrl.add(songObj.getUrl());
                    artists.add(songObj.getArtists());

                    album_art.add(songObj.getCover_image());


                    jcAudios.add(JcAudio.createFromURL(songObj.getSong(),songObj.getUrl()));
                }
                arrayAdapter = new ArrayAdapter<String>(ListSongActivity.this, android.R.layout.simple_list_item_1, arrayListSongsName) {

                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView textView = (TextView) view.findViewById(android.R.id.text1);
                        textView.setSingleLine(true);
                        textView.setMaxLines(1);
                        return view;
                    }
                };
                jcPlayerView.initPlaylist(jcAudios,null);
                listView.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    private void pickSong() {

        Intent intent_upload = new Intent();
        intent_upload.setType("audio/*");
        intent_upload.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent_upload,1);

    }

//    public void changSelectedSong(int index){
//        adapter.notifyItemChanged(adapter.getSelectedPosition());
//        currentIndex = index;
//        adapter.setSelectedPosition(currentIndex);
//        adapter.notifyItemChanged(currentIndex);
//    }
}