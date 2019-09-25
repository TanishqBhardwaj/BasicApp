package com.example.moviemate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {

    private Context mContext;
    private ArrayList<FavouriteItem> mFavouriteList;





    public FavouriteAdapter(Context context, ArrayList<FavouriteItem> FavouriteList) {
        mContext = context;
        mFavouriteList = FavouriteList;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.favourite, parent, false);
        return new FavouriteViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        FavouriteItem favouriteItem = mFavouriteList.get(position);



       // holder.setImageURI(Uri.parse(imageUrl)); //property of Fresco used
        holder.set_fav_item_name(favouriteItem.getFName());
        holder.set_fav_item_date(favouriteItem.getFDate());

    }

    @Override
    public int getItemCount()
    {if(mFavouriteList.isEmpty()) {
        return 0;
    }
    else {

        return mFavouriteList.size();
    }
    }

    //this class holds the Views of XML file
    public class FavouriteViewHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView mImageView;
        public TextView mTextViewName;
        public TextView mTextViewDate;
        //  public TextView mTextViewPopularity;


        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewDate = itemView.findViewById(R.id.fav_date);
            mTextViewName = itemView.findViewById(R.id.fav_name);
            // mTextViewPopularity = itemView.findViewById(R.id.text_view_popularity_tv);


        //    mTextViewVoteAverage = itemView.findViewById(R.id.text_view_vote_average_tv);


        }


    public void set_fav_item_name(String name) {
        mTextViewName.setText(name);
    }
    public void set_fav_item_date(String price) {
        mTextViewDate.setText(price);
    }
}
}