CREATE TABLE IF NOT EXISTS AUTHORITIES (
                                           username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    FOREIGN KEY (username) REFERENCES USERS(username)
    );
