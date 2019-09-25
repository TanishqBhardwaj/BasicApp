package com.example.moviemate;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.moviemate.R;
import com.example.moviemate.tv.TvItem;
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

        String imageUrl = favouriteItem.getImageUrl();
        String overview = favouriteItem.getOverview();
        String name = favouriteItem.getName();
        int popularity = favouriteItem.getPopularity();
        int voteAverage = favouriteItem.getVoteAverage();

        holder.mImageView.setImageURI(Uri.parse(imageUrl)); //property of Fresco used
        holder.mTextViewName.setText(name);

        holder.mTextViewVoteAverage.setText("Vote Average: " + voteAverage);
        //holder.mTextViewPopularity.setText("Popularity: " + popularity);

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
        public TextView mTextViewVoteAverage;
        //  public TextView mTextViewPopularity;


        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view_tv);
            mTextViewName = itemView.findViewById(R.id.text_view_name_tv);
            // mTextViewPopularity = itemView.findViewById(R.id.text_view_popularity_tv);


            mTextViewVoteAverage = itemView.findViewById(R.id.text_view_vote_average_tv);


        }
    }
}