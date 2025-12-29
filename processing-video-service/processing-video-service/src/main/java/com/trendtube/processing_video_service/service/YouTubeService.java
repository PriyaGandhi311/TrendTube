package com.trendtube.processing_video_service.service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.trendtube.processing_video_service.entity.Video;
import com.trendtube.processing_video_service.repository.VideoRepository;

@Service
public class YouTubeService {

    private final VideoRepository videoRepository;
    private final RestTemplate restTemplate;

    @Value("${api.key}")
    private String apiKey;

    @Autowired
    public YouTubeService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
        this.restTemplate = new RestTemplate();
    }

    public void fetchAndSaveMetadata(String videoId) {
        String url = "https://www.googleapis.com/youtube/v3/videos?part=snippet,statistics&id=" + videoId + "&key=" + apiKey;
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    JSONObject json = new JSONObject(response.getBody());
    JSONObject item = json.getJSONArray("items").getJSONObject(0);
    JSONObject snippet = item.getJSONObject("snippet");
    JSONObject stats = item.getJSONObject("statistics");

    Video video = new Video();
    video.setVideoId(videoId);
    video.setTitle(snippet.getString("title"));
    video.setDescription(snippet.getString("description"));
    video.setChannelTitle(snippet.getString("channelTitle"));
    video.setViewCount(Long.parseLong(stats.getString("viewCount")));
    video.setLikeCount(Long.parseLong(stats.optString("likeCount", "0")));
   
    JSONArray tagArray = snippet.optJSONArray("tags");
    if (tagArray != null) {
        List<String> tags = new ArrayList<>();
        for (int i = 0; i < tagArray.length(); i++) {
            tags.add(tagArray.getString(i));
        }
        video.setTags(tags);
    } else {
        video.setTags(Collections.emptyList()); // fallback if no tags
    }

    videoRepository.save(video);
}

}