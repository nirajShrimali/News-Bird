package com.example.newsbird.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsbird.Articles;
import com.example.newsbird.MainActivity;
import com.example.newsbird.NewsDetailActivity;
import com.example.newsbird.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsRVAdapter extends RecyclerView.Adapter<NewsRVAdapter.ViewHolder> {

    private ArrayList<Articles> articlesArrayList;
    private Context context;

    public NewsRVAdapter(ArrayList<Articles> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_rv_item,parent,false);
        return new NewsRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRVAdapter.ViewHolder holder, int position) {
        Articles articles = articlesArrayList.get(position);
        holder.titleTV.setText(articles.getTitle());
        holder.subTitleTV.setText(articles.getDescription());
        Picasso.get().load(articles.getUrl()).into(holder.newsIV);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("title", articles.getTitle());
                intent.putExtra("content",articles.getContent());
                intent.putExtra("desc",articles.getDescription());
                intent.putExtra("image",articles.getUrlToImage());
                intent.putExtra("url",articles.getUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Animation move = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.move);
        private TextView titleTV,subTitleTV;
        private ImageView newsIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.TVNewsHeading);
            subTitleTV = itemView.findViewById(R.id.TVNewsSubtitle);
            newsIV = itemView.findViewById(R.id.IVNews);
            titleTV.startAnimation(move);
            subTitleTV.startAnimation(move);
            newsIV.startAnimation(move);
        }
    }
}
