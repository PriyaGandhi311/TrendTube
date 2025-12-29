# Code Smells Analysis and Refactoring Report

## Identified Code Smells

### 1. **Missing @Autowired Annotation** (AnalyticsController.java:31)
- **Location**: `analytics-service/analytics-service/src/main/java/com/trendtube/analytics_service/controller/AnalyticsController.java`
- **Issue**: Field `engagementRepository` is declared but not annotated with `@Autowired`, causing it to be null
- **Severity**: Critical - Will cause NullPointerException

### 2. **Use of System.out.println Instead of Logger** (Multiple locations)
- **Location 1**: `RabbitMQListener.java:21,23`
- **Location 2**: `UploadController.java:55`
- **Issue**: Using System.out.println for logging instead of proper logging framework
- **Severity**: Medium - Poor practice, not production-ready

### 3. **Use of printStackTrace() Instead of Logger** (UserController.java:75)
- **Location**: `analytics-service/analytics-service/src/main/java/com/trendtube/analytics_service/controller/UserController.java`
- **Issue**: Using printStackTrace() instead of proper logging
- **Severity**: Medium - Poor error handling

### 4. **Creating RestTemplate Instance Instead of Injecting** (YouTubeService.java:32)
- **Location**: `processing-video-service/processing-video-service/src/main/java/com/trendtube/processing_video_service/service/YouTubeService.java`
- **Issue**: Creating new RestTemplate instance instead of injecting it as a bean
- **Severity**: Medium - Not following Spring best practices

### 5. **Inconsistent Field Injection Pattern** (UserController.java:35-36)
- **Location**: `analytics-service/analytics-service/src/main/java/com/trendtube/analytics_service/controller/UserController.java`
- **Issue**: Field declared, then @Autowired annotation on separate line, then constructor injection - inconsistent pattern
- **Severity**: Low - Code style issue

### 6. **Magic Numbers/Strings** (Multiple locations)
- **Location**: Various hardcoded URLs and status codes
- **Issue**: Hardcoded values should be constants or configuration
- **Severity**: Low - Maintainability issue

### 7. **Long Method** (YouTubeService.java:fetchAndSaveMetadata)
- **Location**: `processing-video-service/processing-video-service/src/main/java/com/trendtube/processing_video_service/service/YouTubeService.java`
- **Issue**: Method does too many things - fetching, parsing, and saving
- **Severity**: Medium - Violates Single Responsibility Principle

### 8. **Inconsistent Indentation** (UploadController.java:86-95)
- **Location**: `upload-video-service/upload-video-service/src/main/java/com/trendtube/upload_video_service/controller/UploadController.java`
- **Issue**: Method has inconsistent indentation
- **Severity**: Low - Code style issue

## Refactoring Plan

We will refactor the following 5 code smells:
1. Missing @Autowired annotation in AnalyticsController
2. System.out.println in RabbitMQListener
3. printStackTrace() in UserController
4. RestTemplate instantiation in YouTubeService
5. System.out.println in UploadController


