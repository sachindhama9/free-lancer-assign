  # Spring Boot REST API for FreeLancer Management System
  
  This project is a Spring Boot application that exposes a REST API to manage freelancers.
  It supports the registration, update, deletion of freelancers, and notifies a separate notification service of
  these events asynchronously.
  
  ## Features
  
  1. **Freelancer Registration, Update, and Deletion**: Freelancers can register themselves with first name, last name, date of birth, and gender. They can also update or delete their profiles.
  2. **Async Notification**: Every time a freelancer registers, updates, or deletes their profile, an asynchronous notification is sent to the Notification Service, which processes and persists the event.
  3. **Freelancer Verification**: Staff users are responsible for approving freelancers. All newly registered freelancers are listed for staff users to verify, and once verified, the freelancer's status changes from `NEW_FREELANCER` to `VERIFIED`.
  4. **Deleted Freelancers Retrieval**: Staff users can also fetch all freelancers who marked their profiles for deletion in the last 7 days.
  
  ## Architecture Overview
  
  This project uses the following components:
  - **Spring Boot**: As the main framework to build RESTful APIs.
  - **Spring Data JPA**: For interacting with the database using JPA repositories.
  - **Spring Kafka**: For async communication between services using Kafka.
  - **Swagger/OpenAPI**: To generate API documentation.
  - **JUnit and Mockito**: For unit testing the application.
  
  ## Project Structure
  
  The project is structured into two main services:
  1. **Freelancer Service**: Manages freelancer registration, updates, and deletion.
  2. **Notification Service**: Receives notifications of freelancer events asynchronously and persists them.
  
  ## Dependencies
  
  Here are the main dependencies used in this project:
  - Spring Boot 1.5.22.RELEASE
  - Spring Data JPA
  - Kafka
  - Swagger for API documentation
  - JUnit 4 for unit testing
  - Docker and Docker Compose for containerization
  
  ## Prerequisites
  
  Before running the application, make sure you have the following installed:
  1. JDK 1.8
  2. Apache Kafka
  3. Docker and Docker Compose
  4. Maven
  
  ## Running the Application
  
  ### 1. Clone the Repository
  ```bash
  git clone <repository-url>
  cd freelancer
  ```
  
  ### 2. Run Kafka
  Start the Kafka broker using Docker Compose:
  ```bash
  docker-compose up -d
  ```
  
  ### 3. Run the Application
  Maven is used to build the code
  To run the Spring Boot application:
  ```bash
  mvn spring-boot:run
  ```
  
  ### 4. Access the Application
  - **API Documentation (Swagger UI)**: `http://localhost:8081/swagger-ui.html`
  - **API Endpoints**:
    - POST /api/freelancers: Register a freelancer.
    - PUT /api/freelancers/{id}: Update freelancer details.
    - DELETE /api/freelancers/{id}: Delete a freelancer.
  
  ### 5. Running Tests
  Run unit tests using:
  ```bash
  mvn test
  ```
  
  ## Design Decisions
  
  1. **Kafka for Asynchronous Communication**: We chose Kafka to ensure reliable, decoupled communication between the Freelancer and Notification services.
  2. **Docker**: Containerization ensures that the application runs consistently across different environments.
  3. **Spring Data JPA**: Simplifies database access and management through the repository pattern.
  
