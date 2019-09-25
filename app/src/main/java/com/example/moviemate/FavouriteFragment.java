package com.example.moviemate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class FavouriteFragment extends Fragment {

    public static ArrayList<FavouriteItem> fav_collection;
    private FavouriteAdapter favAdapter;
    private RecyclerView fav_list;

    public FavouriteFragment() {

    }

    public FavouriteFragment(ArrayList<FavouriteItem> favItems) {
        fav_collection = favItems;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.favourite, container, false);

    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        setFavouriteAdapter();
    }

    private void setFavouriteAdapter(){
        fav_list = getView().findViewById(R.id.recycler_view_fav);
        fav_list.setLayoutManager(new LinearLayoutManager(getActivity()));

        favAdapter = new FavouriteAdapter(fav_collection);
        fav_list.setAdapter(favAdapter);
    }

}
