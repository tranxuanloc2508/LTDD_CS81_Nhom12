package com.example.musicapp.Adapter;




import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicapp.Model.SlideItem;
import com.example.musicapp.R;

import java.util.List;

public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.SildeViewHolder>{

    //tao list image
    private List<SlideItem> sildeItems;

    public SlideAdapter(List<SlideItem> sildeItems) {
        this.sildeItems = sildeItems;
    }

    Context context;

//    public SlideAdapter(List<SlideItem> sildeItems) {
//        this.sildeItems = sildeItems;
//    }


    @NonNull
    @Override
    public SildeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new SildeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.silde_item,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull SildeViewHolder holder, int position) {
        holder.setImage(sildeItems.get(position));
    }

    @Override
    public int getItemCount() {
        return sildeItems.size();
    }

    class SildeViewHolder extends RecyclerView.ViewHolder {

        private ImageView cover_image;
        public SildeViewHolder(@NonNull View itemView) {
            super(itemView);
            cover_image = itemView.findViewById(R.id.image_silde);
        }
        void  setImage(SlideItem sildeItem)
        {

            Glide.with(context)
                    .load(sildeItem.getCover_image())
                    .override(300,300)
                    .into(cover_image);

        }
    }

}
