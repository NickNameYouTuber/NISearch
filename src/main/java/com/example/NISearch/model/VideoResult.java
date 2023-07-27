package com.example.NISearch.model;
import java.time.LocalDateTime;

public class VideoResult {
    private String title;
    private String videoUrl;
    private String description;
    private String channelTitle;
    private LocalDateTime publishedAt;
    private String thumbnailUrl;

    public VideoResult(String title, String videoUrl, String description, String channelTitle, LocalDateTime publishedAt) {
        this.title = title;
        this.videoUrl = videoUrl;
        this.description = description;
        this.channelTitle = channelTitle;
        this.publishedAt = publishedAt;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }
}
