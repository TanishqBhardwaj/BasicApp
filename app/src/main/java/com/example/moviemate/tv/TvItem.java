//This class is made for making Getters for values of API

package com.example.moviemate.tv;

public class TvItem {
    private String mImageUrl;
    private String mName;
    private int mPopularity;
    private int mId;

    public TvItem(String imageUrl, String name, int popularity, int id) {
        mImageUrl = imageUrl;
        mName = name;
        mPopularity = popularity;
        mId = id;
    }

    public String getImageUrl() { return mImageUrl; }

    public String getName() {
        return mName;
    }

    public int getPopularity() {
        return mPopularity;
    }

    public int getId() { return mId; }
}
