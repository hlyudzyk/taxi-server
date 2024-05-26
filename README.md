# Taxi App Server

## Overview

The Taxi App Server is a backend application designed to manage and coordinate the operations of a taxi service. It handles user registration, order management, driver tracking, and more. The application is built using Spring Boot, Hibernate, and MySQL, and provides RESTful endpoints for various functionalities.

## Features

- User registration and authentication
- Order management (create, retrieve, filter orders)
- Driver management and tracking
- Admin functionalities
- Integration with Google Maps API for geolocation services

## Technologies Used

- Java
- Spring Boot
- Hibernate (JPA)
- MySQL
- H2 (for testing)
- Google Maps API
- Maven

## Prerequisites

- Java 11 or higher
- Maven
- MySQL

## Getting Started

### Setting Up the Database

1. **Install MySQL** if it's not already installed.
2. **Create a database** for the application:
   ```sql
   CREATE DATABASE taxi_app;
   ```
3. **Update the database configuration** in `src/main/resources/application.yml`:
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/taxi_app
       username: your_mysql_username
       password: your_mysql_password
       driver-class-name: com.mysql.cj.jdbc.Driver
     jpa:
       hibernate:
         ddl-auto: update
       show-sql: true
       properties:
         hibernate:
           format_sql: true
       database: mysql
   ```

### Running the Application

1. **Clone the repository**:
   ```sh
   git clone https://github.com/hlyudzyk/taxi-server.git
   cd taxi-app-server
   ```
2. **Build the project** using Maven:
   ```sh
   mvn clean install
   ```
3. **Run the application**:
   ```sh
   mvn spring-boot:run
   ```

## API Endpoints

### Authentication

- **Authenticate User**
  ```
  POST /api/v1/auth/authenticate
  ```

- **Register User**
  ```
  POST /api/v1/auth/register
  ```

### Orders

- **Get All Orders**
  ```
  GET /api/v1/order/all
  ```

- **Create New Order**
  ```
  POST /api/v1/order/new
  ```

### Drivers

- **Get All Drivers**
  ```
  GET /api/v1/driver/all
  ```

- **Get Online Drivers**
  ```
  GET /api/v1/driver/online
  ```

## Configuration

### Application Properties

Configuration for the application is done via `application.yml` files located in `src/main/resources` and `src/test/resources`. 

### Setting Up Different Datasources for Tests

The application uses a different datasource for running tests. The configuration for the test datasource is located in `src/test/resources/application-test.yml`.

## Google Maps API Integration

To use the Google Maps API for geocoding services, you need to obtain an API key from the Google Cloud Platform and set it up in your application.

1. **Obtain an API Key** from the Google Cloud Platform.
2. **Configure the API Key** in your application:
   ```yaml
   api-keys:
     maps:
       api-key: YOUR_GOOGLE_MAPS_API_KEY
   ```

## Contribution

Contributions to the Taxi App Server are welcome. Please follow these steps to contribute:

1. **Fork the repository**.
2. **Create a new branch** (`git checkout -b feature/your-feature`).
3. **Commit your changes** (`git commit -am 'Add some feature'`).
4. **Push to the branch** (`git push origin feature/your-feature`).
5. **Create a new Pull Request**.

## License

This project is licensed under the MIT License. See the LICENSE file for details.

## Contact

For any inquiries or issues, please contact the project maintainer.
