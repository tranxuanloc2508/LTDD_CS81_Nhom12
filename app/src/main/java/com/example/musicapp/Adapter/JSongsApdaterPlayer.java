package com.example.musicapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Model.UpLoadSong;
import com.example.musicapp.Model.Utility;
import com.example.musicapp.R;

import java.util.List;

public class JSongsApdaterPlayer extends RecyclerView.Adapter<JSongsApdaterPlayer.SongsAdapterViewHolder>{

    private int selectedPosition;
    Context context;
    List<UpLoadSong> arrayListSongs;
    private RecyclerItemClickListener listener;

    public JSongsApdaterPlayer(Context context, List<UpLoadSong> arrayListSongs, RecyclerItemClickListener listener) {
        this.context = context;
        this.arrayListSongs = arrayListSongs;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SongsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.songs_rows,parent,false);

        return new SongsAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SongsAdapterViewHolder holder, int position) {

        UpLoadSong upLoadSong =  arrayListSongs.get(position);


        if (upLoadSong!=null){
            if (selectedPosition==position){
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimary));
                holder.iv_play_active.setVisibility(View.VISIBLE);

            }else {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.transparent));
                holder.iv_play_active.setVisibility(View.INVISIBLE);

            }
        }

        holder.tv_title.setText(upLoadSong.getSongTitle());
        holder.tv_artist.setText(upLoadSong.getArtist());
        String duration = Utility.convertDuration(Long.parseLong(upLoadSong.getSongDuration()));
        holder.tv_duration.setText(duration);

        holder.bind(upLoadSong,listener);
    }

    @Override
    public int getItemCount() {
        return arrayListSongs.size();
    }

    public class SongsAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title,tv_artist,tv_duration;
        ImageView iv_play_active;
        ;

        public SongsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_artist=itemView.findViewById(R.id.tv_artist);
            tv_duration=itemView.findViewById(R.id.tv_duration);
            iv_play_active = itemView.findViewById(R.id.iv_play_active);

        }

        public void bind(UpLoadSong upLoadSong,final RecyclerItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickListener(upLoadSong,getAdapterPosition());
                }
            });
        }
    }
    public interface RecyclerItemClickListener{

        void onClickListener(UpLoadSong upLoadSong,int postion);
    }
    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }
}
