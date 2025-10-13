package com.trendtube.analytics_service.entity;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "video")
@Schema(description = "Represents a YouTube video with metadata and tags")
public class Video {

    @Id
    @Schema(description = "Unique YouTube video ID", example = "RRubcjpTkks")
    private String videoId;

    @Schema(description = "Title of the video", example = "Learn Java in 14 Minutes")
    private String title;

    @Column(length = 5000)
    @Schema(description = "Full description of the video", example = "This video covers Java basics...")
    private String description;

    @Schema(description = "Name of the channel that uploaded the video", example = "Tech Simplified")
    private String channelTitle;

    @Schema(description = "Total number of views", example = "123456")
    private long viewCount;

    @Schema(description = "Total number of likes", example = "7890")
    private long likeCount;

    @ElementCollection
    @CollectionTable(name = "video_tags", joinColumns = @JoinColumn(name = "video_id"))
    @Column(name = "tag")
    @Schema(description = "List of tags associated with the video", example = "[\"java\", \"tutorial\", \"programming\"]")
    private List<String> tags;

    // Getters and Setters
    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}