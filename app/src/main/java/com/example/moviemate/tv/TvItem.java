//This class is made for making Getters for values of API

package com.example.moviemate.tv;

public class TvItem {
    private String mImageUrl;
    private String mName;
    private String mOverview;
    private int mPopularity;
    private int mVoteAverage;
    private int mVoteCount;
    private String mDate;
    private int mId;

    public TvItem(String imageUrl, String name,String overview,String date, int popularity,int voteAverage,int voteCount,int id) {
        mImageUrl = imageUrl;
        mName = name;
        mOverview=overview;
        mDate=date;
        mPopularity = popularity;
        mVoteAverage=voteAverage;
        mVoteCount=voteCount;
        mId = id;

    }

    public String getImageUrl() { return mImageUrl; }

    public String getName() {
        return mName;
    }
    public String getOverview() {
        return mOverview;
    }

    public String getDate() {
        return mDate;
    }
    public int getPopularity() {
        return mPopularity;
    }
    public int getVoteAverage() {
        return mVoteAverage;
    }

    public int getVoteCount() {
        return mVoteCount;
    }

    public int getId() { return mId; }
}
