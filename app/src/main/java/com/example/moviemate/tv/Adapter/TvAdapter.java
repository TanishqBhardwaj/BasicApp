package com.example.moviemate.tv.Adapter;

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
import com.example.moviemate.tv.Model.TvItem;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvViewHolder> {
public   TextView fav_name;
public TextView fav_date;
    public TextView fav_overview;
    private Context mContext;
    private ArrayList<TvItem> mTvList;
    private OnItemClickListener mListener;
    static ArrayList<FavouriteItemTv> myfav = new ArrayList<>();
    public ArrayList<TvItem> data;

    public interface OnItemClickListener {
        void onItemClick(int position, ArrayList<TvItem> tvItemArrayList);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public TvAdapter(Context context, ArrayList<TvItem> tvList) {
        mContext = context;
        mTvList = tvList;
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.card_view_tv, parent, false);
        return new TvViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull TvViewHolder holder, final int position) {
        TvItem tvItem = mTvList.get(position);

        String imageUrl = tvItem.getImageUrl();
        String name = tvItem.getName();
        int voteAverage = tvItem.getVoteAverage();

       holder.mImageView.setImageURI(Uri.parse(imageUrl)); //property of Fresco used
        holder.mTextViewName.setText(name);

        holder.mTextViewVoteAverage.setText("" + voteAverage);
        //holder.mTextViewPopularity.setText("Popularity: " + popularity);
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(position);
            }
        });


    }
    public void openDialog(final int position) {

                try {
                    FavouriteItemTv value = new FavouriteItemTv();

                        value.setmImageUrl(mTvList.get(position).getImageUrl());
                        value.setmImageUrl2(mTvList.get(position).getImageUrl2());
                        value.setmName(mTvList.get(position).getName());
                        value.setmOverview(mTvList.get(position).getOverview());
                        value.setmPopularity(mTvList.get(position).getPopularity());
                        value.setmVoteAverage(mTvList.get(position).getVoteAverage());
                        value.setmVoteCount(mTvList.get(position).getVoteCount());
                        value.setmDate(mTvList.get(position).getDate());
                        value.setmId(mTvList.get(position).getId());
                        value.setmType("tv");
                        // Log.d(value.getFImageUrl()), "onClick: ");
//                        Log.d(value.getFName(), "onClick: ");
//                        Log.d(value.getFDate(), "onClick: ");
//                        Log.d(value.getFOverview(), "onClick: ");

//                        myfav.add(value);
//                        new FavouriteFragment(myfav);
//                        value.setFImageUrl(mTvList.get(position).getImageUrl());
//                        value.setFName(mTvList.get(position).getName());
//                        value.setFDate(mTvList.get(position).getDate());
//                        value.setFOverview(mTvList.get(position).getOverview());
//
//                        Log.d(value.getFName(), "onClick: ");
//                        Log.d(value.getFDate(), "onClick: ");
//                        Log.d(value.getFOverview(), "onClick: ");
try{ int l=myfav.size();
    int flag=0;
    for(int i=0;i<l;i++)
    {
    if(myfav.get(i).getmName()==mTvList.get(position).getName()) { flag=1;
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
        return mTvList.size();
    }

    //this class holds the Views of XML file
    public class TvViewHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView mImageView;
        public TextView mTextViewName;

        public TextView mTextViewVoteAverage;
        ImageView plus;
        //  public TextView mTextViewPopularity;


        public TvViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view_tv);
            mTextViewName = itemView.findViewById(R.id.text_view_name_tv);

           // mTextViewPopularity = itemView.findViewById(R.id.text_view_popularity_tv);
            plus = itemView.findViewById(R.id.plus);

            mTextViewVoteAverage = itemView.findViewById(R.id.text_view_vote_average_tv);



            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(mListener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position, mTvList);
                        }
                    }
                }
            });
        }

    }
}