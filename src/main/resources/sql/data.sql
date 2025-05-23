INSERT INTO users (id, username, email, password, role, first_name, last_name, created_at, profile_image_url, enabled)
VALUES
    ('11111111-1111-1111-1111-111111111111', 'admin', 'admin@example.com', '$2a$12$LfE80ftlELiMwgGVnZFYTObPuMRACH/zjUETR7S6Uuk8EJf7jS3Bi', 0, 'Admin', 'User', CURRENT_TIMESTAMP, 'https://example.com/admin.jpg', true),
    ('22222222-2222-2222-2222-222222222222', 'tutor1', 'tutor1@example.com', '$2a$12$LfE80ftlELiMwgGVnZFYTObPuMRACH/zjUETR7S6Uuk8EJf7jS3Bi', 1, 'John', 'Doe', CURRENT_TIMESTAMP, 'https://example.com/tutor1.jpg', true),
    ('33333333-3333-3333-3333-333333333333', 'tutor2', 'tutor2@example.com', '$2a$12$LfE80ftlELiMwgGVnZFYTObPuMRACH/zjUETR7S6Uuk8EJf7jS3Bi', 1, 'Jane', 'Smith', CURRENT_TIMESTAMP, 'https://example.com/tutor2.jpg', true);

INSERT INTO tutors (id, description, rating, experience_years)
VALUES
    ('22222222-2222-2222-2222-222222222222', 'Experienced tutor with 10 years of teaching programming', 4.8, 10),
    ('33333333-3333-3333-3333-333333333333', 'Professional tutor specializing in algorithms and data structures', 4.9, 8);

INSERT INTO authorities (username, authority)
VALUES
    ('admin', 'ROLE_ADMIN'),
    ('tutor1', 'ROLE_TUTOR'),
    ('tutor2', 'ROLE_TUTOR');