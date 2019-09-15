//This class is made for making Getters for values of API

package com.example.moviemate.main;

public class MovieItem {
    private String mImageUrl;
    private String mTitle;
    private int mPopularity;

    public MovieItem(String imageUrl, String title, int popularity) {
        mImageUrl = imageUrl;
        mTitle = title;
        mPopularity = popularity;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getPopularity() {
        return mPopularity;
    }
}