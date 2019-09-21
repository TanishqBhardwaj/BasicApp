package com.example.moviemate.tv;

import android.content.Intent;
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

public class TvFragment extends Fragment implements TvAdapter.OnItemClickListener {

    public final static String EXTRA_IMAGE = "imageUrl";
    public final static String EXTRA_NAME = "name";
    public final static String EXTRA_POPULARITY = "popularity";
    public final static String EXTRA_ID = "id";

    private RecyclerView mRecyclerViewPopular;
    private RecyclerView mRecyclerViewTopRated;
    private RecyclerView mRecyclerViewAiringToday;
    private RecyclerView mRecyclerViewLatest;
    private TvAdapter mTvAdapter;

    View v;

    final static String API_URL_POPULAR = "https://api.themoviedb.org/3/tv/popular?api_key=b8f745c2d43033fd65ce3af63180c3c3";
    final static String API_URL_TOP_RATED = "https://api.themoviedb.org/3/tv/top_rated?api_key=b8f745c2d43033fd65ce3af63180c3c3";
    final static String API_URL_AIRING_TODAY = "https://api.themoviedb.org/3/tv/airing_today?api_key=b8f745c2d43033fd65ce3af63180c3c3";
    final static String API_URL_LATEST = "https://api.themoviedb.org/3/tv/on_the_air?api_key=b8f745c2d43033fd65ce3af63180c3c3";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_tv, container, false);
        mRecyclerViewPopular = v.findViewById(R.id.recycler_view_popular_tv);
        // it fixes the size of recycler view, which is responsible for better performance
        mRecyclerViewPopular.setHasFixedSize(true);
        mRecyclerViewPopular.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));// it sets the layout of recycler view as linear

        mRecyclerViewTopRated = v.findViewById(R.id.recycler_view_top_rated_tv);
        // it fixes the size of recycler view, which is responsible for better performance
        mRecyclerViewTopRated.setHasFixedSize(true);
        mRecyclerViewTopRated.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));// it sets the layout of recycler view as linear


        mRecyclerViewAiringToday = v.findViewById(R.id.recycler_view_airing_today_tv);
        // it fixes the size of recycler view, which is responsible for better performance
        mRecyclerViewAiringToday.setHasFixedSize(true);
        mRecyclerViewAiringToday.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));// it sets the layout of recycler view as linear


        mRecyclerViewLatest = v.findViewById(R.id.recycler_view_latest_tv);
        // it fixes the size of recycler view, which is responsible for better performance
        mRecyclerViewLatest.setHasFixedSize(true);
        mRecyclerViewLatest.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));


        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        URL searchURLPopular = buildUrlPopular();
        URL searchURLTopRated = buildUrlTopRated();
        URL searchURLLatest = buildUrlLatest();
        URL searchURLAiringToday = buildUrlAiringToday();


        new TvFragment.queryTaskPopular().execute(searchURLPopular);
        new TvFragment.queryTaskTopRated().execute(searchURLTopRated);
        new TvFragment.queryTaskAiringToday().execute(searchURLAiringToday);
        new TvFragment.queryTaskLatest().execute(searchURLLatest);
    }

    @Override
    public void onItemClick(int position, ArrayList<TvItem> tvItemArrayList) {
        Intent detailIntent = new Intent(getActivity().getApplicationContext(), TvDetailActivity.class); //what does this mean
        TvItem clickedItem = tvItemArrayList.get(position);

        detailIntent.putExtra(EXTRA_IMAGE, clickedItem.getImageUrl());
        detailIntent.putExtra(EXTRA_NAME, clickedItem.getName());
        detailIntent.putExtra(EXTRA_POPULARITY, clickedItem.getPopularity());
        detailIntent.putExtra(EXTRA_ID, clickedItem.getId());

        startActivity(detailIntent);
    }

    public class queryTaskPopular extends AsyncTask<URL, Void, String> {

        private ArrayList<TvItem> mTvList = new ArrayList<>();

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
                String name = result.getString("name");
                int popularity = result.getInt("popularity");
                int id = result.getInt("id");
                mTvList.add(new TvItem(imageUrl, name, popularity, id));
            }
            mTvAdapter = new TvAdapter(getContext(), mTvList);
            mRecyclerViewPopular.setAdapter(mTvAdapter);
            mTvAdapter.setOnItemClickListener(TvFragment.this);
        }
    }

    public class queryTaskTopRated extends AsyncTask<URL, Void, String> {
        private ArrayList<TvItem> mTvList = new ArrayList<>();

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
                String name = result.getString("name");
                int popularity = result.getInt("popularity");
                int id = result.getInt("id");
                mTvList.add(new TvItem(imageUrl, name, popularity, id));
            }
            mTvAdapter = new TvAdapter(getContext(), mTvList);
            mRecyclerViewTopRated.setAdapter(mTvAdapter);
            mTvAdapter.setOnItemClickListener(TvFragment.this);
        }
    }


    public class queryTaskAiringToday extends AsyncTask<URL, Void, String> {

        private ArrayList<TvItem> mTvList = new ArrayList<>();

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
                String name = result.getString("name");
                int popularity = result.getInt("popularity");
                int id = result.getInt("id");
                mTvList.add(new TvItem(imageUrl, name, popularity, id));
            }
            mTvAdapter = new TvAdapter(getContext(), mTvList);
            mRecyclerViewAiringToday.setAdapter(mTvAdapter);
            mTvAdapter.setOnItemClickListener(TvFragment.this);
        }
    }














    public class queryTaskLatest extends AsyncTask<URL, Void, String> {

        private ArrayList<TvItem> mTvList = new ArrayList<>();

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
                String name = result.getString("name");
                int popularity = result.getInt("popularity");
                int id = result.getInt("id");
                mTvList.add(new TvItem(imageUrl, name, popularity, id));
            }
            mTvAdapter = new TvAdapter(getContext(), mTvList);
            mRecyclerViewLatest.setAdapter(mTvAdapter);
            mTvAdapter.setOnItemClickListener(TvFragment.this);
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
    public static URL buildUrlAiringToday() {
        Uri builtUri = Uri.parse(API_URL_AIRING_TODAY);
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
    public static URL buildUrlLatest() {
        Uri builtUri = Uri.parse(API_URL_LATEST);
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