package com.example.musicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.musicapp.Model.UploadAlbum;
import com.example.musicapp.Adapter.RecycleViewAlbum;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends AppCompatActivity {


    RecycleViewAlbum adapter;
    RecyclerView recyclerView;
    DatabaseReference mDatabaseReference;
    ProgressDialog progressDialog;
    private List<UploadAlbum> uploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        recyclerView =  findViewById(R.id.recycleView_id);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        progressDialog = new ProgressDialog(this);
        uploads = new ArrayList<>();

        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("uploads");
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    UploadAlbum uploadAlbum = dataSnapshot.getValue(UploadAlbum.class);
                    uploads.add(uploadAlbum);
                }
                adapter = new RecycleViewAlbum(getApplicationContext(),uploads);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                progressDialog.dismiss();
            }
        });

        BottomNavigationView navView = findViewById(R.id.bottom_navigation);
        navView.setSelectedItemId(R.id.navigation_dashboard);
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

                    case R.id.navigation_notifications:
                        startActivity(new Intent(getApplicationContext(), ArtistActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return false;
                    case R.id.playlist:
                        startActivity(new Intent(getApplicationContext(), PlaylistActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return false;

                }

                return true;
            }
        });
    }

}