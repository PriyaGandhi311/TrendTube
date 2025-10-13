package com.trendtube.analytics_service.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trendtube.analytics_service.entity.Video;

public interface VideoRepository extends JpaRepository<Video, String> {
    List<Video> findTop10ByOrderByViewCountDesc();
    List<Video> findByTitleContainingIgnoreCaseOrTagsContaining(String title, String tag);
}