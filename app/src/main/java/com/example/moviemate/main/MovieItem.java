//This class is made for making Getters for values of API

package com.example.moviemate.main;

public class MovieItem {
    private String mImageUrl;
    private String mTitle;
    private int mPopularity;
    private int mId;
    private int mRating;
    private int mVoteCount;
    private String mOverview;
    private String mReleaseDate;
    private String mImageUrl2;
    private String mType;

    public MovieItem(String imageUrl, String title, int popularity, int id, int rating, int voteCount, String overview,
                     String releaseDate, String imageUrl2, String type) {
        mImageUrl = imageUrl;
        mTitle = title;
        mPopularity = popularity;
        mId = id;
        mRating = rating;
        mVoteCount = voteCount;
        mOverview = overview;
        mReleaseDate = releaseDate;
        mImageUrl2 = imageUrl2;
        mType = type;
    }

    public MovieItem(String imageUrl, String title, int popularity, int id ,int rating, int voteCount, String overview,
                     String releaseDate, String imageUrl2) {
        mImageUrl = imageUrl;
        mTitle = title;
        mPopularity = popularity;
        mId = id;
        mRating = rating;
        mVoteCount = voteCount;
        mOverview = overview;
        mReleaseDate = releaseDate;
        mImageUrl2 = imageUrl2;
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

    public int getRating() {return mRating; }

    public int getVoteCount() { return mVoteCount; }

    public String getOverview() { return mOverview; }

    public String getReleaseDate() { return mReleaseDate; }

    public String getImageUrl2() { return mImageUrl2; }

    public String getType() { return mType; }
}
