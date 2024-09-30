package com.trenton.photogallery.feature.photosearch.data.model;

import java.util.List;

public class PhotoSearchList {
    private boolean hasMoreResults;

    private List<PhotoItem> photoItemList;

    // Constructor
    public PhotoSearchList(boolean hasMoreResults, List<PhotoItem> photoItemList) {
        this.hasMoreResults = hasMoreResults;
        this.photoItemList = photoItemList;
    }

    // Getters and Setters
    public boolean isHasMoreResults() {
        return hasMoreResults;
    }

    public void setHasMoreResults(boolean hasMoreResults) {
        this.hasMoreResults = hasMoreResults;
    }

    public List<PhotoItem> getPhotoItemList() {
        return photoItemList;
    }

    public void setPhotoItemList(List<PhotoItem> photoItemList) {
        this.photoItemList = photoItemList;
    }
}
