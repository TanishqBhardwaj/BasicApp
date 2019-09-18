package com.example.moviemate.main;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviemate.R;
import com.example.moviemate.home.HomeFragment;
import com.example.moviemate.main.MainActivity;
import com.facebook.drawee.backends.pipeline.Fresco;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(HomeFragment.EXTRA_IMAGE);
        String title = intent.getStringExtra(HomeFragment.EXTRA_TITLE);
        int popularity = intent.getIntExtra(HomeFragment.EXTRA_POPULARITY,0);
        String overview = intent.getStringExtra(HomeFragment.EXTRA_OVERVIEW);
        String releaseDate = intent.getStringExtra(HomeFragment.EXTRA_RELEASE_DATE);
        String adult = intent.getStringExtra(HomeFragment.EXTRA_ADULT);
        ImageView imageView = findViewById(R.id.image_view_detail);
        TextView textViewTitle = findViewById(R.id.text_view_title_detail);
        TextView textViewPopularity = findViewById(R.id.text_view_popularity_detail);
        TextView textViewOverview = findViewById(R.id.text_view_over_view_detail);
        TextView textViewReleaseDate = findViewById(R.id.text_view_release_date_detail);
        TextView textViewAdult = findViewById(R.id.text_view_adult_detail);

        imageView.setImageURI(Uri.parse(imageUrl));
        textViewTitle.setText(title);
        textViewPopularity.setText("Popularity : " + popularity);
        textViewOverview.setText(overview);
        textViewAdult.setText(adult);
        textViewReleaseDate.setText(releaseDate);
    }
}
