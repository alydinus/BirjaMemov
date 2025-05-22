//потом допилю что бы норм работало
CREATE TABLE IF NOT EXISTS AUTHORITIES (
                                           username VARCHAR(50) NOT NULL,
                                            authority VARCHAR(50) NOT NULL,
                                            FOREIGN KEY (username) REFERENCES USERS(username)
    );


INSERT INTO users (id, username, email, password, role, first_name, last_name, created_at, profile_image_url, enabled)
VALUES
    ('550e8400-e29b-41d4-a716-446655440001', 'tutor1', 'tutor1@example.com', '$2a$12$yWJRq2CHwvIQYBZgL1AlEe1o9HWG1mSUQgS7SK49.66j5OTpQ9cmO', 1, 'John', 'Doe', '2023-01-02 11:00:00', 'https://example.com/tutor1.jpg', true),
    ('550e8400-e29b-41d4-a716-446655440002', 'tutor2', 'tutor2@example.com', '$2a$12$yWJRq2CHwvIQYBZgL1AlEe1o9HWG1mSUQgS7SK49.66j5OTpQ9cmO', 1, 'Jane', 'Smith', '2023-01-03 12:00:00', 'https://example.com/tutor2.jpg', true);

INSERT INTO tutors (id, description, rating, experience_years)
VALUES
    ('550e8400-e29b-41d4-a716-446655440001', 'Опытный преподаватель математики с 10-летним стажем', 4.8, 10),
    ('550e8400-e29b-41d4-a716-446655440002', 'Преподаватель английского языка, носитель', 4.9, 8);

INSERT INTO authorities (username, authority)
VALUES
    ('tutor1', 'ROLE_TUTOR'),
    ('tutor2', 'ROLE_TUTOR');username, authority);