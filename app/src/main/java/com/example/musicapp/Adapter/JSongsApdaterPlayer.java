package com.example.musicapp.Adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Model.UpLoadSong;
import com.example.musicapp.Model.Utility;
import com.example.musicapp.PlayerActivity;
import com.example.musicapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.DOWNLOAD_SERVICE;
import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class JSongsApdaterPlayer extends RecyclerView.Adapter<JSongsApdaterPlayer.SongsAdapterViewHolder>{

    private int selectedPosition;
    DatabaseReference mref ;
    Integer currentSongIndex ;
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
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.design_default_color_primary));
                holder.iv_play_active.setVisibility(View.VISIBLE);
                holder.iv_download.setVisibility(View.VISIBLE);
                currentSongIndex= selectedPosition;

            }else {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent));
                holder.iv_play_active.setVisibility(View.INVISIBLE);
                holder.iv_download.setVisibility(View.INVISIBLE);
                currentSongIndex= selectedPosition;

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
        ImageView iv_play_active,iv_download;
        ArrayList<String> so = new ArrayList<>();


        public SongsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_artist=itemView.findViewById(R.id.tv_artist);
            tv_duration=itemView.findViewById(R.id.tv_duration);
            iv_play_active = itemView.findViewById(R.id.iv_play_active);
            iv_download=itemView.findViewById(R.id.download);


        }

        public void bind(UpLoadSong upLoadSong,final RecyclerItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickListener(upLoadSong,getAdapterPosition());

                }
            });
            mref =  FirebaseDatabase.getInstance().getReference("songs");
            iv_download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            for (DataSnapshot dss: snapshot.getChildren()){
//                                UpLoadSong getSongs = dss.getValue(UpLoadSong.class);
//
//
//                                getSongs.setmKey(dss.getKey());
//
//                                currentSongIndex = 0;

                            Uri url = Uri.parse(snapshot.child(String.valueOf(currentSongIndex+1)).child("songLink").getValue().toString());

                            String name= snapshot.child(String.valueOf(currentSongIndex+1)).child("songTitle").getValue().toString();
                            String title = URLUtil.guessFileName(String.valueOf(url), null, null);

                            DownloadManager.Request request = new DownloadManager.Request(url).setTitle(name);
                            String cookie = CookieManager.getInstance().getCookie(String.valueOf(url));
                            request.addRequestHeader("cookie", cookie)
                                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                                    .setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS, title);
                            DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
                            downloadManager.enqueue(request);
//                            Toast.makeText(this, "Start download ", Toast.LENGTH_SHORT).show();

//                        }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });

        }
    }


    private void dowloadfile(Context context, String fileName, String fileExtension, String des, String url) {

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context,des,fileName+fileExtension);

        downloadManager.enqueue(request);

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
