package com.example.moviemate.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviemate.FavouriteFragment;
import com.example.moviemate.FavouriteItem;
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
    static ArrayList<FavouriteItem> myfav = new ArrayList<>();

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
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (MovieItem movieItem : mMovieListFull) {
                    if (movieItem.getTitle().toLowerCase().startsWith(filterPattern)) {
                        filteredList.add(movieItem);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

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
    public void onBindViewHolder(@NonNull MovieViewHolderMain holder, final int position) {
        try {
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
            holder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            holder.plus3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDialog(position);
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();

        }
    }

    public void openDialog(final int position) {

                try {
                    FavouriteItem value = new FavouriteItem();
                    value.setFImageUrl(mMovieList.get(position).getImageUrl());
                    value.setFName(mMovieList.get(position).getTitle());
                    value.setFDate(mMovieList.get(position).getReleaseDate());
                    value.setFOverview(mMovieList.get(position).getOverview());
                    // Log.d(value.getFImageUrl()), "onClick: ");
                    Log.d(value.getFName(), "onClick: ");
                    Log.d(value.getFDate(), "onClick: ");
                    Log.d(value.getFOverview(), "onClick: ");
                    try{ int l=myfav.size();
                        int flag=0;
                        for(int i=0;i<l;i++)
                        {
                            if(myfav.get(i).getFName()==mMovieList.get(position).getTitle()) { flag=1;
                            }
                        }if(flag!=1){
                            myfav.add(value);
                            new FavouriteFragment(myfav);

                        }}catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(mContext, "Added To Favourites", Toast.LENGTH_SHORT).show();
                }
                catch(NullPointerException e){
                    e.printStackTrace();

                }
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
        ImageView plus3;

        public MovieViewHolderMain(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mTextViewTitle = itemView.findViewById(R.id.text_view_title);
            mTextViewRating = itemView.findViewById(R.id.text_view_rating);
            mTextViewPopularity = itemView.findViewById(R.id.text_view_popularity);
            mTextViewReleaseDate = itemView.findViewById(R.id.text_view_release_date);
            plus3 = itemView.findViewById(R.id.plus3);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}