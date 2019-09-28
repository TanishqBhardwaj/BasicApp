package com.example.moviemate.movie.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.moviemate.main.UI.DetailActivity;
import com.example.moviemate.main.Model.MovieItem;
import com.example.moviemate.R;
import com.example.moviemate.movie.Adapter.MovieAdapter;

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
    public final static String EXTRA_ID = "id";
    public final static String EXTRA_RATING = "vote_average";
    public final static String EXTRA_VOTE_COUNT = "vote_count";
    public final static String EXTRA_OVERVIEW = "overview";
    public final static String EXTRA_RELEASE_DATE = "release_date";
    public final static String EXTRA_IMAGE2 = "imageUrl2";
    public final static String EXTRA_TYPE = "media_type";

    private static RecyclerView mRecyclerViewUpcoming;
    private static RecyclerView mRecyclerViewNowPlaying;
    private static RecyclerView mRecyclerViewPopular;
    private static RecyclerView mRecyclerViewTopRated;
    private static MovieAdapter mMovieAdapter;
    SearchView searchView;
    ProgressBar progressBarPopular;
    ProgressBar progressBarTopRated;
    ProgressBar progressBarNowPlaying;
    ProgressBar progressBarUpcoming;

    View v;

    final static String API_URL_POPULAR = "https://api.themoviedb.org/3/movie/popular?api_key=b8f745c2d43033fd65ce3af63180c3c3";
    final static String API_URL_TOP_RATED = "https://api.themoviedb.org/3/movie/top_rated?api_key=b8f745c2d43033fd65ce3af63180c3c3";
    final static String API_URL_NOW_PLAYING = "https://api.themoviedb.org/3/movie/now_playing?api_key=b8f745c2d43033fd65ce3af63180c3c3";
    final static String API_URL_UPCOMING = "https://api.themoviedb.org/3/movie/upcoming?api_key=b8f745c2d43033fd65ce3af63180c3c3";

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//
//
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_movies, container, false);
        if(!isConnected(getActivity())){ buildDialog(getActivity()).show();}
        else {

            URL searchURLPopular = buildUrlPopular();
            new MoviesFragment.queryTaskPopular().execute(searchURLPopular);

            URL searchURLTopRated = buildUrlTopRated();
            new MoviesFragment.queryTaskTopRated().execute(searchURLTopRated);

            URL searchURLUpcoming = buildUrlUpcoming();
            new MoviesFragment.queryTaskUpcoming().execute(searchURLUpcoming);


            URL searchURLNowPlaying = buildUrlNowPlaying();
            new MoviesFragment.queryTaskNowPlaying().execute(searchURLNowPlaying);

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
        }




        return v;
    }

