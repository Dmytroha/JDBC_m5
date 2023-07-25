CREATE TABLE IF NOT EXISTS client (
    ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(1000) NOT NULL CHECK (length(NAME) >= ? AND length(NAME) <= ?)
);