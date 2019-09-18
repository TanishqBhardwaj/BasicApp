package com.example.moviemate.tv;


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

public class TvDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(TvFragment.EXTRA_IMAGE);
        String title = intent.getStringExtra(TvFragment.EXTRA_NAME);
        int popularity = intent.getIntExtra(TvFragment.EXTRA_POPULARITY,0);

        ImageView imageView = findViewById(R.id.image_view_detail);
        TextView textViewTitle = findViewById(R.id.text_view_title_detail);
        TextView textViewPopularity = findViewById(R.id.text_view_popularity_detail);

        imageView.setImageURI(Uri.parse(imageUrl));
        textViewTitle.setText(title);
        textViewPopularity.setText("Popularity : " + popularity);
    }
}

