package com.example.moviemate.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.moviemate.info.DevelopersAbout;
import com.example.moviemate.info.DevelopersFragment;
import com.example.moviemate.home.HomeFragment;
import com.example.moviemate.R;
import com.example.moviemate.tv.TvFragment;
import com.example.moviemate.movie.MoviesFragment;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.material.navigation.NavigationView;
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

public class MainActivity extends AppCompatActivity implements MovieAdapterMain.OnItemClickListener, NavigationView.OnNavigationItemSelectedListener {

    public final static String EXTRA_IMAGE = "imageUrl";
    public final static String EXTRA_TITLE = "title";
    public final static String EXTRA_POPULARITY = "popularity";

    final static String API_URL_POPULAR = "https://api.themoviedb.org/3/movie/popular?api_key=b8f745c2d43033fd65ce3af63180c3c3";
    private RecyclerView mRecyclerView;
    private MovieAdapterMain mMovieAdapterMain;
    private ArrayList<MovieItem> mMovieList;
    NavigationView navigationView;

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true); // it fixes the size of recycler view, which is responsible for better performance
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)); // it sets the layout of recycler view as linear
        mMovieList = new ArrayList<>();

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        setUpToolBar();
        URL searchURL = buildUrl();
        new MainActivity.queryTask().execute(searchURL);
    }

    public void sakshiInstaClick(View view) {
        openUrl1("https://www.instagram.com/sakshi_yadav_77/");
    }


    private void openUrl1(String url) {
        Uri uri = Uri.parse(url);
        Intent launchWeb = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(launchWeb);
    }

    public void sakshiFacebookClick(View view) {
        openUrl2("https://www.facebook.com/sakshi.yadav.140");
    }

    private void openUrl2(String url) {
        Uri uri = Uri.parse(url);
        Intent launchWeb = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(launchWeb);
    }

    public void sakshiGmailClick(View view) {
        openUrl3("https://www.gmail.com");
    }


    private void openUrl3(String url) {
        Uri uri = Uri.parse(url);
        Intent launchWeb = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(launchWeb);
    }


    public void tanishqInstaClick(View view) {
        openUrl4("https://www.instagram.com/tanishq_bhardwaj_9/");
    }


    private void openUrl4(String url) {
        Uri uri = Uri.parse(url);
        Intent launchWeb = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(launchWeb);
    }

    public void tanishqFacebookClick(View view) {
        openUrl5("https://www.facebook.com/sakshi.yadav.140");
    }


    private void openUrl5(String url) {
        Uri uri = Uri.parse(url);
        Intent launchWeb = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(launchWeb);
    }

    public void tanishqGmailClick(View view) {
        openUrl6("https://mail.google.com/mail/u/0/#inbox");
    }

    private void openUrl6(String url) {
        Uri uri = Uri.parse(url);
        Intent launchWeb = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(launchWeb);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, new HomeFragment()).commit();
                break;

            case R.id.nav_movies:
                getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, new MoviesFragment()).commit();
                break;

            case R.id.nav_TV_shows:
                getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, new TvFragment()).commit();
                break;

            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, new DevelopersAbout()).commit();
                Toast.makeText(this, "ABOUT US", Toast.LENGTH_LONG).show();
                break;

            case R.id.nav_dev:
                getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, new DevelopersFragment()).commit();
                Toast.makeText(this, "DEVELOPERS", Toast.LENGTH_LONG).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setUpToolBar() {
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar
                , R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }

    //Formation of intent on clicking images
    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class); //what does this mean
        MovieItem clickedItem = mMovieList.get(position);

        detailIntent.putExtra(EXTRA_IMAGE, clickedItem.getImageUrl());
        detailIntent.putExtra(EXTRA_TITLE, clickedItem.getTitle());
        detailIntent.putExtra(EXTRA_POPULARITY, clickedItem.getPopularity());

        startActivity(detailIntent);
    }

    public class queryTask extends AsyncTask<URL, Void, String> {

        //this function works in background thread
        @Override
        protected String doInBackground(URL... urls) {
            URL searchURL = urls[0]; // what does this mean
            String searchResults = null;
            try {
                searchResults = getResponseFromHttpUrl(searchURL);
            } catch (IOException e) {
                e.printStackTrace();//what does this mean
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
            String initialImageUrl = "http://image.tmdb.org/t/p/original";
            JSONArray jsonArray = response.getJSONArray("results");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject result = jsonArray.getJSONObject(i);
                String imageUrl = initialImageUrl.concat(result.getString("poster_path"));
                String title = result.getString("title");
                int popularity = result.getInt("popularity");
                mMovieList.add(new MovieItem(imageUrl, title, popularity));
            }
            mMovieAdapterMain = new MovieAdapterMain(MainActivity.this, mMovieList);
            mRecyclerView.setAdapter(mMovieAdapterMain);
            mMovieAdapterMain.setOnItemClickListener(MainActivity.this);
        }
    }

    //this function converts the API url into formatted url
    public static URL buildUrl() {
        Uri builtUri = Uri.parse(API_URL_POPULAR);
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
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