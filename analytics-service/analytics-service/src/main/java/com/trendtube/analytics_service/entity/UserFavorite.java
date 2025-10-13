package com.trendtube.analytics_service.entity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Schema(description = "Represents a user's favorited video")
public class UserFavorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Auto-generated unique ID for the favorite entry", example = "1")
    private Long id;

    @Schema(description = "Google user ID of the person who favorited the video", example = "106121619038704088671")
    private String userId;

    @Schema(description = "YouTube video ID that was favorited", example = "RRubcjpTkks")
    private String videoId;

    @Schema(description = "Title of the favorited video", example = "Learn Java in 14 Minutes")
    private String title;

    public UserFavorite() {}

    public UserFavorite(String userId, String videoId, String title) {
        this.userId = userId;
        this.videoId = videoId;
        this.title = title;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

}

