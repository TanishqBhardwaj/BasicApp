package com.example.moviemate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    final static String apiUrl = "https://api.themoviedb.org/3/movie/popular?api_key=b8f745c2d43033fd65ce3af63180c3c3";
    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private ArrayList<MovieItem> mMovieList;

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
//    ViewPager viewPager;
//    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);





        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true); // what does this mean
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // what does this mean
        mMovieList = new ArrayList<>();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        setUpToolBar();
        URL searchURL = buildUrl();
        new queryTask().execute(searchURL);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
                break;

            case R.id.nav_movies:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MoviesFragment()).commit();
                break;
            case R.id.nav_TV_shows:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TvFragment()).commit();
            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new DevelopersAbout()).commit();
                Toast.makeText(this, "ABOUT US", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_dev:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new DevelopersFragment()).commit();
                Toast.makeText(this, "DEVELOPERS", Toast.LENGTH_SHORT).show();
                break;


        }
        drawerLayout.closeDrawer(GravityCompat.START);


        return true;
    }


    private void setUpToolBar() {
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar
                , R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
//
//        viewPager = findViewById(R.id.pager);
//        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
//        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    public class queryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL searchURL  = urls[0]; // what does this mean
            String searchResults = null;
            try {
                searchResults = getResponseFromHttpUrl(searchURL);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return searchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s!=null && !s.equals("")) {
                try {
                    JSONObject ob = new JSONObject(s);
                    onResponse(ob);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        public void onResponse(JSONObject response) throws JSONException {
            String initialImageUrl = "http://image.tmdb.org/t/p/original";
            JSONArray jsonArray = response.getJSONArray("results");

            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject result = jsonArray.getJSONObject(i);
                String imageUrl = initialImageUrl.concat(result.getString("poster_path"));
                String title = result.getString("title");
                int popularity = result.getInt("popularity");
                mMovieList.add(new MovieItem(imageUrl, title, popularity));
            }
            mMovieAdapter = new MovieAdapter(MainActivity.this, mMovieList);
            mRecyclerView.setAdapter(mMovieAdapter);
        }
    }

    public static URL buildUrl() {
        Uri builtUri = Uri.parse(apiUrl);
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            }
            else {
                return null;
            }
        }
        finally {
            urlConnection.disconnect();
        }
    }

}