package com.trendtube.processing_video_service.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trendtube.processing_video_service.service.YouTubeService;

@Component
public class RabbitMQListener {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQListener.class);
    private final YouTubeService youTubeService;

    @Autowired
    public RabbitMQListener(YouTubeService youTubeService) {
        this.youTubeService = youTubeService;
    }

    @RabbitListener(queues = "video.id.queue")
    public void receive(String videoId) {
        logger.info("Received videoId: {}", videoId);
        youTubeService.fetchAndSaveMetadata(videoId);
        logger.info("Processed videoId: {}", videoId);
    }
}

