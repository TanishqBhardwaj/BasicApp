package com.example.moviemate.movie;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.moviemate.R;
import com.example.moviemate.main.MovieItem;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context mContext;
    private ArrayList<MovieItem> mMovieList;
//    private OnItemClickListener mListener;

//    public interface OnItemClickListener {
//        void onItemClick(int position);
//    }
//
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        mListener = listener;
//    }

    public MovieAdapter(Context context, ArrayList<MovieItem> movieList) {
        mContext = context;
        mMovieList = movieList;
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
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieItem movieItem = mMovieList.get(position);

        String imageUrl = movieItem.getImageUrl();
        String title = movieItem.getTitle();
        int popularity = movieItem.getPopularity();

        holder.mImageView.setImageURI(Uri.parse(imageUrl)); //property of Fresco used
        holder.mTextViewTitle.setText(title);
        holder.mTextViewPopularity.setText("Popularity: " + popularity);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    //this class holds the Views of XML file
    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView mImageView;
        public TextView mTextViewTitle;
        public TextView mTextViewPopularity;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view_movie);
            mTextViewTitle = itemView.findViewById(R.id.text_view_title_movie);
            mTextViewPopularity = itemView.findViewById(R.id.text_view_popularity_movie);

//            itemView.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    if(mListener != null) {
//                        int position = getAdapterPosition();
//                        if(position != RecyclerView.NO_POSITION) {
//                            mListener.onItemClick(position);
//                        }
//                    }
//                }
//            });
        }
    }
}