//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//    }

    //Formation of intent on clicking images
    @Override
    public void onItemClick(int position, ArrayList<MovieItem> movieItemArrayList) {
        Intent detailIntent = new Intent(getActivity(), DetailActivity.class); //what does this mean
        MovieItem clickedItem = movieItemArrayList.get(position);

        detailIntent.putExtra(EXTRA_IMAGE, clickedItem.getImageUrl());
        detailIntent.putExtra(EXTRA_TITLE, clickedItem.getTitle());
        detailIntent.putExtra(EXTRA_POPULARITY, clickedItem.getPopularity());
        detailIntent.putExtra(EXTRA_ID, clickedItem.getId());
        detailIntent.putExtra(EXTRA_RATING, clickedItem.getRating());
        detailIntent.putExtra(EXTRA_VOTE_COUNT, clickedItem.getVoteCount());
        detailIntent.putExtra(EXTRA_OVERVIEW, clickedItem.getOverview());
        detailIntent.putExtra(EXTRA_RELEASE_DATE, clickedItem.getReleaseDate());
        detailIntent.putExtra(EXTRA_IMAGE2, clickedItem.getImageUrl2());
        detailIntent.putExtra(EXTRA_TYPE, clickedItem.getType());

        startActivity(detailIntent);
    }

    public class queryTaskPopular extends AsyncTask<URL, Void, String>{

        private ArrayList<MovieItem> mMovieList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarPopular = v.findViewById(R.id.progress_bar_popular_movie);
            progressBarPopular.setVisibility(View.VISIBLE);
        }

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
                    progressBarPopular.setVisibility(View.GONE);
                    JSONObject ob = new JSONObject(s);
                    onResponse(ob);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //this function is made to fetch values from API using JSON
        public void onResponse(JSONObject response) throws JSONException {
            String title, releaseDate;
            String initialImageUrl = "http://image.tmdb.org/t/p/original";
            JSONArray jsonArray = response.getJSONArray("results");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject result = jsonArray.getJSONObject(i);
                    String imageUrl = initialImageUrl.concat(result.getString("poster_path"));
                    String imageUrl2 = initialImageUrl.concat(result.getString("backdrop_path"));
                    title = result.getString("title");
                    releaseDate = result.getString("release_date");
                    int popularity = result.getInt("popularity");
                    int id = result.getInt("id");
                    int rating = result.getInt("vote_average");
                    int voteCount = result.getInt("vote_count");
                    String overview = result.getString("overview");
                    mMovieList.add(new MovieItem(imageUrl, title, popularity, id, rating, voteCount, overview, releaseDate,
                            imageUrl2));
                }
                mMovieAdapter = new MovieAdapter(getContext(), mMovieList);
                mRecyclerViewPopular.setAdapter(mMovieAdapter);
            mMovieAdapter.setOnItemClickListener(MoviesFragment.this);
        }
    }

    public class queryTaskTopRated extends AsyncTask<URL, Void, String> {
        private ArrayList<MovieItem> mMovieList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarTopRated = v.findViewById(R.id.progress_bar_top_rated_movie);
            progressBarTopRated.setVisibility(View.VISIBLE);
        }

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
                    progressBarTopRated.setVisibility(View.GONE);
                    JSONObject ob = new JSONObject(s);
                    onResponse(ob);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //this function is made to fetch values from API using JSON
        public void onResponse(JSONObject response) throws JSONException {
            String title, releaseDate;
            String initialImageUrl = "http://image.tmdb.org/t/p/original";
            JSONArray jsonArray = response.getJSONArray("results");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject result = jsonArray.getJSONObject(i);
                String imageUrl = initialImageUrl.concat(result.getString("poster_path"));
                String imageUrl2 = initialImageUrl.concat(result.getString("backdrop_path"));
                    title = result.getString("title");
                    releaseDate = result.getString("release_date");
                int popularity = result.getInt("popularity");
                int id = result.getInt("id");
                int rating = result.getInt("vote_average");
                int voteCount = result.getInt("vote_count");
                String overview = result.getString("overview");
                mMovieList.add(new MovieItem(imageUrl, title, popularity, id, rating, voteCount, overview, releaseDate,
                        imageUrl2));
            }
            mMovieAdapter = new MovieAdapter(getContext(), mMovieList);
            mRecyclerViewTopRated.setAdapter(mMovieAdapter);
            mMovieAdapter.setOnItemClickListener(MoviesFragment.this);
        }
    }

    public class queryTaskUpcoming extends AsyncTask<URL, Void, String>{

        private ArrayList<MovieItem> mMovieList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarUpcoming = v.findViewById(R.id.progress_bar_upcoming_movie);
            progressBarUpcoming.setVisibility(View.VISIBLE);
        }

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
                    progressBarUpcoming.setVisibility(View.GONE);
                    JSONObject ob = new JSONObject(s);
                    onResponse(ob);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //this function is made to fetch values from API using JSON
        public void onResponse(JSONObject response) throws JSONException {
            String title, releaseDate;
            String initialImageUrl = "http://image.tmdb.org/t/p/original";
            JSONArray jsonArray = response.getJSONArray("results");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject result = jsonArray.getJSONObject(i);
                String imageUrl = initialImageUrl.concat(result.getString("poster_path"));
                String imageUrl2 = initialImageUrl.concat(result.getString("backdrop_path"));
                    title = result.getString("title");
                    releaseDate = result.getString("release_date");
                int popularity = result.getInt("popularity");
                int id = result.getInt("id");
                int rating = result.getInt("vote_average");
                int voteCount = result.getInt("vote_count");
                String overview = result.getString("overview");
                mMovieList.add(new MovieItem(imageUrl, title, popularity, id, rating, voteCount, overview, releaseDate,
                        imageUrl2));
            }
            mMovieAdapter = new MovieAdapter(getContext(), mMovieList);
            mRecyclerViewUpcoming.setAdapter(mMovieAdapter);
            mMovieAdapter.setOnItemClickListener(MoviesFragment.this);
        }
    }

    public class queryTaskNowPlaying extends AsyncTask<URL, Void, String>{

        private ArrayList<MovieItem> mMovieList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarNowPlaying = v.findViewById(R.id.progress_bar_now_playing_movie);
            progressBarNowPlaying.setVisibility(View.VISIBLE);
        }

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
                    progressBarNowPlaying.setVisibility(View.GONE);
                    JSONObject ob = new JSONObject(s);
                    onResponse(ob);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //this function is made to fetch values from API using JSON
        public void onResponse(JSONObject response) throws JSONException {
            String title, releaseDate;
            String initialImageUrl = "http://image.tmdb.org/t/p/original";
            JSONArray jsonArray = response.getJSONArray("results");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject result = jsonArray.getJSONObject(i);
                String imageUrl = initialImageUrl.concat(result.getString("poster_path"));
                String imageUrl2 = initialImageUrl.concat(result.getString("backdrop_path"));
                    title = result.getString("title");
                    releaseDate = result.getString("release_date");
                int popularity = result.getInt("popularity");
                int id = result.getInt("id");
                int rating = result.getInt("vote_average");
                int voteCount = result.getInt("vote_count");
                String overview = result.getString("overview");
//                String type = result.getString("media_type");
                mMovieList.add(new MovieItem(imageUrl, title, popularity, id, rating, voteCount, overview, releaseDate,
                        imageUrl2));
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
        Uri builtUri = Uri.parse(API_URL_UPCOMING);
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
    public boolean isConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else
            return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Dismiss.");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        return builder;
    }


}