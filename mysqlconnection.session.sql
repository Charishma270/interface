-- Create database (only if not exists)
CREATE DATABASE IF NOT EXISTS userdata;
USE userdata;

-- Create user table
CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    confirmPassword VARCHAR(255) NOT NULL,
    verification_code VARCHAR(10),
    is_verified BOOLEAN DEFAULT FALSE
);
ALTER TABLE user DROP COLUMN IF EXISTS confirmPassword;

SELECT * FROM user;

