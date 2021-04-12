package com.example.musicapp.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicapp.R;

import java.util.List;

public class Lyrics extends RecyclerView.Adapter<Lyrics.SildeViewHolder>{

    private List<SlideLyrics> lyrics;

    Context context;
    public Lyrics(List<SlideLyrics> lyrics) {
        this.lyrics = lyrics;
    }

    @NonNull
    @Override
    public Lyrics.SildeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new SildeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_lyrics,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SildeViewHolder holder, int position) {

        holder.setImage(lyrics.get(position));
    }

    @Override
    public int getItemCount() {
        return lyrics.size();
    }

    public class SildeViewHolder extends RecyclerView.ViewHolder {
        private ImageView lyrics;
        public SildeViewHolder(@NonNull View itemView) {
            super(itemView);
            lyrics = itemView.findViewById(R.id.image_lyrics);
        }
        void  setImage(SlideLyrics slideLyricslyric)
        {

            Glide.with(context)
                    .load(slideLyricslyric.getLyrics())
                    .override(300,300)
                    .into(lyrics);

        }
    }
}
