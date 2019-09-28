package com.example.moviemate.movie;

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

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
private ImageView plus2;
    private Context mContext;
    private ArrayList<MovieItem> mMovieList;
    private ArrayList<MovieItem> mMovieListFull;
    private OnItemClickListener mListener;
    static ArrayList<FavouriteItem> myfav = new ArrayList<>();

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
            }  Toast.makeText(mContext, "Added To Favourites", Toast.LENGTH_SHORT).show();
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