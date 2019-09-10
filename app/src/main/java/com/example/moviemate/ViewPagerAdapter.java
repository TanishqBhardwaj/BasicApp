package com.example.moviemate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
//        CategoriesFragment categoriesFragment = new CategoriesFragment();
//        position += 1;
//        Bundle bundle = new Bundle();
//        bundle.putString("message", "Fragment : " + position);
//        categoriesFragment.setArguments(bundle);
//        return categoriesFragment;
        return null;
    }

    @Override
    public int getCount() {
//        return 3;
        return 0;
    }

}
