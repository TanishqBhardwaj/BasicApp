package com.example.moviemate.home;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.moviemate.R;
import com.example.moviemate.main.MovieItem;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MovieViewHolderMain> implements Filterable {

    private Context mContext;
    private ArrayList<MovieItem> mMovieList;
    private ArrayList<MovieItem> mMovieListFull;
    private ArrayList<MovieItem> mMovieListTemp = new ArrayList<>();
    private OnItemClickListener mListener;
    static String API_LINK;
    String API_KEY = "b8f745c2d43033fd65ce3af63180c3c3";
    HomeAdapter mHomeAdapter;
    RecyclerView mRecyclerView;

    @Override
    public Filter getFilter() {
        return movieFilter;
    }

    private Filter movieFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<MovieItem> filteredList = new ArrayList<>();
//
            if (charSequence == null || charSequence.length() == 0) {
//                mMovieList.clear();
                filteredList.addAll(mMovieListFull);
            }
            else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(MovieItem movieItem : mMovieListFull) {
                    if(movieItem.getTitle().toLowerCase().startsWith(filterPattern)) {
                        filteredList.add(movieItem);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

//            else {
//                String filterPattern = charSequence.toString().toLowerCase().trim();
//
//                Uri.Builder builder = new Uri.Builder();
//                builder.scheme("https")
//                        .authority("api.themoviedb.org")
//                        .appendPath("3")
//                        .appendPath("search")
//                        .appendPath("multi")
//                        .appendQueryParameter("api_key", API_KEY)
//                        .appendQueryParameter("query", filterPattern);
//
//                API_LINK = builder.build().toString();
//                URL searchURL = buildUrl();
//                new HomeAdapter.queryTask().execute(searchURL);
//                mMovieList.clear();
//                mMovieList.addAll(mMovieListTemp);
//            }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = mMovieList;
//                mMovieListTemp.clear();
                return filterResults;
            }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mMovieList.clear();
            mMovieList.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

//    public HomeAdapter(ArrayList<MovieItem> moveList) {
//        mMovieListTemp = moveList;
//    }

    public HomeAdapter(Context context, ArrayList<MovieItem> movieList) {
        mContext = context;
        mMovieList = movieList;
        mMovieListFull = new ArrayList<>(movieList);
    }

    @NonNull
    @Override
    public MovieViewHolderMain onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //this line links the card view XML file with the object of View for accessing properties of XML file
        View v = LayoutInflater.from(mContext).inflate(R.layout.card_view_main, parent, false);
        return new MovieViewHolderMain(v);
    }

    //this function actually sets values from API to XML file
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolderMain holder, int position) {
        MovieItem movieItem = mMovieList.get(position);

        String imageUrl = movieItem.getImageUrl();
        String title = movieItem.getTitle();
        int popularity = movieItem.getPopularity();
        int rating = movieItem.getRating();
        String releaseDate = movieItem.getReleaseDate();

        holder.mImageView.setImageURI(Uri.parse(imageUrl)); //property of Fresco used
        holder.mTextViewTitle.setText(title);
        holder.mTextViewRating.setText(rating + "/10");
        holder.mTextViewPopularity.setText(String.valueOf(popularity));
        holder.mTextViewReleaseDate.setText(releaseDate);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    //this class holds the Views of XML file
    public class MovieViewHolderMain extends RecyclerView.ViewHolder {

        public SimpleDraweeView mImageView;
        public TextView mTextViewTitle;
        public TextView mTextViewRating;
        public TextView mTextViewPopularity;
        public TextView mTextViewReleaseDate;

        public MovieViewHolderMain(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mTextViewTitle = itemView.findViewById(R.id.text_view_title);
            mTextViewRating = itemView.findViewById(R.id.text_view_rating);
            mTextViewPopularity = itemView.findViewById(R.id.text_view_popularity);
            mTextViewReleaseDate = itemView.findViewById(R.id.text_view_release_date);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(mListener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

//    public class queryTask extends AsyncTask<URL, Void, String> { //<params, progress, result>
//
//        //this function works in background thread
//        @Override
//        protected String doInBackground(URL... urls) {
//            URL searchURL = urls[0]; // what does this mean
//            String searchResults = null;
//            try {
//                searchResults = getResponseFromHttpUrl(searchURL);
//            } catch (IOException e) {
//                e.printStackTrace();//what does this mean
//            }
//            return searchResults;
//        }
//
//        //this function works in main thread
//        @Override
//        protected void onPostExecute(String s) {
//            if (s != null && !s.equals("")) {
//                try {
//                    JSONObject ob = new JSONObject(s);
//                    onResponse(ob);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        //this function is made to fetch values from API using JSON
//        public void onResponse(JSONObject response) throws JSONException {
//            String title;
//            String initialImageUrl = "http://image.tmdb.org/t/p/original";
//            JSONArray jsonArray = response.getJSONArray("results");//puts api result array in jason array named jsonArray
//
//            mMovieListTemp.clear();
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject result = jsonArray.getJSONObject(i);
//                String imageUrl = initialImageUrl.concat(result.getString("poster_path"));
//                if(result.getString("media_type").equals("movie")) {
//                    title = result.getString("title");
//                }
//                else if(result.getString("media_type").equals("tv")){
//                    title = result.getString("name");
//                }
//                else {
//                    continue;
//                }
//                int popularity = result.getInt("popularity");
//                int id = result.getInt("id");
//                mMovieListTemp.add(new MovieItem(imageUrl, title, popularity, id));
//            }
//            mHomeAdapter = new HomeAdapter(mMovieListTemp);
////            mHomeAdapter = new HomeAdapter(getContext(), mMovieList);
////            mRecyclerView.setAdapter(mHomeAdapter);
////            mHomeAdapter.setOnItemClickListener(HomeFragment.this);
//        }
//    }
//
//    //this function converts the API url into formatted url
//    public static URL buildUrl() {
//        URL url = null;
//        try {
//            url = new URL(API_LINK);
//        } catch (MalformedURLException e) {
//            e.printStackTrace(); // prints class name and error line of Throwable object
//        }
//        return url;
//    }
//
//
//
//    //this function is responsible for getting response from API
//    public static String getResponseFromHttpUrl(URL url) throws IOException {
//        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();//what does this mean
//        try {
//            InputStream in = urlConnection.getInputStream();
//            Scanner scanner = new Scanner(in);
//            scanner.useDelimiter("\\A");//what does this Delimiter mean
//            boolean hasInput = scanner.hasNext();
//            if (hasInput) {
//                return scanner.next();
//            } else {
//                return null;
//            }
//        } finally {
//            urlConnection.disconnect();
//        }
//    }
}