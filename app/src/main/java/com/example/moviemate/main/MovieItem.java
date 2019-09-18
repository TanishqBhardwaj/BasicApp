//This class is made for making Getters for values of API

package com.example.moviemate.main;

public class MovieItem {
    private String mImageUrl;
    private String mTitle;
    private String mOverView;
    private String mReleaseDate;
    private String mAdult;
    private int mPopularity;

    public MovieItem(String imageUrl, String title, int popularity,String overView,String adult,String releaseDate) {
        mImageUrl = imageUrl;
        mTitle = title;
        mPopularity = popularity;
        mOverView=overView;
        mAdult=adult;
        mReleaseDate=releaseDate;
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
    public String getOverView() {
        return mOverView;
    }
    public String getAdult() {
        return mAdult;
    }
    public String getmReleaseDate() {
        return mReleaseDate;
    }
}
