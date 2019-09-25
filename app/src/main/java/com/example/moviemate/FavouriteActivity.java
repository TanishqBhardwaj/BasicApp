package com.example.moviemate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviemate.R;
import com.example.moviemate.home.HomeFragment;
import com.example.moviemate.main.DetailActivity;
import com.example.moviemate.tv.TvAdapter;
import com.example.moviemate.tv.TvFragment;
import com.example.moviemate.tv.TvItem;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class FavouriteActivity extends AppCompatActivity {

//    int flag = 0;
//    ImageView mButtonPlay;
//    String API_KEY = "b8f745c2d43033fd65ce3af63180c3c3";
//    static String API_VIDEO;
//    String videoLink;
//    String key;
//    private YouTubePlayerView youTubePlayerView;
//    private static final int RECOVERY_DIALOG_REQUEST = 1;
//    YouTubePlayer.OnInitializedListener onInitializedListener;

    String title;
    private ArrayList<TvItem> horizontal_collection;
    private TvAdapter tAdapter;
    private RecyclerView horizontal_list;
    public ArrayList<FavouriteItem> cart_collection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);

        setContentView(R.layout.favourite);
        setAdapter();

        Intent intent = getIntent();
        String imageUrl2 = intent.getStringExtra(TvFragment.EXTRA_IMAGE2);
        // String imageUrl2 = intent.getStringExtra(TvFragment.EXTRA_IMAGE2);
        String title = intent.getStringExtra(TvFragment.EXTRA_NAME);
        String overview = intent.getStringExtra(TvFragment.EXTRA_OVERVIEW);
        String date = intent.getStringExtra(TvFragment.EXTRA_DATE);
        int popularity = intent.getIntExtra(TvFragment.EXTRA_POPULARITY, 0);
        int id = intent.getIntExtra(TvFragment.EXTRA_ID, 0);
        int voteAverage = intent.getIntExtra(TvFragment.EXTRA_VOTE_AVERAGE, 0);
        int voteCount = intent.getIntExtra(TvFragment.EXTRA_VOTE_COUNT, 0);
        ImageView imageView = findViewById(R.id.image_view_detail_tv);
        TextView textViewTitle = findViewById(R.id.text_view_title_detail_tv);
        TextView textViewOverview = findViewById(R.id.text_view_overview_detail_tv);
        TextView textViewDate = findViewById(R.id.text_view_date_detail_tv);
        TextView textViewPopularity = findViewById(R.id.text_view_popularity_detail_tv);
        TextView textViewVoteAverage = findViewById(R.id.text_view_vote_average_detail_tv);
        TextView textViewVoteCount = findViewById(R.id.text_view_vote_count_detail_tv);

        imageView.setImageURI(Uri.parse(imageUrl2));
        textViewTitle.setText(title);
        textViewOverview.setText(overview);
        textViewDate.setText("Release Date : " + date);
        textViewPopularity.setText("Popularity : " + popularity);
        textViewVoteAverage.setText("Average Vote : " + voteAverage);
        textViewVoteCount.setText("Total Votes : " + voteCount);

    }

    public void setAdapter() {

        horizontal_list = findViewById(R.id.recycler_view_popular_tv);
        horizontal_list.setLayoutManager(new LinearLayoutManager(this));
        horizontal_list.setHasFixedSize(true);
        horizontal_collection = new ArrayList<>();
        tAdapter = new TvAdapter(this, horizontal_collection);
        horizontal_list.setAdapter(tAdapter);


    }
}

