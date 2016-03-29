package com.mascarade.model.adapter;

/**
 * Created by melanie on 3/28/16.
 */
public class ListItem {

    private int imageId;
    private String nameImage;

    public ListItem(int imageId, String nameImage) {
        this.nameImage = nameImage;
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {

        this.imageId = imageId;
    }

    public String getNameImage() {
        return nameImage;
    }

    public void setNameImage(String nameImage) {
        this.nameImage = nameImage;
    }
}
