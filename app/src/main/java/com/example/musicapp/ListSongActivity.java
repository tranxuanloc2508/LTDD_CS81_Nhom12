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
import android.widget.ImageView;
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
    ProgressBar progressBar;
    Boolean checkin =  false;
    List<UpLoadSong> mUpload;
    JSongsApdaterPlayer adapter;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;
    JcPlayerView jcPlayerView;
    ArrayList<JcAudio> jcAudios = new ArrayList<>();
    Integer currentIndex = 0;
   ImageView down;
   TextView Lyric;
//    ArrayList<Post> posts = null;

    ArrayList<String> lyrics = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_song);
        down = findViewById(R.id.down);

        jcPlayerView = findViewById(R.id.jcplayer);

        Lyric=(TextView)findViewById(R.id.lyrics);
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListSongActivity.this,ListSonggsActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.toptodown, R.anim.fade);
            }
        });
//        adapter = new JSongsApdaterPlayer(getApplicationContext(), mUpload, new JSongsApdaterPlayer.RecyclerItemClickListener() {
//            @Override
//            public void onClickListener(UpLoadSong songs, int postion) {
//                changSelectedSong(postion);
//                jcPlayerView.playAudio(jcAudios.get(postion));
//                jcPlayerView.setVisibility(View.VISIBLE);
//                jcPlayerView.createNotification();
//                currentIndex=postion;
//
//            }
//        });

        databaseReference = FirebaseDatabase.getInstance().getReference("songs");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                mUpload.clear();
                for (DataSnapshot dss: snapshot.getChildren()){
                    UpLoadSong getSongs = dss.getValue(UpLoadSong.class);

                    getSongs.setmKey(dss.getKey());

//                    mUpload.add(getSongs);
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
                    Toast.makeText(ListSongActivity.this, "there is no songs!!", Toast.LENGTH_SHORT).show();
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