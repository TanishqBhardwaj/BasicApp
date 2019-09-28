package com.example.moviemate.movie.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviemate.favourite.UI.FavouriteFragment;
import com.example.moviemate.favourite.Model.FavouriteItemTv;
import com.example.moviemate.R;
import com.example.moviemate.main.Model.MovieItem;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
private ImageView plus2;
    private Context mContext;
    private ArrayList<MovieItem> mMovieList;
    private ArrayList<MovieItem> mMovieListFull;
    private OnItemClickListener mListener;
    static ArrayList<FavouriteItemTv> myfav = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(int position, ArrayList<MovieItem> movieItemArrayList);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public MovieAdapter(Context context, ArrayList<MovieItem> movieList) {
        mContext = context;
        mMovieList = movieList;
        mMovieListFull=new ArrayList<>(movieList);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //this line links the card view XML file with the object of View for accessing properties of XML file
        View v = LayoutInflater.from(mContext).inflate(R.layout.card_view_movie, parent, false);
        return new MovieViewHolder(v);
    }

    //this function actually sets values from API to XML file
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, final int position) {

            try {
                MovieItem movieItem = mMovieList.get(position);

                String imageUrl = movieItem.getImageUrl();
                String title = movieItem.getTitle();
                int rating = movieItem.getRating();

                holder.mImageView.setImageURI(Uri.parse(imageUrl)); //property of Fresco used
                holder.mTextViewTitle.setText(title);
                holder.mTextViewRating.setText("" + rating);

                holder.mImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                holder.plus2.setOnClickListener(new View.OnClickListener() {
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
            FavouriteItemTv value = new FavouriteItemTv();
            value.setmImageUrl(mMovieList.get(position).getImageUrl());
            value.setmImageUrl2(mMovieList.get(position).getImageUrl2());
            value.setmName(mMovieList.get(position).getTitle());
            value.setmOverview(mMovieList.get(position).getOverview());
            value.setmPopularity(mMovieList.get(position).getPopularity());
            value.setmVoteAverage(mMovieList.get(position).getRating());
            value.setmVoteCount(mMovieList.get(position).getVoteCount());
            value.setmDate(mMovieList.get(position).getReleaseDate());
            value.setmId(mMovieList.get(position).getId());
            value.setmType("movie");
            // Log.d(value.getFImageUrl()), "onClick: ");
//            Log.d(value.getFName(), "onClick: ");
//            Log.d(value.getFDate(), "onClick: ");
//            Log.d(value.getFOverview(), "onClick: ");
            myfav.add(value);
            new FavouriteFragment(myfav);


            Toast.makeText(mContext, "Added To Favourites", Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            e.printStackTrace();

        }
    }



    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    //this class holds the Views of XML file
    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView mImageView;
        public TextView mTextViewTitle;
        public TextView mTextViewRating;
        ImageView plus2;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view_movie);
            mTextViewTitle = itemView.findViewById(R.id.text_view_title_movie);
            mTextViewRating = itemView.findViewById(R.id.text_view_rating_movie);
            plus2 = itemView.findViewById(R.id.plus2);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(mListener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position, mMovieList);
                        }
                    }
                }
            });
        }
    }
}