CREATE DATABASE debate_tournament_manager;
use debate_tournament_manager;
CREATE TABLE team (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    institution VARCHAR(100) NOT NULL
);

CREATE TABLE user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL
);

CREATE TABLE tournament (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    date DATE DEFAULT NULL,
    id_user INT NOT NULL,
    FOREIGN KEY (id_user) REFERENCES user(id) ON DELETE CASCADE
);

CREATE TABLE participant (
    dni VARCHAR(9) PRIMARY KEY NOT NULL,
    role VARCHAR(20) NOT NULL,
    gender VARCHAR(20) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    age INT NOT NULL,
    id_team INT NOT NULL,
    FOREIGN KEY (id_team) REFERENCES team(id) ON DELETE CASCADE
);

CREATE TABLE participation (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_team INT NOT NULL,
    id_tournament INT NOT NULL,
    FOREIGN KEY (id_team) REFERENCES team(id) ON DELETE CASCADE,
    FOREIGN KEY (id_tournament) REFERENCES tournament(id) ON DELETE CASCADE
);