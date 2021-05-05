package com.example.musicapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Model.GetSongs;
import com.example.musicapp.Model.SongModel;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.CustomHolder>{
    private  List<GetSongs> getSongs;
    Context context;

    public MyAdapter(List<GetSongs> getSongs) {
        this.getSongs = getSongs;
    }

    @NonNull
    @Override
    public MyAdapter.CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View view =layoutInflater.inflate(R.layout.row_layout,parent,false);
        return  new CustomHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.CustomHolder holder, int position) {

        holder.tv_SongName.setText(getSongs.get(position).getSong());
        holder.tv_Artist.setText(getSongs.get(position).getArtists());
        holder.tv_Url.setText(getSongs.get(position).getUrl());
        holder.tv_Image.setText(getSongs.get(position).getCover_image());
    }

    @Override
    public int getItemCount() {
        return getSongs.size();
    }

    public class CustomHolder extends RecyclerView.ViewHolder {
        TextView tv_SongName,tv_Artist,tv_Url,tv_Image;
        public CustomHolder(@NonNull View itemView) {
            super(itemView);
            tv_SongName = itemView.findViewById(R.id.title);
            tv_Artist = itemView.findViewById(R.id.artist);
            tv_Url = itemView.findViewById(R.id.url);

        }
    }


//    private List<SongModel> songList;
//
//    public MyAdapter(List<SongModel> songList) {
//        this.songList = songList;
//    }
//
//    @NonNull
//    @Override
//    public MyAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View view =layoutInflater.inflate(R.layout.row_layout,parent,false);
//        return  new CustomViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyAdapter.CustomViewHolder holder, int position) {
//
//        holder.tv_SongName.setText(songList.get(position).getSong());
//        holder.tv_Artist.setText(songList.get(position).getArtists());
//        holder.tv_Url.setText(songList.get(position).getUrl());
//        holder.tv_Image.setText(songList.get(position).getCover_image());
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return songList.size();//tra ve danh sach bai hat
//    }
//
//    public class CustomViewHolder extends  RecyclerView.ViewHolder{
//
//        TextView tv_SongName,tv_Artist,tv_Url,tv_Image;
//
//        public CustomViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            tv_SongName = itemView.findViewById(R.id.title);
//            tv_Artist = itemView.findViewById(R.id.artist);
//            tv_Url = itemView.findViewById(R.id.url);
//            tv_Image = itemView.findViewById(R.id.cover_image);
//        }
//    }
}
