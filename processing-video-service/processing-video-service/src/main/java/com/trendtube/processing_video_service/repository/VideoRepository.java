package com.trendtube.processing_video_service.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.trendtube.processing_video_service.entity.Video;

public interface VideoRepository extends JpaRepository<Video, String> {}
