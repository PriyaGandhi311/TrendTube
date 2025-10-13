package com.trendtube.analytics_service.entity;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class EngagementSnapshot {

    @Id @GeneratedValue
    private Long id;
    private String videoId;
    private LocalDate date;
    private long viewCount;
    private long likeCount;

    
    public EngagementSnapshot(String videoId, LocalDate date, long viewCount, long likeCount) {
        this.videoId = videoId;
        this.date = date;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getVideoId() {
        return videoId;
    }
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
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

}

