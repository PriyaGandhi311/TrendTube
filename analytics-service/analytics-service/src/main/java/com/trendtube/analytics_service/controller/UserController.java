package com.trendtube.analytics_service.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trendtube.analytics_service.entity.UserFavorite;
import com.trendtube.analytics_service.entity.Video;
import com.trendtube.analytics_service.repository.UserFavoriteRepository;
import com.trendtube.analytics_service.repository.VideoRepository;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;

@CrossOrigin(origins = "http://localhost:3000") //Connect to frontend
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserFavoriteRepository favoriteRepo;
    private final VideoRepository videoRepo;

    @Autowired
    public UserController(UserFavoriteRepository favoriteRepo, VideoRepository videoRepo) {
        this.favoriteRepo = favoriteRepo;
        this.videoRepo = videoRepo;
    }

    @Transactional
    @Operation(summary = "Remove a video from user's favorites")
    @DeleteMapping("/{userId}/favorites/{videoId}")
    public ResponseEntity<String> removeFavorite(@PathVariable String userId, @PathVariable String videoId) {
        if (!favoriteRepo.existsByUserIdAndVideoId(userId, videoId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Favorite not found");
        }
        favoriteRepo.deleteByUserIdAndVideoId(userId, videoId);
        return ResponseEntity.ok("Removed");
    }

    @Operation(summary = "Check if a video is in user's favorites")
    @GetMapping("/{userId}/favorites/{videoId}")
    public ResponseEntity<Boolean> isFavorite(@PathVariable String userId, @PathVariable String videoId) {
        boolean exists = favoriteRepo.existsByUserIdAndVideoId(userId, videoId);
        return ResponseEntity.ok(exists);
    }

    private String fetchTitleFromVideoRepository(String videoId) {
        Optional<Video> videoOpt = videoRepo.findById(videoId);
        if (videoOpt.isPresent()) {
            return videoOpt.get().getTitle();
        } else {
            logger.warn("Video not found for videoId: {}", videoId);
            return "Untitled Video";
        }
    }

    @Operation(summary = "Add a video to user's favorites")
    @PostMapping("/{userId}/favorites")
    public ResponseEntity<String> addFavorite(@PathVariable String userId, @RequestBody Map<String, String> payload) {
        String videoId = payload.get("videoId");
        String title = fetchTitleFromVideoRepository(videoId); // uses video repository directly
        if (favoriteRepo.existsByUserIdAndVideoId(userId, videoId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Already favorited");
        }
        favoriteRepo.save(new UserFavorite(userId, videoId, title));
        return ResponseEntity.ok("Saved");
    }

    @Operation(summary = "Get user's favorite videos")
    @GetMapping("/{userId}/favorites")
    public ResponseEntity<List<Map<String, String>>> getFavorites(@PathVariable String userId) {
        List<UserFavorite> favorites = favoriteRepo.findByUserId(userId);

        List<Map<String, String>> response = favorites.stream().map(fav -> {
            Map<String, String> entry = new HashMap<>();
            entry.put("videoId", fav.getVideoId());
            entry.put("title", fav.getTitle()); // Make sure title is stored
            return entry;
        }).toList();

        return ResponseEntity.ok(response);
    }

}

