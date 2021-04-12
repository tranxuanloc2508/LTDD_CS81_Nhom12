package com.example.musicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.musicapp.Model.GetSongs;
import com.example.musicapp.Model.SongModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SongsListActivity extends AppCompatActivity {


    private MyAdapter myAdapter;
    private RecyclerView recyclerView;

    private static final int REQUEST_CODE = 101;
    List<GetSongs> mUpload;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    ProgressBar progressBar;
    ValueEventListener valueEventListener;
    private int currentIndex;
    Boolean checkin =  false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_list);


//        recyclerView = findViewById(R.id.myRecyclerView);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mUpload = new ArrayList<GetSongs>();
//        recyclerView.setAdapter(myAdapter);
//
//        databaseReference = FirebaseDatabase.getInstance().getReference("songs");
//        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                mUpload.clear();
//                for (DataSnapshot dss: snapshot.getChildren()){
//                    GetSongs getSongs = dss.getValue(GetSongs.class);
//                    getSongs.setSong(dss.getKey());
//                    currentIndex = 0;
//
//                    final String s =  getIntent().getExtras().getString("songCategory");
//                    if(s.equals(getSongs.getSong())){
//
//                        mUpload.add(getSongs);
//                        checkin=true;
////                        jcAudios.add(JcAudio.createFromURL(getSongs.getSongTitle(),getSongs.getSongLink()));
//
//                    }
//                }
//                progressBar.setVisibility(View.GONE);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//                progressBar.setVisibility(View.GONE);
//            }
//        });

    }

    private void loadDataList(List<SongModel> songsList) {

//        recyclerView = findViewById(R.id.myRecyclerView);
//        myAdapter = new MyAdapter(songsList);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SongsListActivity.this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(myAdapter);

    }
}