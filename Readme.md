# 🎬 TrendTube: YouTube Video Analytics Platform

TrendTube is a microservices-based web application that allows users to submit YouTube video links, analyze metadata, and manage personal favorites. Built with Spring Boot, React, RabbitMQ, and MySQL, it offers a scalable and modular architecture for video processing and user interaction.

---

## 🧱 Microservices Overview

| Service             | Port  | Description                                      |
|---------------------|-------|--------------------------------------------------|
| `upload-service`    | 8080  | Accepts YouTube URLs, extracts video IDs, and publishes them to RabbitMQ for processing |
| `analytics-service` | 8083  | Fetches video metadata, stores user favorites, and serves analytics |
| `processor-service` | 8081  | Listens to RabbitMQ, fetches YouTube metadata using API key, and stores it in the database |

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/PriyaGandhi311/trendtube.git
cd trendtube
```

### 2. Prerequisites
- Java 17+
- Node.js 18+
- Docker & Docker Compose
- MySQL 8+
- RabbitMQ (Dockerized or local)
- YouTube Data API v3 Key

### API Key Setup
The processor-service requires a YouTube Data API key to fetch video metadata.
- Go to Google Cloud Console
- Enable YouTube Data API v3
- Generate an API key
- Add it to application.properties in processor-service

### Database Configuration
Each service connects to its own MySQL schema. Update the following in each service’s application.properties:
spring.datasource.url=jdbc:mysql://localhost:3306/{schema_name}
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
Ensure your tables are created with proper primary keys and auto-increment settings. 

### Running the Microservice
Backend (Spring Boot)
Each service can be run independently:
```bash 
cd upload-service
./mvnw spring-boot:run

cd ../analytics-service
./mvnw spring-boot:run

cd ../processor-service
./mvnw spring-boot:run
```

Frontend (React)
```bash
cd frontend
npm install
npm start
```

### Tech Stack
- Frontend: React, Axios, Toastify
- Backend: Spring Boot, JPA, RabbitMQ, MySQL
- Messaging: RabbitMQ
- API: YouTube Data API v3
