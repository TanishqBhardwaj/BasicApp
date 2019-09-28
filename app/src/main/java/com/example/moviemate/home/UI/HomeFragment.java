package com.example.moviemate.home.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.moviemate.R;
import com.example.moviemate.home.Adapter.HomeAdapter;
import com.example.moviemate.main.UI.DetailActivity;
import com.example.moviemate.main.Model.MovieItem;
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

public class HomeFragment extends Fragment implements HomeAdapter.OnItemClickListener {

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

    final static String API_URL_TRENDING = "https://api.themoviedb.org/3/trending/all/day?api_key=b8f745c2d43033fd65ce3af63180c3c3";
    private RecyclerView mRecyclerView;
    private ArrayList<MovieItem> mMovieList;
    public HomeAdapter mHomeAdapter;
    SearchView searchView;
    ProgressBar progressBar;
    View v;

//
//        @Override
//        public void onCreate (@Nullable Bundle savedInstanceState){
//            super.onCreate(savedInstanceState);
//
//        }

        @Nullable
        @Override
        public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup
        container, @Nullable Bundle savedInstanceState){
            v = inflater.inflate(R.layout.fragment_home, container, false);




            if(!isConnected(getActivity())){ buildDialog(getActivity()).show();}
            else {

                URL searchURL = buildUrl();
                new HomeFragment.queryTask().execute(searchURL);

                mRecyclerView = v.findViewById(R.id.recycler_view_home);
                mRecyclerView.setHasFixedSize(true); // it fixes the size of recycler view, which is responsible for better performance
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                // it sets the layout of recycler view as linear
                mMovieList = new ArrayList<>();
                setHasOptionsMenu(true);
            }



        return v;
    }

        @Override
        public void onCreateOptionsMenu (Menu menu, MenuInflater menuInflater){
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
                    mHomeAdapter.getFilter().filter(newText);
                    return false;
                }
            });
        }

        //Formation of intent on clicking images
        @Override
        public void onItemClick ( int position){
            Intent detailIntent = new Intent(getActivity(), DetailActivity.class); //what does this mean
            MovieItem clickedItem = mMovieList.get(position);

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

        public class queryTask extends AsyncTask<URL, Void, String> { //<params, progress, result>

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = v.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }

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
                    progressBar.setVisibility(View.GONE);
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
                JSONArray jsonArray = response.getJSONArray("results");//puts api result array in jason array named jsonArray

                mMovieList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject result = jsonArray.getJSONObject(i);
                    String imageUrl = initialImageUrl.concat(result.getString("poster_path"));
                    String imageUrl2 = initialImageUrl.concat(result.getString("backdrop_path"));
                    if (result.getString("media_type").equals("movie")) {
                        title = result.getString("title");
                        releaseDate = result.getString("release_date");
                    } else if (result.getString("media_type").equals("tv")) {
                        title = result.getString("name");
                        releaseDate = result.getString("first_air_date");
                    } else {
                        continue;
                    }
                    int popularity = result.getInt("popularity");
                    int id = result.getInt("id");
                    int rating = result.getInt("vote_average");
                    int voteCount = result.getInt("vote_count");
                    String overview = result.getString("overview");
                    String type = result.getString("media_type");
                    mMovieList.add(new MovieItem(imageUrl, title, popularity, id, rating, voteCount, overview, releaseDate,
                            imageUrl2, type));
                }
                mHomeAdapter = new HomeAdapter(getContext(), mMovieList);
                mRecyclerView.setAdapter(mHomeAdapter);
                mHomeAdapter.setOnItemClickListener(HomeFragment.this);
            }
        }

        //this function converts the API url into formatted url
        public static URL buildUrl () {
//        Uri builtUri = Uri.parse(API_URL_POPULAR); //why do we convert string to URI
            URL url = null;
            try {
//            url = new URL(builtUri.toString());
                url = new URL(API_URL_TRENDING);
            } catch (MalformedURLException e) {
                e.printStackTrace(); // prints class name and error line of Throwable object
            }
            return url;
        }


        //this function is responsible for getting response from API
        public static String getResponseFromHttpUrl (URL url) throws IOException {
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
