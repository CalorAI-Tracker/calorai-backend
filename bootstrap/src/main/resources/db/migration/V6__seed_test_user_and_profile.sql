-- 1. Создаём пользователя и получаем его id (если он новый)
WITH u AS (
INSERT INTO users (email, password_hash, created_at, email_verified, enabled, name, auth_provider)
VALUES ('test@test.com', '12345', now(), true, true, 'Тест Тестович', 'local')
ON CONFLICT (email) DO NOTHING
    RETURNING id
    )

-- 2. Присваиваем роль USER (если пользователь новый)
INSERT INTO user_roles (user_id, role_id)
SELECT id, 1 FROM u;

-- 2b. Присваиваем роль USER, если пользователь уже существовал ранее
INSERT INTO user_roles (user_id, role_id)
SELECT id, 1
FROM users
WHERE email = 'test@test.com'
  AND NOT EXISTS (
    SELECT 1 FROM user_roles ur
    WHERE ur.user_id = users.id AND ur.role_id = 1
);

-- 3. Создаём профиль пользователя (без ON CONFLICT)
INSERT INTO user_profile (
    user_id,
    sex,
    height_cm,
    birth_date,
    health_goal_id,
    activity_level_id,
    target_weight_kg,
    weekly_rate_kg,
    weight_kg
)
SELECT id,
       'F',
       170,
       DATE '2000-08-15',
       1,
       3,
       60.0,
       -0.50,
       65.0
FROM users
WHERE email = 'test@test.com'
  AND NOT EXISTS (
    SELECT 1 FROM user_profile p
    WHERE p.user_id = users.id
);

-- 4. Добавляем стартовую запись в историю веса
INSERT INTO health_history (user_id, measured_at, weight, note)
SELECT id, NOW(), 65.0, 'Начальный вес'
FROM users
WHERE email = 'test@test.com'
  AND NOT EXISTS (
    SELECT 1 FROM health_history h
    WHERE h.user_id = users.id
      AND h.note = 'Начальный вес'
);
