package com.trendtube.analytics_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trendtube.analytics_service.entity.EngagementSnapshot;

public interface EngagementRepository extends JpaRepository<EngagementSnapshot, Long> {
    List<EngagementSnapshot> findByVideoIdOrderByDateAsc(String videoId);
}
