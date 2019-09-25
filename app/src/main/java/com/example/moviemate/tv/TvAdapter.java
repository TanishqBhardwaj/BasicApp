package com.example.moviemate.tv;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviemate.FavouriteActivity;
import com.example.moviemate.FavouriteFragment;
import com.example.moviemate.FavouriteItem;
import com.example.moviemate.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvViewHolder> {

    private Context mContext;
    private ArrayList<TvItem> mTvList;
    private OnItemClickListener mListener;
    ArrayList<FavouriteItem> myfav = new ArrayList<>();
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
    public void onBindViewHolder(@NonNull TvViewHolder holder, int position) {
        TvItem tvItem = mTvList.get(position);

        String imageUrl = tvItem.getImageUrl();
        String overview = tvItem.getOverview();
        String name = tvItem.getName();
        int popularity = tvItem.getPopularity();
        int voteAverage = tvItem.getVoteAverage();

        holder.mImageView.setImageURI(Uri.parse(imageUrl)); //property of Fresco used
        holder.mTextViewName.setText(name);

        holder.mTextViewVoteAverage.setText("Vote Average: " + voteAverage);
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
    private void openDialog(final int position) {
        AlertDialog.Builder  builder = new AlertDialog.Builder(mContext);
        builder.setNegativeButton("ADD TO CART" , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FavouriteItem value = new FavouriteItem();

                value.setFName(data.get(position).getName());

                value.setFDate(data.get(position).getDate());
                Log.d(value.getFName(), "onClick: ");
                Log.d(value.getFDate(), "onClick: ");
                myfav.add(value);
                new FavouriteFragment(myfav);
              //  Toast.makeText(getContext() , "Item Added To Cart" , Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });



        builder.show();
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