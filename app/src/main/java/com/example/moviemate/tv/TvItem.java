//This class is made for making Getters for values of API

package com.example.moviemate.tv;

public class TvItem {
    private String mImageUrl;
    private String mName;
    private int mPopularity;

    public TvItem(String imageUrl, String name, int popularity) {
        mImageUrl = imageUrl;
        mName = name;
        mPopularity = popularity;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getName() {
        return mName;
    }

    public int getPopularity() {
        return mPopularity;
    }
}
