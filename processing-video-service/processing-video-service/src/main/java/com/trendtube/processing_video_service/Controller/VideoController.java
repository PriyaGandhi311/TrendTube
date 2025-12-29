package com.trendtube.processing_video_service.Controller;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trendtube.processing_video_service.entity.Video;
import com.trendtube.processing_video_service.repository.VideoRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content; 
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Video Metadata API", description = "Endpoints for checking and retrieving video metadata")
@RestController
@RequestMapping("/videos")
public class VideoController {

    private final VideoRepository videoRepository;

    @Autowired
    public VideoController(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Operation(summary = "Check if a video exists in the database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Returns true if video exists, false otherwise",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)))
    })
    
    @GetMapping("/{videoId}/exists")
    public ResponseEntity<Boolean> checkVideoExists(@PathVariable String videoId) {
        boolean exists = videoRepository.existsById(videoId);
        return ResponseEntity.ok(exists);
    }

    @Operation(summary = "Get the title of a video by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Returns the video title",
            content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"title\": \"Learn Java in 14 Minutes\"}"))),
        @ApiResponse(responseCode = "404", description = "Video not found",
            content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"title\": \"Unknown Video\"}")))
    })
    @GetMapping("/api/upload/title/{videoId}")
    public ResponseEntity<Map<String, String>> getTitle(@PathVariable String videoId) {
        Optional<Video> videoOpt = videoRepository.findById(videoId);

        if (videoOpt.isPresent()) {
            Map<String, String> response = new HashMap<>();
            response.put("title", videoOpt.get().getTitle());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("title", "Unknown Video"));
        }
    }
}


