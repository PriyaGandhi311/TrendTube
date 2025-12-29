package com.trendtube.processing_video_service.Controller;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.trendtube.processing_video_service.entity.Video;
import com.trendtube.processing_video_service.repository.VideoRepository;

/**
 * Test class for VideoController.getTitle() method
 * 
 * This class contains:
 * - 5 test cases using Equivalence Class Partitioning (Black Box Testing)
 * - 5 test cases using Control Flow criterion (White Box Testing)
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("VideoController.getTitle() Test Suite")
class VideoControllerTest {

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private VideoController videoController;

    private Video testVideo;
    private String validVideoId;
    private String validTitle;

    @BeforeEach
    void setUp() {
        validVideoId = "RRubcjpTkks";
        validTitle = "Learn Java in 14 Minutes";
        testVideo = new Video();
        testVideo.setVideoId(validVideoId);
        testVideo.setTitle(validTitle);
    }

    // ============================================
    // EQUIVALENCE CLASS PARTITIONING TEST CASES
    // (Black Box Testing - 5 test cases)
    // ============================================

    /**
     * ECP Test Case 1: Valid videoId that exists in database
     * Equivalence Class: Valid existing videoId
     * Expected: 200 OK with correct title
     */
    @Test
    @DisplayName("ECP-1: Valid existing videoId should return title with 200 OK")
    void testGetTitle_ValidExistingVideoId_ReturnsTitle() {
        // Arrange
        when(videoRepository.findById(validVideoId)).thenReturn(Optional.of(testVideo));

        // Act
        ResponseEntity<Map<String, String>> response = videoController.getTitle(validVideoId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(validTitle, response.getBody().get("title"));
        verify(videoRepository, times(1)).findById(validVideoId);
    }

    /**
     * ECP Test Case 2: Valid videoId that doesn't exist in database
     * Equivalence Class: Valid non-existing videoId
     * Expected: 404 NOT_FOUND with "Unknown Video"
     */
    @Test
    @DisplayName("ECP-2: Valid non-existing videoId should return 404 with Unknown Video")
    void testGetTitle_ValidNonExistingVideoId_ReturnsNotFound() {
        // Arrange
        String nonExistingVideoId = "NonExistent123";
        when(videoRepository.findById(nonExistingVideoId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Map<String, String>> response = videoController.getTitle(nonExistingVideoId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Unknown Video", response.getBody().get("title"));
        verify(videoRepository, times(1)).findById(nonExistingVideoId);
    }

    /**
     * ECP Test Case 3: Null videoId
     * Equivalence Class: Null input
     * Expected: 404 NOT_FOUND with "Unknown Video" (repository returns empty)
     */
    @Test
    @DisplayName("ECP-3: Null videoId should return 404 with Unknown Video")
    void testGetTitle_NullVideoId_ReturnsNotFound() {
        // Arrange
        when(videoRepository.findById(null)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Map<String, String>> response = videoController.getTitle(null);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Unknown Video", response.getBody().get("title"));
        verify(videoRepository, times(1)).findById(null);
    }

    /**
     * ECP Test Case 4: Empty string videoId
     * Equivalence Class: Empty string input
     * Expected: 404 NOT_FOUND with "Unknown Video"
     */
    @Test
    @DisplayName("ECP-4: Empty string videoId should return 404 with Unknown Video")
    void testGetTitle_EmptyStringVideoId_ReturnsNotFound() {
        // Arrange
        String emptyVideoId = "";
        when(videoRepository.findById(emptyVideoId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Map<String, String>> response = videoController.getTitle(emptyVideoId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Unknown Video", response.getBody().get("title"));
        verify(videoRepository, times(1)).findById(emptyVideoId);
    }

    /**
     * ECP Test Case 5: VideoId with special characters
     * Equivalence Class: Invalid format videoId (special characters)
     * Expected: 404 NOT_FOUND with "Unknown Video"
     */
    @Test
    @DisplayName("ECP-5: VideoId with special characters should return 404 with Unknown Video")
    void testGetTitle_SpecialCharactersVideoId_ReturnsNotFound() {
        // Arrange
        String specialCharVideoId = "abc@#$%^&*()";
        when(videoRepository.findById(specialCharVideoId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Map<String, String>> response = videoController.getTitle(specialCharVideoId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Unknown Video", response.getBody().get("title"));
        verify(videoRepository, times(1)).findById(specialCharVideoId);
    }

    // ============================================
    // CONTROL FLOW TEST CASES
    // (White Box Testing - 5 test cases)
    // ============================================

    /**
     * CF Test Case 1: Path where videoOpt.isPresent() == true
     * Control Flow: Branch 1 - Video found path
     * Expected: Execute if block, return 200 OK with title
     */
    @Test
    @DisplayName("CF-1: Control Flow - Video found path (isPresent == true)")
    void testGetTitle_ControlFlow_VideoFoundPath() {
        // Arrange
        Video video = new Video();
        video.setVideoId("test123");
        video.setTitle("Test Video Title");
        when(videoRepository.findById("test123")).thenReturn(Optional.of(video));

        // Act
        ResponseEntity<Map<String, String>> response = videoController.getTitle("test123");

        // Assert - Verify the true branch is taken
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test Video Title", response.getBody().get("title"));
        assertTrue(response.getBody().containsKey("title"));
        verify(videoRepository, times(1)).findById("test123");
    }

    /**
     * CF Test Case 2: Path where videoOpt.isPresent() == false
     * Control Flow: Branch 2 - Video not found path (else block)
     * Expected: Execute else block, return 404 NOT_FOUND
     */
    @Test
    @DisplayName("CF-2: Control Flow - Video not found path (isPresent == false)")
    void testGetTitle_ControlFlow_VideoNotFoundPath() {
        // Arrange
        when(videoRepository.findById("nonexistent")).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Map<String, String>> response = videoController.getTitle("nonexistent");

        // Assert - Verify the false branch (else) is taken
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Unknown Video", response.getBody().get("title"));
        verify(videoRepository, times(1)).findById("nonexistent");
    }

    /**
     * CF Test Case 3: Path where video exists but title is null
     * Control Flow: Branch 1 with null title edge case
     * Expected: Execute if block, return 200 OK with null title
     */
    @Test
    @DisplayName("CF-3: Control Flow - Video found but title is null")
    void testGetTitle_ControlFlow_VideoFoundWithNullTitle() {
        // Arrange
        Video videoWithNullTitle = new Video();
        videoWithNullTitle.setVideoId("test456");
        videoWithNullTitle.setTitle(null);
        when(videoRepository.findById("test456")).thenReturn(Optional.of(videoWithNullTitle));

        // Act
        ResponseEntity<Map<String, String>> response = videoController.getTitle("test456");

        // Assert - Verify if branch is taken, but title is null
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody().get("title"));
        verify(videoRepository, times(1)).findById("test456");
    }

    /**
     * CF Test Case 4: Path where video exists with empty title
     * Control Flow: Branch 1 with empty string title
     * Expected: Execute if block, return 200 OK with empty title
     */
    @Test
    @DisplayName("CF-4: Control Flow - Video found with empty title")
    void testGetTitle_ControlFlow_VideoFoundWithEmptyTitle() {
        // Arrange
        Video videoWithEmptyTitle = new Video();
        videoWithEmptyTitle.setVideoId("test789");
        videoWithEmptyTitle.setTitle("");
        when(videoRepository.findById("test789")).thenReturn(Optional.of(videoWithEmptyTitle));

        // Act
        ResponseEntity<Map<String, String>> response = videoController.getTitle("test789");

        // Assert - Verify if branch is taken with empty title
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("", response.getBody().get("title"));
        verify(videoRepository, times(1)).findById("test789");
    }

    /**
     * CF Test Case 5: Path testing complete method execution flow
     * Control Flow: Complete method execution - both branches covered
     * Expected: Verify method completes successfully in both scenarios
     */
    @Test
    @DisplayName("CF-5: Control Flow - Complete method execution with both branches")
    void testGetTitle_ControlFlow_CompleteExecutionBothBranches() {
        // Test the true branch
        Video video1 = new Video();
        video1.setVideoId("video1");
        video1.setTitle("Title 1");
        when(videoRepository.findById("video1")).thenReturn(Optional.of(video1));

        ResponseEntity<Map<String, String>> response1 = videoController.getTitle("video1");
        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertEquals("Title 1", response1.getBody().get("title"));

        // Test the false branch
        when(videoRepository.findById("video2")).thenReturn(Optional.empty());
        ResponseEntity<Map<String, String>> response2 = videoController.getTitle("video2");
        assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());
        assertEquals("Unknown Video", response2.getBody().get("title"));

        // Verify both paths were executed
        verify(videoRepository, times(1)).findById("video1");
        verify(videoRepository, times(1)).findById("video2");
    }
}

