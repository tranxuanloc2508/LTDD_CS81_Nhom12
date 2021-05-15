package com.example.musicapp.Adapter;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.AlbumActivity;
import com.example.musicapp.ListSongActivity;
import com.example.musicapp.ListSonggsActivity;
import com.example.musicapp.LoginActivity;
import com.example.musicapp.MainActivity;
import com.example.musicapp.Model.Contants;
import com.example.musicapp.Model.UpLoadSong;
import com.example.musicapp.Model.UploadAlbum;
import com.example.musicapp.Model.Utility;
import com.example.musicapp.PlayerActivity;
import com.example.musicapp.R;
import com.example.musicapp.SongsListActivity;
import com.example.musicapp.UploadAlbummActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.DOWNLOAD_SERVICE;
import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class JSongsApdaterPlayer extends RecyclerView.Adapter<JSongsApdaterPlayer.SongsAdapterViewHolder>{

    private int selectedPosition;
    DatabaseReference mref,mref2 ;
    Integer currentSongIndex ;
    Context context;
    private static int like=0;
    int likee=0;
    private static int dem;
    private  int numId = ++dem;


    List<UpLoadSong> arrayListSongs;
    //    private OnNoteListener mOnNote;
    private RecyclerItemClickListener listener;
    StorageReference storageReference;
    private Uri fileFath;

    public JSongsApdaterPlayer(Context context, List<UpLoadSong> arrayListSongs, RecyclerItemClickListener listener) {
        this.context = context;
        this.arrayListSongs = arrayListSongs;
        this.listener = listener;
//        this.mOnNote = onNoteListener;
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



    public class SongsAdapterViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_title,tv_artist,tv_duration;
        ImageView iv_play_active,iv_download,a,heart;
        ArrayList<String> so = new ArrayList<>();
        OnNoteListener onNoteListener;


        public SongsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_artist=itemView.findViewById(R.id.tv_artist);
            tv_duration=itemView.findViewById(R.id.tv_duration);
            iv_play_active = itemView.findViewById(R.id.iv_play_active);
            heart= itemView.findViewById(R.id.heart1);
            a = itemView.findViewById(R.id.a);
            iv_download=itemView.findViewById(R.id.download);
            this.onNoteListener= onNoteListener;

        }

        public void bind(UpLoadSong upLoadSong,final RecyclerItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickListener(upLoadSong,getAdapterPosition());


                }
            });
            mref =  FirebaseDatabase.getInstance().getReference("songs");
            mref2= FirebaseDatabase.getInstance().getReference("playlist");

            heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(likee==0){
                        mref2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                dem= Integer.parseInt(String.valueOf(snapshot.getChildrenCount()));

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                        mref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                String tiltle = snapshot.child(String.valueOf(currentSongIndex + 1)).child("songTitle").getValue().toString();
                                String cate = snapshot.child(String.valueOf(currentSongIndex + 1)).child("songCategory").getValue().toString();
                                String album = snapshot.child(String.valueOf(currentSongIndex + 1)).child("album_art").getValue().toString();
                                String dura = snapshot.child(String.valueOf(currentSongIndex + 1)).child("songDuration").getValue().toString();
                                String link = snapshot.child(String.valueOf(currentSongIndex + 1)).child("songLink").getValue().toString();

                                UpLoadSong upLoadSong = new UpLoadSong(cate, tiltle, cate, album, dura, link);
                                String uploadID = mref2.push().getKey();
                                mref2.child(String.valueOf(dem+1)).setValue(upLoadSong);
                                heart.setImageResource(R.drawable.heart);
                                likee=1;

                            }



                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }

                        });

                    }else{
                        heart.setImageResource(R.drawable.hear2);
                        likee=0;
                    }
                }



            });

            iv_download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {


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
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });
            a.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, UploadAlbummActivity.class);

                    context.startActivity(i);
                }
            });

        }
//        public String getFileExtension(Uri uri){
//            ContentResolver cr = getContentResolver();
//            MimeTypeMap mime = MimeTypeMap.getSingleton();
//            return mime.getMimeTypeFromExtension(cr.getType(uri));
//        }


    }
    public interface OnNoteListener{
        void onNoteClick(int position);

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
