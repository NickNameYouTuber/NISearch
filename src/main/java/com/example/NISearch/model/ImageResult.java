package com.example.NISearch.model;

public class ImageResult {
    private String imageUrl;
    private String imageAltText;

    public ImageResult(String imageUrl, String imageAltText) {
        this.imageUrl = imageUrl;
        this.imageAltText = imageAltText;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageAltText() {
        return imageAltText;
    }

    public void setImageAltText(String imageAltText) {
        this.imageAltText = imageAltText;
    }
}
