package com.example.moviemate;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

public class MoviesFragment extends Fragment {

    View v;
    private RecyclerView mRecyclerView;
    private ArrayList<MovieItem> mMovieList;
    public MovieAdapter mMovieAdapter;

    final static String API_URL = "https://api.themoviedb.org/3/movie/popular?api_key=b8f745c2d43033fd65ce3af63180c3c3";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_movies, container, false);
        mRecyclerView = v.findViewById(R.id.recycler_view_movie);
        mRecyclerView.setHasFixedSize(true); // it fixes the size of recycler view, which is responsible for better performance
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // it sets the layout of recycler view as linear
        mMovieList = new ArrayList<>();
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        URL searchURL = buildUrl();
        new MoviesFragment.queryTask().execute(searchURL);
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
            mMovieAdapter = new MovieAdapter(getContext(), mMovieList);
            mRecyclerView.setAdapter(mMovieAdapter);
        }
    }

    //this function converts the API url into formatted url
    public static URL buildUrl() {
        Uri builtUri = Uri.parse(API_URL);
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
