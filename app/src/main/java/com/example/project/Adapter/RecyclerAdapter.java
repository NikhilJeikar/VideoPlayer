package com.example.project.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project.Holder.FileHolder;
import com.example.project.R;
import com.example.project.VideoPlayerWindow;
import com.example.project.ViewHolder.RecyclerViewHolder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    ArrayList<FileHolder> Array = new ArrayList<>();
    Context context;
    DisplayMetrics displayMetrics;

    public RecyclerAdapter(Context context) {
        this.context = context;
    }

    public void Add(FileHolder holder) {
        Array.add(holder);
    }

    public void AddAll(ArrayList<FileHolder> array)
    {
        this.Array = array;
    }

    public void setDisplayMetrics(DisplayMetrics displayMetrics) {
        this.displayMetrics = displayMetrics;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_files, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
    holder.textView.setText(Array.get(position).file.getName());
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
    {
        if(Array.get(position).type == FileHolder.VIDEO)
        {
            Glide.with(context).load(Array.get(position).file).error(R.drawable.no_movie).into(holder.imageView);
        }
        else if(Array.get(position).type == FileHolder.AUDIO)
        {
            Glide.with(context).load(Array.get(position).file).error(R.drawable.no_music).into(holder.imageView);
        }
        else
        {
            Glide.with(context).load(Array.get(position).file).error(R.drawable.no_preview).into(holder.imageView);
        }

    }
    holder.cardView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, VideoPlayerWindow.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("Path",Array.get(holder.getAdapterPosition()).file.getPath());
            context.startActivity(intent);
        }
    });
    }
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return Array.size();
    }
}
