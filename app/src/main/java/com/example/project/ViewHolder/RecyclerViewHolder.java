package com.example.project.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView textView;
    public CardView cardView;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.FileName);
        imageView = itemView.findViewById(R.id.Image);
        cardView = itemView.findViewById(R.id.Card);
    }
}
