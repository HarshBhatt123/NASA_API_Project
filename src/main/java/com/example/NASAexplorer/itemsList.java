package com.example.NASAexplorer;

public class itemsList {
    private String mImageUrl;
    private String mTitle;
    private String  mId;
    private String  mType;
    private String mDescription;

    public itemsList(String imageUrl,String title, String id,String description,String  mediaType) {
        mImageUrl=imageUrl;
        mTitle = title;
        mId = id;
        mType=mediaType;
        mDescription = description;
    }
    public String getImageUrl() {
        return mImageUrl;
    }


    public String getTitle() {
        return mTitle;
    }
    public String getType() {
        return mType;
    }
    public String getDescription() {
        return mDescription;
    }


    public String getId() {
        return mId;
    }

}
