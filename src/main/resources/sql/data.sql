INSERT INTO users (id, username, email, password, role, first_name, last_name, created_at, profile_image_url, enabled)
VALUES
    ('11111111-1111-1111-1111-111111111111', 'admin', 'admin@example.com', '$2a$12$LfE80ftlELiMwgGVnZFYTObPuMRACH/zjUETR7S6Uuk8EJf7jS3Bi', 'ADMIN', 'Admin', 'User', CURRENT_TIMESTAMP, 'https://example.com/admin.jpg', true),
    ('22222222-2222-2222-2222-222222222222', 'tutor1', 'tutor1@example.com', '$2a$12$LfE80ftlELiMwgGVnZFYTObPuMRACH/zjUETR7S6Uuk8EJf7jS3Bi', 'TUTOR', 'John', 'Doe', CURRENT_TIMESTAMP, 'https://example.com/tutor1.jpg', true),
    ('33333333-3333-3333-3333-333333333333', 'tutor2', 'tutor2@example.com', '$2a$12$LfE80ftlELiMwgGVnZFYTObPuMRACH/zjUETR7S6Uuk8EJf7jS3Bi', 'TUTOR', 'Jane', 'Smith', CURRENT_TIMESTAMP, 'https://example.com/tutor2.jpg', true);

INSERT INTO tutors (id, description, rating, experience_years)
VALUES
    ('22222222-2222-2222-2222-222222222222', 'Experienced tutor with 10 years of teaching programming', 4.8, 10),
    ('33333333-3333-3333-3333-333333333333', 'Professional tutor specializing in algorithms and data structures', 4.9, 8);

INSERT INTO authorities (username, authority)
VALUES
    ('admin', 'ROLE_ADMIN'),
    ('tutor1', 'ROLE_TUTOR'),
    ('tutor2', 'ROLE_TUTOR');


INSERT INTO subjects (id, name)
VALUES
    ('11111111-1111-1111-1111-111111111111', 'Математика'),
    ('22222222-2222-2222-2222-222222222222', 'Английский язык'),
    ('33333333-3333-3333-3333-333333333333', 'Биология'),
    ('44444444-4444-4444-4444-444444444444', 'Информатика'),
    ('55555555-5555-5555-5555-555555555555', 'Русский язык');

INSERT INTO ads (
    id,
    created_at,
    description,
    hourly_pay,
    is_active,
    subject_id,
    title,
    tutor_id
)
VALUES
    (
        'a1f0d6e2-1234-4c9f-9876-a6b1f1f4e2c3',
        TIMESTAMP '2025-05-23 10:15:00',
        'Подготовка к ЕГЭ по математике, базовый и профильный уровень',
        15,
        TRUE,
        '11111111-1111-1111-1111-111111111111',
        'Математика ЕГЭ',
        '22222222-2222-2222-2222-222222222222'
    ),
    (
        'b7e9c3a4-5678-4f0b-a3c2-d5b7c3b9e4f5',
        TIMESTAMP '2025-05-22 16:00:00',
        'Разговорный английский язык для школьников 7-11 классов',
        20,
        TRUE,
        '22222222-2222-2222-2222-222222222222',
        'Английский для подростков',
        '22222222-2222-2222-2222-222222222222'
    ),
    (
        'c3a1f8d9-9012-4cdd-889a-e7c5f6a1d9e8',
        TIMESTAMP '2025-05-21 13:45:00',
        'Индивидуальные занятия по биологии, подготовка к ОРТ',
        18,
        TRUE,
        '33333333-3333-3333-3333-333333333333',
        'Биология ОРТ',
        '33333333-3333-3333-3333-333333333333'
    ),
    (
        'd6b4e7f1-3456-4a6f-b8d2-f3c7e4d1b2a0',
        TIMESTAMP '2025-05-20 09:00:00',
        'Подготовка к олимпиадам по информатике',
        25,
        TRUE,
        '44444444-4444-4444-4444-444444444444',
        'Олимпиадная информатика',
        '33333333-3333-3333-3333-333333333333'
    ),
    (
        'e2c9f6a3-7890-4e1b-b9f3-a9c2e7f1d4a5',
        TIMESTAMP '2025-05-19 17:30:00',
        'Русский язык: грамматика и подготовка к экзаменам',
        14,
        TRUE,
        '55555555-5555-5555-5555-555555555555',
        'Грамматика и ОРТ',
        '33333333-3333-3333-3333-333333333333'
    );
