package com.trendtube.analytics_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trendtube.analytics_service.entity.UserFavorite;

@Repository
public interface UserFavoriteRepository extends JpaRepository<UserFavorite, Long> {
    List<UserFavorite> findByUserId(String userId);
    boolean existsByUserIdAndVideoId(String userId, String videoId);
    void deleteByUserIdAndVideoId(String userId, String videoId);
}

