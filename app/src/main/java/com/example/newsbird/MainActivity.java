package com.example.newsbird;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.newsbird.Adapters.CategoryRVAdapter;
import com.example.newsbird.Adapters.NewsRVAdapter;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.categoryClickInterface {
    //b9c7e886c68c4391a0d7d39e56b3aa2a
    private RecyclerView categoryRV,newsRV;
    private ProgressBar PBloading;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryRVModal> categoryRVModalArrayList;
    private CategoryRVAdapter categoryRVAdapter;
    private  NewsRVAdapter newsRVAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsRV = findViewById(R.id.newsRV);
        categoryRV = findViewById(R.id.categoiesRV);
        PBloading = findViewById(R.id.pageLoading);
        articlesArrayList = new ArrayList<>();
        categoryRVModalArrayList = new ArrayList<>();
        newsRVAdapter = new NewsRVAdapter(articlesArrayList,this);
        categoryRVAdapter = new CategoryRVAdapter(categoryRVModalArrayList,this,this::onCategoryClick);
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsRVAdapter);
        categoryRV.setAdapter(categoryRVAdapter);
        getNews("Business");
        getCategories();
        newsRVAdapter.notifyDataSetChanged();

    }

    @Override
    public void onCategoryClick(int position) {
        String category = categoryRVModalArrayList.get(position).getCategory();
        getNews(category);
    }

    private void getCategories(){
        categoryRVModalArrayList.add(new CategoryRVModal("All","https://img.freepik.com/premium-vector/green-earth_54199-1938.jpg"));
        categoryRVModalArrayList.add(new CategoryRVModal("Technology","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRrYY7vTR6MeObJdnxt2JiJOzcTrh22OI6vsQB9S-S7DZk_No0UsI17SocccDL7capP2YSU9QGOsDY&usqp=CAU&ec=48665701"));
        categoryRVModalArrayList.add(new CategoryRVModal("Sports","https://teamo-sports.com/wp-content/uploads/2021/04/sports.jpeg"));
        categoryRVModalArrayList.add(new CategoryRVModal("Business","https://www.simplilearn.com/ice9/free_resources_article_thumb/What_Is_Business_Analysis_And_Why_Every_Company_Needs_A_Business_Analyst.jpg"));
        categoryRVModalArrayList.add(new CategoryRVModal("General","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTvs9T8cTy5UCvnfnfGeousuzbcr1FkSShkEXFsw2PP2VD4E12qRwZHIIM3ZBboQjObue_7cALwaWs&usqp=CAU&ec=48665701"));
        categoryRVModalArrayList.add(new CategoryRVModal("Entertainment","https://etimg.etb2bimg.com/photo/81478822.cms"));
        categoryRVModalArrayList.add(new CategoryRVModal("Health","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRh7htozBf6YyXLpebD94XdD0RS0vDCoeaDd9wRtJaxy_0S4qdO8BQlx6oFUC_H-vzEN5Uk2hD-n0k&usqp=CA"));
        categoryRVAdapter.notifyDataSetChanged();
    }
    private void getNews(String category){
        PBloading.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryURL = "https://newsapi.org/v2/top-headlines?country=in&category="+category+"&apiKey=b9c7e886c68c4391a0d7d39e56b3aa2a";
        String url = "https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=b9c7e886c68c4391a0d7d39e56b3aa2a";
        String BASE_URL = "https://newsapi.org/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModel> call;
        if(category.equals("All")){
            call = retrofitAPI.getAllNews(url);
        }else{
            call= retrofitAPI.getNewsByCategory(categoryURL);
        }

        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                NewsModel newsModel = response.body();
                PBloading.setVisibility(View.GONE);
                ArrayList<Articles> articles = newsModel.getArticles();
                for(int i = 0;i< articles.size();i++){
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(),
                            articles.get(i).getDescription(),
                            articles.get(i).getUrlToImage(),
                            articles.get(i).getUrl(),
                            articles.get(i).getContent()));
                }
                newsRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to get NEWS", Toast.LENGTH_SHORT).show();
            }
        });
    }
}