package com.trendtube.analytics_service.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trendtube.analytics_service.entity.EngagementSnapshot;
import com.trendtube.analytics_service.entity.Video;
import com.trendtube.analytics_service.repository.EngagementRepository;
import com.trendtube.analytics_service.repository.VideoRepository;
import com.trendtube.analytics_service.entity.UserFavorite;
import com.trendtube.analytics_service.repository.UserFavoriteRepository;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/analytics")
@CrossOrigin(origins = "http://localhost:3000") // allow frontend access
public class AnalyticsController {
    public record VideoComparison(String title, Long viewCount){}

    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private UserFavoriteRepository userFavoriteRepository;
    
    @Autowired
    private EngagementRepository engagementRepository;

    @Operation(summary = "Get top 10 trending videos by view count")
    @GetMapping("/trending")
    public ResponseEntity<List<Video>> getTrendingVideos() {
        return ResponseEntity.ok(videoRepository.findTop10ByOrderByViewCountDesc());
    }

    @Operation(summary = "Search videos by title or tags")
    @GetMapping("/search")
    public ResponseEntity<List<Video>> searchVideos(@RequestParam String query) {
        return ResponseEntity.ok(videoRepository.findByTitleContainingIgnoreCaseOrTagsContaining(query, query));
    }

    @Operation(summary = "Get analytics for a specific video by ID")
    @GetMapping("/{videoId}")
    public ResponseEntity<Map<String, Object>> getAnalytics(@PathVariable String videoId) {
        Optional<Video> videoOpt = videoRepository.findById(videoId);

        if (videoOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Video not found"));
        }

        Video video = videoOpt.get();
        Map<String, Object> analytics = new LinkedHashMap<>();
        analytics.put("videoId", video.getVideoId());
        analytics.put("title", video.getTitle());
        analytics.put("description", video.getDescription());
        analytics.put("channelTitle", video.getChannelTitle());
        analytics.put("viewCount", video.getViewCount());
        analytics.put("likeCount", video.getLikeCount());
        analytics.put("tags", video.getTags());

        return ResponseEntity.ok(analytics);
    }

    @Operation(summary = "Get engagement snapshots for a specific video by ID")
    @GetMapping("/{videoId}/engagement")
    public ResponseEntity<List<EngagementSnapshot>> getEngagement(@PathVariable String videoId) {
        return ResponseEntity.ok(engagementRepository.findByVideoIdOrderByDateAsc(videoId));
    }

    @Operation(summary = "Compare all user's favorite video's view counts")
    @GetMapping("/compareAll/{userId}")
    public ResponseEntity<List<VideoComparison>> compareViewCounts(@PathVariable String userId) {
        
        List<String> videoIds = userFavoriteRepository.findByUserId(userId).stream().map(UserFavorite::getVideoId).toList();
        List<Video> videos = videoRepository.findAllById(videoIds);
        List<VideoComparison> results = videos.stream()
            .map(v -> new VideoComparison(v.getTitle(), v.getViewCount()))
            .toList();

        return ResponseEntity.ok(results);
    }
}