package com.example.moviemate.movie;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.moviemate.main.DetailActivity;
import com.example.moviemate.main.MovieItem;
import com.example.moviemate.R;
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

public class MoviesFragment extends Fragment implements MovieAdapter.OnItemClickListener {

    public final static String EXTRA_IMAGE = "imageUrl";
    public final static String EXTRA_TITLE = "title";
    public final static String EXTRA_POPULARITY = "popularity";


    private RecyclerView mRecyclerViewLatest;
    private RecyclerView mRecyclerViewUpcoming;
    private RecyclerView mRecyclerViewNowPlaying;
    private RecyclerView mRecyclerViewPopular;
    private RecyclerView mRecyclerViewTopRated;
    private MovieAdapter mMovieAdapter;
    SearchView searchView;

    View v;

    final static String API_URL_POPULAR = "https://api.themoviedb.org/3/movie/popular?api_key=b8f745c2d43033fd65ce3af63180c3c3";
    final static String API_URL_TOP_RATED = "https://api.themoviedb.org/3/movie/top_rated?api_key=b8f745c2d43033fd65ce3af63180c3c3";
    final static String API_URL_NOW_PLAYING = "https://api.themoviedb.org/3/movie/now_playing?api_key=b8f745c2d43033fd65ce3af63180c3c3";




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_movies, container, false);
        mRecyclerViewPopular = v.findViewById(R.id.recycler_view_popular_movie);
        // it fixes the size of recycler view, which is responsible for better performance
        mRecyclerViewPopular.setHasFixedSize(true);
        mRecyclerViewPopular.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));// it sets the layout of recycler view as linear

        mRecyclerViewTopRated = v.findViewById(R.id.recycler_view_top_rated_movie);
        // it fixes the size of recycler view, which is responsible for better performance
        mRecyclerViewTopRated.setHasFixedSize(true);
        mRecyclerViewTopRated.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));// it sets the layout of recycler view as linear


        mRecyclerViewUpcoming = v.findViewById(R.id.recycler_view_upcoming_movie);
        // it fixes the size of recycler view, which is responsible for better performance
        mRecyclerViewUpcoming.setHasFixedSize(true);
        mRecyclerViewUpcoming.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));


        mRecyclerViewNowPlaying = v.findViewById(R.id.recycler_view_now_playing_movie);
        // it fixes the size of recycler view, which is responsible for better performance
        mRecyclerViewNowPlaying.setHasFixedSize(true);
        mRecyclerViewNowPlaying.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));



        setHasOptionsMenu(true);
        return v;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mMovieAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        URL searchURLPopular = buildUrlPopular();
        new MoviesFragment.queryTaskPopular().execute(searchURLPopular);

        URL searchURLTopRated = buildUrlTopRated();
        new MoviesFragment.queryTaskTopRated().execute(searchURLTopRated);

        URL searchURLUpcoming = buildUrlUpcoming();
        new MoviesFragment.queryTaskUpcoming().execute(searchURLUpcoming);


        URL searchURLNowPlaying = buildUrlNowPlaying();
        new MoviesFragment.queryTaskNowPlaying().execute(searchURLNowPlaying);
    }

    //Formation of intent on clicking images
    @Override
    public void onItemClick(int position, ArrayList<MovieItem> movieItemArrayList) {
        Intent detailIntent = new Intent(getActivity(), DetailActivity.class); //what does this mean
        MovieItem clickedItem = movieItemArrayList.get(position);

        detailIntent.putExtra("imageUrl", clickedItem.getImageUrl());
        detailIntent.putExtra(EXTRA_TITLE, clickedItem.getTitle());
        detailIntent.putExtra(EXTRA_POPULARITY, clickedItem.getPopularity());

        startActivity(detailIntent);
    }

    public class queryTaskPopular extends AsyncTask<URL, Void, String>{

        private ArrayList<MovieItem> mMovieList = new ArrayList<>();

        //this function works in background thread
        @Override
        protected String doInBackground(URL... urls) {
            URL searchURLPopular = urls[0]; // what does this mean
            String searchResults = null;
            try {
                searchResults = getResponseFromHttpUrl(searchURLPopular);
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
            mMovieAdapter = new MovieAdapter(getContext(), mMovieList);
            mRecyclerViewPopular.setAdapter(mMovieAdapter);
            mMovieAdapter.setOnItemClickListener(MoviesFragment.this);
        }
    }

    public class queryTaskTopRated extends AsyncTask<URL, Void, String>{
        private ArrayList<MovieItem> mMovieList = new ArrayList<>();

        //this function works in background thread
        @Override
        protected String doInBackground(URL... urls) {
            URL searchURLTopRated = urls[0]; // what does this mean
            String searchResults = null;
            try {
                searchResults = getResponseFromHttpUrl(searchURLTopRated);
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
            mMovieAdapter = new MovieAdapter(getContext(), mMovieList);
            mRecyclerViewTopRated.setAdapter(mMovieAdapter);
            mMovieAdapter.setOnItemClickListener(MoviesFragment.this);
        }
    }










    public class queryTaskUpcoming extends AsyncTask<URL, Void, String>{

        private ArrayList<MovieItem> mMovieList = new ArrayList<>();

        //this function works in background thread
        @Override
        protected String doInBackground(URL... urls) {
            URL searchURLUpcoming = urls[0]; // what does this mean
            String searchResults = null;
            try {
                searchResults = getResponseFromHttpUrl(searchURLUpcoming);
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
            mMovieAdapter = new MovieAdapter(getContext(), mMovieList);
            mRecyclerViewUpcoming.setAdapter(mMovieAdapter);
            mMovieAdapter.setOnItemClickListener(MoviesFragment.this);
        }
    }









    public class queryTaskNowPlaying extends AsyncTask<URL, Void, String>{

        private ArrayList<MovieItem> mMovieList = new ArrayList<>();

        //this function works in background thread
        @Override
        protected String doInBackground(URL... urls) {
            URL searchURLNowPlaying = urls[0]; // what does this mean
            String searchResults = null;
            try {
                searchResults = getResponseFromHttpUrl(searchURLNowPlaying);
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
            mMovieAdapter = new MovieAdapter(getContext(), mMovieList);
            mRecyclerViewNowPlaying.setAdapter(mMovieAdapter);
            mMovieAdapter.setOnItemClickListener(MoviesFragment.this);
        }
    }


















    //this function converts the API url into formatted url
    public static URL buildUrlPopular() {
        Uri builtUri = Uri.parse(API_URL_POPULAR);
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildUrlTopRated() {
        Uri builtUri = Uri.parse(API_URL_TOP_RATED);
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }



    public static URL buildUrlUpcoming() {
        Uri builtUri = Uri.parse(API_URL_NOW_PLAYING);
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }



    public static URL buildUrlNowPlaying() {
        Uri builtUri = Uri.parse(API_URL_NOW_PLAYING);
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