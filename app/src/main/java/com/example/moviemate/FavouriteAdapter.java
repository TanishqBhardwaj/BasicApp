package com.example.moviemate;

import android.content.Context;
import android.net.Uri;
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
        View v = LayoutInflater.from(mContext).inflate(R.layout.my_favourite, parent, false);

        return new FavouriteViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        FavouriteItem favouriteItem = mFavouriteList.get(position);

try {
    String imageUrl = favouriteItem.getFImageUrl();
    String name = favouriteItem.getFName();
    String date = favouriteItem.getFDate();
    String overview = favouriteItem.getFOverview();

    holder.mImageView.setImageURI(Uri.parse(imageUrl)); //property of Fresco used
    holder.mTextViewName.setText(name);
    holder.mTextViewDate.setText(date);
holder.mTextViewOverview.setText(overview);

}catch (NullPointerException e){
    e.printStackTrace();
}

    }

    @Override
    public int getItemCount()
    {
        try{
        if(mFavouriteList.isEmpty()) {
        return 0;
    }
    else {

        return mFavouriteList.size();
    }}
       catch(NullPointerException e){
          e.printStackTrace();
    }
        return 0;
    }

    //this class holds the Views of XML file
    public class FavouriteViewHolder extends RecyclerView.ViewHolder {

        //public SimpleDraweeView mImageView;
        public TextView mTextViewName;
        public TextView mTextViewDate;
        public TextView mTextViewOverview;
        public SimpleDraweeView mImageView;
        //  public TextView mTextViewPopularity;


        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
           mImageView = itemView.findViewById(R.id.fav_image);
            mTextViewDate = itemView.findViewById(R.id.fav_date);
            mTextViewName = itemView.findViewById(R.id.fav_name);
            mTextViewOverview=itemView.findViewById(R.id.fav_overview);
            // mTextViewPopularity = itemView.findViewById(R.id.text_view_popularity_tv);


        //    mTextViewVoteAverage = itemView.findViewById(R.id.text_view_vote_average_tv);


        }



}
}