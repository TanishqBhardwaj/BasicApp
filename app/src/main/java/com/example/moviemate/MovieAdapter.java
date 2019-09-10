package com.example.moviemate;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context mContext;
    private ArrayList<MovieItem> mMovieList;

    public MovieAdapter(Context context, ArrayList<MovieItem> movieList) {
        mContext = context;
        mMovieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // what does this line mean
        View v = LayoutInflater.from(mContext).inflate(R.layout.card_view, parent, false);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieItem movieItem = mMovieList.get(position);

        String imageUrl = movieItem.getImageUrl();
        String title = movieItem.getTitle();
        int popularity = movieItem.getPopularity();
//
//        PointF focusPoint = new PointF(0.5f, 0.5f);
//        holder.mImageView
//                .getHierarchy()
//                .setActualImageFocusPoint(focusPoint);
        holder.mImageView.setImageURI(Uri.parse(imageUrl));
        Log.d("Image url",imageUrl);
        holder.mTextViewTitle.setText(title);
        holder.mTextViewPopularity.setText("Popularity: " + popularity);
//        Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView mImageView;
        public TextView mTextViewTitle;
        public TextView mTextViewPopularity;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mTextViewTitle = itemView.findViewById(R.id.text_view_title);
            mTextViewPopularity = itemView.findViewById(R.id.text_view_popularity);
        }
    }
}