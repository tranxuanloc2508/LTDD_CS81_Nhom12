package com.example.musicapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Model.SongModel;

import java.util.List;

public class MyAdapter  {

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
