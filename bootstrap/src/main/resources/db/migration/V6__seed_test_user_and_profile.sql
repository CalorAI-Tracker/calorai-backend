-- Тестовый пользователь
with u as (
    insert into users (email, password_hash, created_at, email_verified, enabled, name, auth_provider)
    values ('test@test.com', '12345', now(), true, true, 'Тест Тестович', 'local')
    on conflict (id) do nothing
)
insert
into user_roles (user_id, role_id)
select id, 1
from u;

-- Профиль пользователя: женщина, 170 см, 65 кг, умеренная активность, цель похудеть
insert into user_profile (user_id,
                          sex,
                          height_cm,
                          birth_date,
                          health_goal_id,
                          activity_level_id,
                          target_weight_kg,
                          weekly_rate_kg,
                          weight_kg)
values (1000,
        'F',
        170,
        date '2000-08-15',
        1, -- LOSE_WEIGHT
        3, -- MODERATE
        60.0,
        -0.50,
        65.0) on conflict (user_id) do nothing;

-- Стартовая запись в истории веса
insert into health_history (user_id, measured_at, weight, note)
values (1000,
        now(),
        65.0,
        'Начальный вес') on conflict (user_id, measured_at) do nothing;
