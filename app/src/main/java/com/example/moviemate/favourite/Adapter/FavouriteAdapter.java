package com.example.moviemate.favourite.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviemate.R;
import com.example.moviemate.favourite.Model.FavouriteItemTv;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {

    private Context mContext;
    private ArrayList<FavouriteItemTv> mFavouriteList;
    private ArrayList<FavouriteItemTv> mFavouriteListFull;
    private FavouriteAdapter.OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position, ArrayList<FavouriteItemTv> arrayList);
    }

    public void setOnItemClickListener(FavouriteAdapter.OnItemClickListener listener) {
        mListener = listener;
    }


    public FavouriteAdapter(Context context, ArrayList<FavouriteItemTv> FavouriteList) {
        mContext = context;
        mFavouriteList = FavouriteList;
//        mFavouriteListFull =

    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.my_favourite, parent, false);

        return new FavouriteViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        mFavouriteListFull = new ArrayList<>();
//        for(FavouriteItemTv element : mFavouriteList) {
//            if(!mFavouriteListFull.contains(element)) {
//                mFavouriteListFull.add(element);
//            }
//        }

//        mFavouriteList = mFavouriteListFull;
        FavouriteItemTv favouriteItemTv = mFavouriteList.get(position);

    try {
        String imageUrl = favouriteItemTv.getmImageUrl();
        String name = favouriteItemTv.getmName();
        String overview = favouriteItemTv.getmOverview();
        String date = favouriteItemTv.getmDate();
        holder.mImageView.setImageURI(Uri.parse(imageUrl)); //property of Fresco used
        holder.mTextViewName.setText(name);
        holder.mTextViewDate.setText(date);
        holder.mTextViewOverview.setText(overview);

    }
    catch (NullPointerException e){
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
            }
        }
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
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position, mFavouriteList);
                        }
                    }
                }
            });

        }



}
}