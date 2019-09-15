//This class is made for making Getters for values of API

package com.example.moviemate;

public class TvItem {
    private String mImageUrlTv;
    private String mName;
    private int mPopularityTv;

    public TvItem(String imageUrlTv, String name, int popularitytv) {
        mImageUrlTv = imageUrlTv;
        mName = name;
        mPopularityTv = popularitytv;
    }

    public String getImageUrl() {
        return mImageUrlTv;
    }

    public String getName() {
        return mName;
    }

    public int getPopularity() {
        return mPopularityTv;
    }
}
