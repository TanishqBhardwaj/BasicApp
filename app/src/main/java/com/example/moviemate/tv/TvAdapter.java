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
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.ArrayList;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvViewHolder> {

    private Context mContext;
    private ArrayList<TvItem> mTvList;
//    private OnItemClickListener mListener;

//    public interface OnItemClickListener {
//        void onItemClick(int position);
//    }
//
//    public void setOnItemClickListener(OnItemClickListener listener) {
//        mListener = listener;
//    }

    public TvAdapter(Context context, ArrayList<TvItem> tvList) {
        mContext = context;
        mTvList = tvList;
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //this line links the card view XML file with the object of View for accessing properties of XML file
        View v = LayoutInflater.from(mContext).inflate(R.layout.card_view_tv, parent, false);
        return new TvViewHolder(v);
    }

    //this function actually sets values from API to XML file
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
        public TextView mTextViewRecyclerName;

        public TvViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view_tv);
            mTextViewName = itemView.findViewById(R.id.text_view_name_tv);
            mTextViewPopularity = itemView.findViewById(R.id.text_view_popularity_tv);
            mTextViewRecyclerName = itemView.findViewById(R.id.recycler_view_name);

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