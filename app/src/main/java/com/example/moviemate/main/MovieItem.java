//This class is made for making Getters for values of API

package com.example.moviemate.main;

public class MovieItem {
    private String mImageUrl;
    private String mTitle;
    private int mPopularity;
    private int mId;

    public MovieItem(String imageUrl, String title, int popularity, int id) {
        mImageUrl = imageUrl;
        mTitle = title;
        mPopularity = popularity;
        mId = id;
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

    public int getId() { return mId; }
}
