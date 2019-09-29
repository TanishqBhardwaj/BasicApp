package com.example.moviemate.favourite.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviemate.R;
import com.example.moviemate.favourite.Adapter.FavouriteAdapter;
import com.example.moviemate.favourite.Model.FavouriteItemTv;
import com.example.moviemate.main.UI.DetailActivity;
import com.example.moviemate.tv.UI.TvDetailActivity;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;

public class FavouriteFragment extends Fragment implements FavouriteAdapter.OnItemClickListener{

    public static ArrayList<FavouriteItemTv> fav_collection;
    private FavouriteAdapter favAdapter;
    private RecyclerView fav_list;
    public static ArrayList<FavouriteItemTv> favCollectionFull;

    public final static String EXTRA_IMAGE = "imageUrl";
    public final static String EXTRA_TITLE = "title";
    public final static String EXTRA_POPULARITY = "popularity";
    public final static String EXTRA_ID = "id";
    public final static String EXTRA_RATING = "vote_average";
    public final static String EXTRA_VOTE_COUNT = "vote_count";
    public final static String EXTRA_OVERVIEW = "overview";
    public final static String EXTRA_RELEASE_DATE = "release_date";
    public final static String EXTRA_IMAGE2 = "imageUrl2";
    public final static String EXTRA_TYPE = "media_type";

    public FavouriteFragment() {
    }

    public FavouriteFragment(ArrayList<FavouriteItemTv> favItems) {
        fav_collection = favItems;
//        favCollectionFull = new ArrayList<>(favItems);
//        favCollectionFull.addAll(favItems);
    }

    @Override
    public void onItemClick ( int position, ArrayList<FavouriteItemTv> arrayList){
        Intent detailIntent;
        FavouriteItemTv clickedItem = arrayList.get(position);
        if(clickedItem.getmType().equals("movie")) {
            detailIntent = new Intent(getActivity(), DetailActivity.class); //what does this mean
        }
        else {
            detailIntent = new Intent(getActivity(), TvDetailActivity.class);
        }

        detailIntent.putExtra(EXTRA_IMAGE, clickedItem.getmImageUrl());
        detailIntent.putExtra(EXTRA_TITLE, clickedItem.getmName());
        detailIntent.putExtra(EXTRA_POPULARITY, clickedItem.getmPopularity());
        detailIntent.putExtra(EXTRA_ID, clickedItem.getmId());
        detailIntent.putExtra(EXTRA_RATING, clickedItem.getmVoteAverage());
        detailIntent.putExtra(EXTRA_VOTE_COUNT, clickedItem.getmVoteCount());
        detailIntent.putExtra(EXTRA_OVERVIEW, clickedItem.getmOverview());
        detailIntent.putExtra(EXTRA_RELEASE_DATE, clickedItem.getmDate());
        detailIntent.putExtra(EXTRA_IMAGE2, clickedItem.getmImageUrl2());
        detailIntent.putExtra(EXTRA_TYPE, clickedItem.getmType());

        startActivity(detailIntent);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Fresco.initialize(getContext());
        View v =  inflater.inflate(R.layout.favourite, container, false);
        return v;

    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        setFavouriteAdapter();
    }

    private void setFavouriteAdapter(){
        fav_list = getView().findViewById(R.id.recycler_view_fav);
        fav_list.setLayoutManager(new LinearLayoutManager(getActivity()));



        favAdapter = new FavouriteAdapter(getActivity(),fav_collection);
        fav_list.setAdapter(favAdapter);
        favAdapter.setOnItemClickListener(FavouriteFragment.this);
    }

}
