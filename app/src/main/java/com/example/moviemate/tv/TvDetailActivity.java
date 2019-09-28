package com.example.moviemate.tv;

import androidx.appcompat.app.AppCompatActivity;
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
import java.util.Scanner;

public class TvDetailActivity extends YouTubeBaseActivity {

    int flag = 0;
    ImageView mButtonPlay;
    String API_KEY = "b8f745c2d43033fd65ce3af63180c3c3";
    static String API_VIDEO;
    String videoLink;
    String key;
    private YouTubePlayerView youTubePlayerView;

    YouTubePlayer.OnInitializedListener onInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_detail_tv);

        youTubePlayerView = findViewById(R.id.video_view_tv);

        mButtonPlay = findViewById(R.id.button_play_tv);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(TvFragment.EXTRA_IMAGE);
        String imageUrl2 = intent.getStringExtra(TvFragment.EXTRA_IMAGE2);
        String title = intent.getStringExtra(TvFragment.EXTRA_NAME);
        String overview = intent.getStringExtra(TvFragment.EXTRA_OVERVIEW);
        String date = intent.getStringExtra(TvFragment.EXTRA_DATE);
        int popularity = intent.getIntExtra(TvFragment.EXTRA_POPULARITY,0);
        int id = intent.getIntExtra(TvFragment.EXTRA_ID, 0);
        int voteAverage = intent.getIntExtra(TvFragment.EXTRA_VOTE_AVERAGE,0);
        int voteCount = intent.getIntExtra(TvFragment.EXTRA_VOTE_COUNT,0);

        ImageView imageView = findViewById(R.id.image_view_detail_tv);
        ImageView imageView2 = findViewById(R.id.image_view_detail2_tv);
        TextView textViewTitle = findViewById(R.id.text_view_title_detail_tv);
        TextView textViewOverview = findViewById(R.id.text_view_overview_detail_tv);
        TextView textViewDate = findViewById(R.id.text_view_date_detail_tv);
        TextView textViewPopularity = findViewById(R.id.text_view_popularity_detail_tv);
        TextView textViewVoteAverage = findViewById(R.id.text_view_vote_average_detail_tv);
        TextView textViewVoteCount = findViewById(R.id.text_view_vote_count_detail_tv);

        imageView.setImageURI(Uri.parse(imageUrl));
        imageView2.setImageURI(Uri.parse(imageUrl2));
        textViewTitle.setText(title);
        textViewOverview.setText(overview);
        textViewDate.setText("Release : " + date);
        textViewPopularity.setText("Popularity : " + popularity);
        textViewVoteAverage.setText("Rating : " + voteAverage + "/10");
        textViewVoteCount.setText("Vote Count : " + voteCount);

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("tv")
                .appendPath(String.valueOf(id))
                .appendPath("videos")
                .appendQueryParameter("api_key", API_KEY)
                .appendQueryParameter("language", "en-US");

        API_VIDEO = builder.build().toString();
        URL searchURL = buildUrl();
        new TvDetailActivity.queryTask().execute(searchURL);

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    if(flag == 0) {
                        if(key != null) {
                            View button = findViewById(R.id.button_play_tv);
                            button.setVisibility(View.GONE);
                            View img = findViewById(R.id.image_view_detail2_tv);
                            img.setVisibility(View.GONE);
                            youTubePlayer.loadVideo(key);
                            youTubePlayer.play();
                            youTubePlayer.setShowFullscreenButton(false);
                        }
                        else {
                            Toast.makeText(TvDetailActivity.this, "Network issues, Please try again.", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText(TvDetailActivity.this, "Video do not exist", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                CharSequence fail = "Failured to Initialize!";
                Toast.makeText(getApplicationContext(), fail, Toast.LENGTH_LONG).show();
            }
        };

        mButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                youTubePlayerView.initialize(API_KEY, onInitializedListener);
            }
        });

    }

    public class queryTask extends AsyncTask<URL, Void, String> { //<params, progress, result>

        //this function works in background thread
        @Override
        protected String doInBackground(URL... urls) {
            URL searchURL = urls[0]; // what does this mean
            String searchResults = null;
            try {
                searchResults = getResponseFromHttpUrl(searchURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return searchResults;
        }

        //this function works in main thread
        @Override
        protected void onPostExecute(String s) {
            if (s != null && !s.equals("")) {
                try {
                    JSONObject ob = new JSONObject(s);
                    onResponse(ob);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //this function is made to fetch values from API using JSON
        public void onResponse(JSONObject response) throws JSONException {
            JSONArray jsonArray = response.getJSONArray("results");//puts api result array in jason array named jsonArray
            if(jsonArray.length()!=0) {
                flag = 0;
                JSONObject result = jsonArray.getJSONObject(0);
                key = result.getString("key");
            }
            else {
                flag = 1;
            }

            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https")
                    .authority("www.youtube.com")
                    .appendPath("watch")
                    .appendQueryParameter("v", key)
                    .appendQueryParameter("feature", "youtu.be");
            videoLink = builder.build().toString();
            Log.d(videoLink, "onResponse: ");
        }
    }

    //this function converts the API url into formatted url
    public static URL buildUrl() {
        URL url = null;
        try {

            url = new URL(API_VIDEO);
        } catch (MalformedURLException e) {
            e.printStackTrace(); // prints class name and error line of Throwable object
        }
        return url;
    }

    //this function is responsible for getting response from API
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();//what does this mean
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");//what does this Delimiter mean
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}

