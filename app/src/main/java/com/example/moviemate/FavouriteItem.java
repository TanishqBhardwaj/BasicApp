//This class is made for making Getters for values of API

package com.example.moviemate;

public class FavouriteItem {
    private String mName;
    private String mImageUrl;
    private String mDate;

    public String getFImageUrl() { return mImageUrl; }

    public void setFImageUrl(String mImageUrl){
        this.mImageUrl= mImageUrl;
    }

    public String getFName() { return mName; }


        public void setFName(String mName){
            this.mName = mName;
        }


    public String getFDate() {
        return mDate;
    }
    public void setFDate(String mDate){
        this.mDate = mDate;
    }


}
