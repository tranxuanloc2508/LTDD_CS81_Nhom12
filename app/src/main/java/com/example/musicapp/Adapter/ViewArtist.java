package com.example.musicapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicapp.AlbumActivity;
import com.example.musicapp.ArtistActivity;
import com.example.musicapp.ListAlbumSongActivity;

import com.example.musicapp.ListArtistSongActivity;
import com.example.musicapp.Model.UploadAlbum;

import com.example.musicapp.R;

import java.util.List;

public class ViewArtist extends RecyclerView.Adapter<ViewArtist.MyViewHolder> {
    private Context mContext;
    private List<UploadAlbum> uploads;

    public ViewArtist(Context mContext, List<UploadAlbum> uploads) {
        this.mContext = mContext;
        this.uploads = uploads;
    }

    @NonNull
    @Override
    public ViewArtist.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater =LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.card_view_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewArtist.MyViewHolder holder, int position) {

        final UploadAlbum upload = uploads.get(position);
        holder.tv_book_text.setText(upload.getName());

        Glide.with(mContext).load(upload.getUrl()).into(holder.img_book_thumail);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, ListArtistSongActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("songCategory",upload.getSongsCategory());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_book_text;
        ImageView img_book_thumail;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_book_text = itemView.findViewById(R.id.book_title_id);
            img_book_thumail = itemView.findViewById(R.id.bok_img_id);
            cardView = itemView.findViewById(R.id.card_view_id);
        }
    }
}
