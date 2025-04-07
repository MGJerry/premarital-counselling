-- Create roles table if it doesn't exist
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
);

-- Insert roles if they don't exist
INSERT IGNORE INTO roles (name) VALUES 
    ('ROLE_USER'),
    ('ROLE_EXPERT'),
    ('ROLE_ADMIN');

-- Create users table if it doesn't exist
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(120) NOT NULL,
    full_name VARCHAR(100),
    gender VARCHAR(10),
    country VARCHAR(50),
    address VARCHAR(200),
    phone VARCHAR(20),
    birthday DATE,
    imgurl VARCHAR(255),
    role VARCHAR(20) NOT NULL,
    e_status VARCHAR(20) NOT NULL,
    bio TEXT,
    dtype VARCHAR(31) NOT NULL
);

-- Create experts table if it doesn't exist
CREATE TABLE IF NOT EXISTS experts (
    id BIGINT PRIMARY KEY,
    language VARCHAR(50),
    gg_meet_url VARCHAR(255),
    consulting_price DECIMAL(10,2),
    commission DECIMAL(10,2) NOT NULL DEFAULT 0,
    average_rating DOUBLE DEFAULT 0,
    total_ratings INT DEFAULT 0,
    specialization_level INT,
    FOREIGN KEY (id) REFERENCES users(id)
);

-- Create assessment_categories table if it doesn't exist
CREATE TABLE IF NOT EXISTS assessment_categories (
    category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT
);

-- Create expert_categories table if it doesn't exist
CREATE TABLE IF NOT EXISTS expert_categories (
    expert_id BIGINT,
    category_id BIGINT,
    PRIMARY KEY (expert_id, category_id),
    FOREIGN KEY (expert_id) REFERENCES experts(id),
    FOREIGN KEY (category_id) REFERENCES assessment_categories(category_id)
); 