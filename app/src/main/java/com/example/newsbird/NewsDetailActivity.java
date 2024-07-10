package com.example.newsbird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {
    String title,content,desc,imageUrl,url;
    private TextView subTitle,Title,Content;
    private ImageView newsImage;
    private Button readFullNews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        desc = getIntent().getStringExtra("desc");
        imageUrl = getIntent().getStringExtra("image");
        url = getIntent().getStringExtra("url");
        readFullNews = findViewById(R.id.btnReadFullNews);

        subTitle = findViewById(R.id.TVNewsSubTitleDetail);
        Content = findViewById(R.id.TVNewsContentDetail);
        Title = findViewById(R.id.TVNewsHeadingDetail);
        newsImage = findViewById(R.id.IVNewsDetail);
        Picasso.get().load(url).into(newsImage);
        subTitle.setText(desc);
        Content.setText(content);
        Title.setText(title);

        readFullNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(imageUrl));
                startActivity(intent);
            }
        });
    }
}