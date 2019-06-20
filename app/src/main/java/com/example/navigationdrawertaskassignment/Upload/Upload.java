package com.example.navigationdrawertaskassignment.Upload;

public class Upload {

    private String mName;
    private String mImageUrl;
    private String mDate;
    private String mPoints;
    private String mResponsible;

    public Upload() {

    }

    public Upload(String name, String imageUrl, String date, String points, String responsible) {
        if (name.trim().equals("")) {
            name = "No Name";
        }

        mName = name;
        mImageUrl = imageUrl;
        mDate = date;
        mPoints = points;
        mResponsible = responsible;
    }
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public void setmPoints(String mPoints) {
        this.mPoints = mPoints;
    }

    public void setmResponsible(String mResponsible) {
        this.mResponsible = mResponsible;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmPoints() {
        return mPoints;
    }

    public String getmResponsible() {
        return mResponsible;
    }
}
