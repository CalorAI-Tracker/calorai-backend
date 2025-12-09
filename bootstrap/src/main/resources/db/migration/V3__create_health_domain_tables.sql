create table if not exists user_profile
(
    id bigint generated always as identity primary key,
    user_id           bigint        not null,
    sex               char(1)       not null,
    height_cm         smallint      not null,
    birth_date        date,
    health_goal_id    smallint      not null,
    activity_level_id smallint      not null,
    target_weight_kg  numeric(5, 2),
    weekly_rate_kg    numeric(4, 2),
    updated_at        timestamptz   not null default now(),
    weight_kg         numeric(4, 2) not null,
    constraint fk_user_profile_user foreign key (user_id)
        references users (id) on delete cascade,
    constraint user_profile_activity_level_id_fkey foreign key (activity_level_id)
        references activity_level (id),
    constraint user_profile_health_goal_id_fkey foreign key (health_goal_id)
        references health_goal (id),
    constraint user_profile_height_cm_check check (height_cm >= 50 and height_cm <= 300),
    constraint user_profile_sex_check check (sex in ('M', 'F')),
    constraint weekly_rate_bounds check (weekly_rate_kg is null or weekly_rate_kg between -2.00 and 2.00)
);

create table if not exists health_history
(
    user_id     bigint        not null,
    measured_at timestamptz   not null default now(),
    weight      numeric(5, 2) not null,
    note        text,
    constraint health_history_pkey primary key (user_id, measured_at),
    constraint fk_health_history_user foreign key (user_id)
        references users (id) on delete cascade
);

create table if not exists user_daily_targets
(
    user_id          bigint not null,
    date             date   not null,
    kcal_target      integer,
    protein_g_target numeric(6, 2),
    fat_g_target     numeric(6, 2),
    carbs_g_target   numeric(6, 2),
    source           text default 'mifflin_st_jeor',
    constraint user_daily_targets_pkey primary key (user_id, date),
    constraint user_daily_targets_user_id_fkey foreign key (user_id)
        references users (id) on delete cascade
);

create table if not exists daily_intake_totals
(
    user_id     bigint        not null,
    date        date          not null,
    kcal        integer       not null,
    protein_g   numeric(6, 2) not null,
    fat_g       numeric(6, 2) not null,
    carbs_g     numeric(6, 2) not null,
    entries_cnt integer       not null,
    constraint daily_intake_totals_pkey primary key (user_id, date),
    constraint daily_intake_totals_user_id_fkey foreign key (user_id)
        references users (id) on delete cascade
);

DO $$
BEGIN
CREATE TYPE meal AS ENUM ('BREAKFAST', 'LUNCH', 'DINNER', 'SNACK');
EXCEPTION
    WHEN duplicate_object THEN
        NULL; -- тип уже существует, просто молчим
END $$;

create table if not exists food_diary
(
    id bigint generated always as identity primary key,
    user_id          bigint        not null,
    eaten_at         date          not null,
    meal             text          not null,
    entry_name       text          not null,
    brand            text,
    barcode          text,
    provider_code    text,
    provider_food_id text,
    quantity_grams   numeric(7, 2) not null,
    kcal             integer       not null,
    protein_g        numeric(6, 2) not null,
    fat_g            numeric(6, 2) not null,
    carbs_g          numeric(6, 2) not null,
    fiber_g          numeric(6, 2),
    sugar_g          numeric(6, 2),
    sodium_mg        integer,
    note             text,
    created_at       timestamptz   not null default now(),
    constraint food_diary_user_id_fkey foreign key (user_id)
        references users (id) on delete cascade
);
