package com.trendtube.upload_video_service.controller;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/upload")
@Tag(name = "Upload Video API", description = "Extracts videoId from YouTube link and publishes to RabbitMQ")
public class UploadController {

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    private final RestTemplate restTemplate;

    public UploadController() {
        this.restTemplate = new RestTemplate();
    }

    @Operation(
        summary = "Submit YouTube link",
        description = "Extracts videoId from the link and publishes it to RabbitMQ for processing. If the video already exists, metadata will be refreshed."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "VideoId published successfully",
            content = @Content(mediaType = "application/json",
            schema = @Schema(example = "{\"status\": \"new\", \"message\": \"New video submitted successfully.\"}"))),
        @ApiResponse(responseCode = "400", description = "Invalid YouTube URL",
            content = @Content(mediaType = "application/json",
            schema = @Schema(example = "{\"status\": \"error\", \"message\": \"Invalid YouTube URL\"}")))
    })
    
    @PostMapping("/submit")
    public ResponseEntity<Map<String, String>> submit(@RequestBody Map<String, String> payload) {
        logger.info("Received URL: {}", payload.get("url"));
        try {
            String videoId = extractVideoId(payload.get("url"));

            // Check if video already exists
            String checkUrl = "http://localhost:8081/videos/" + videoId + "/exists";
            Boolean exists = restTemplate.getForObject(checkUrl, Boolean.class);

            // Publish to RabbitMQ regardless
            rabbitTemplate.convertAndSend("video.exchange", "video.id", videoId);

            if (exists != null && exists) {
                return ResponseEntity.ok(Map.of(
                    "status", "exists",
                    "message", "Video already exists. Metadata will be refreshed."
                ));
            } else {
                return ResponseEntity.ok(Map.of(
                    "status", "new",
                    "message", "New video submitted successfully."
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "status", "error",
                "message", "Invalid YouTube URL"
            ));
        }
    }

    private String extractVideoId(String url) {
        String pattern = "^(?:https?://)?(?:www\\.)?(?:youtube\\.com/(?:watch\\?v=|embed/|v/)|youtu\\.be/)([\\w-]{11})";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(url);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid YouTube URL");
        }
    }

}
