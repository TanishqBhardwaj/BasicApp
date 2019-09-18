package com.example.moviemate.tv;

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
import com.example.moviemate.movie.MovieAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.ArrayList;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvViewHolder> {

    private Context mContext;
    private ArrayList<TvItem> mTvList;
    private TvAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position, ArrayList<TvItem> tvItemArrayList);
    }

    public void setOnItemClickListener(TvAdapter.OnItemClickListener listener) {
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
        String name = tvItem.getName();
        int popularity = tvItem.getPopularity();

        holder.mImageView.setImageURI(Uri.parse(imageUrl)); //property of Fresco used
        holder.mTextViewName.setText(name);
        holder.mTextViewPopularity.setText("Popularity: " + popularity);
    }

    @Override
    public int getItemCount() {
        return mTvList.size();
    }

    //this class holds the Views of XML file
    public class TvViewHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView mImageView;
        public TextView mTextViewName;
        public TextView mTextViewPopularity;

        public TvViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view_tv);
            mTextViewName = itemView.findViewById(R.id.text_view_name_tv);
            mTextViewPopularity = itemView.findViewById(R.id.text_view_popularity_tv);

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