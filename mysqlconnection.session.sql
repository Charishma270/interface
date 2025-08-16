CREATE DATABASE userdata;

USE userdata;

CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    confirm_password VARCHAR(255) NOT NULL
);
ALTER TABLE user CHANGE COLUMN confirm_password confirmPassword VARCHAR(255) NOT NULL;


-- Note: Ensure that 'password' and 'confirm_password' are equal in the application logic before inserting into the database.