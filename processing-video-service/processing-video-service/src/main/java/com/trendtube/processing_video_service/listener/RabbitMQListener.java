package com.trendtube.processing_video_service.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trendtube.processing_video_service.service.YouTubeService;

@Component
public class RabbitMQListener {

    private final YouTubeService youTubeService;

    @Autowired
    public RabbitMQListener(YouTubeService youTubeService) {
        this.youTubeService = youTubeService;
    }

    @RabbitListener(queues = "video.id.queue")
    public void receive(String videoId) {
        System.out.println("Received videoId: " + videoId);
        youTubeService.fetchAndSaveMetadata(videoId);
        System.out.println("Processed videoId: " + videoId);
    }
}

