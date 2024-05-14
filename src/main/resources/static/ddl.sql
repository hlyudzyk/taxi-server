START TRANSACTION;

CREATE DATABASE
    IF NOT EXISTS taxi_app
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;


DROP TABLE IF EXISTS taxi_app._users;
DROP TABLE IF EXISTS taxi_app.cars;
DROP TABLE IF EXISTS taxi_app.messages;
DROP TABLE IF EXISTS taxi_app.orders;
DROP TABLE IF EXISTS taxi_app.tokens;

-- Table: _users
CREATE TABLE _users (
                       id VARCHAR(36) NOT NULL,
                       firstname VARCHAR(255),
                       lastname VARCHAR(255),
                       email VARCHAR(255),
                       password VARCHAR(255),
                       role VARCHAR(255) CHECK (role IN ('USER', 'ADMIN', 'DRIVER')),
                       birthday DATE,
                       last_session TIMESTAMP(6),
                       PRIMARY KEY (id)
) ENGINE=INNODB;

-- Table: cars
CREATE TABLE cars (
                      id VARCHAR(36) NOT NULL,
                      driver_id VARCHAR(36),
                      car_plate VARCHAR(255) NOT NULL UNIQUE,
                      manufacturer VARCHAR(255),
                      model VARCHAR(255),
                      production_year INT NOT NULL,
                      PRIMARY KEY (id),
                      FOREIGN KEY (driver_id) REFERENCES _users(id)
) ENGINE=INNODB;

-- Table: message_recipients
CREATE TABLE message_recipients (
                                    message_id VARCHAR(36) NOT NULL,
                                    recipient_id VARCHAR(36) NOT NULL,
                                    PRIMARY KEY (message_id, recipient_id),
                                    FOREIGN KEY (message_id) REFERENCES messages(id),
                                    FOREIGN KEY (recipient_id) REFERENCES _users(id)
) ENGINE=INNODB;

-- Table: messages
CREATE TABLE messages (
                          id VARCHAR(36) NOT NULL,
                          sender_id VARCHAR(36),
                          content VARCHAR(255),
                          subject VARCHAR(255),
                          PRIMARY KEY (id),
                          FOREIGN KEY (sender_id) REFERENCES _users(id)
) ENGINE=INNODB;

-- Table: orders
CREATE TABLE orders (
                        id VARCHAR(36) NOT NULL,
                        user_id VARCHAR(36),
                        driver_id VARCHAR(36),
                        car_id VARCHAR(36),
                        pickup_address VARCHAR(255),
                        delivery_address VARCHAR(255),
                        ordered_at TIMESTAMP(6),
                        finished_at TIMESTAMP(6),
                        taxi_type VARCHAR(255) CHECK (taxi_type IN ('BASIC', 'PREMIUM', 'CARGO')),
                        order_status VARCHAR(255) CHECK (order_status IN ('PENDING', 'IN_PROGRESS', 'CANCELLED', 'COMPLETED')),
                        distance FLOAT NOT NULL,
                        total_price FLOAT NOT NULL,
                        PRIMARY KEY (id),
                        FOREIGN KEY (user_id) REFERENCES _users(id),
                        FOREIGN KEY (driver_id) REFERENCES _users(id),
                        FOREIGN KEY (car_id) REFERENCES cars(id)
) ENGINE=INNODB;

-- Table: tokens
CREATE TABLE tokens (
                       id VARCHAR(36) NOT NULL,
                       user_id VARCHAR(36),
                       token VARCHAR(255) UNIQUE,
                       token_type VARCHAR(255) CHECK (token_type IN ('BEARER')),
                       is_revoked BOOLEAN NOT NULL,
                       is_expired BOOLEAN NOT NULL,
                       PRIMARY KEY (id),
                       FOREIGN KEY (user_id) REFERENCES _users(id)
);
