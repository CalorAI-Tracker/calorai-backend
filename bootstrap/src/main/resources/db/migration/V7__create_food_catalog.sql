-- Создаём таблицу каталога продуктов
create table if not exists food_catalog
(
    id               bigint generated always as identity primary key,
    name             text          not null,
    brand            text,
    barcode          text,
    provider         text          not null default 'user', -- 'user', 'fatsecret', 'openfoodfacts'
    provider_food_id text,
    created_by       bigint,                                -- null если из внешнего API
    kcal_per_100g    numeric(6, 2),
    protein_per_100g numeric(6, 2),
    fat_per_100g     numeric(6, 2),
    carbs_per_100g   numeric(6, 2),
    fiber_per_100g   numeric(6, 2),
    sugar_per_100g   numeric(6, 2),
    sodium_per_100g  numeric(6, 2),
    created_at       timestamptz   not null default now(),
    constraint food_catalog_created_by_fkey foreign key (created_by)
    references users (id) on delete set null,
    constraint food_catalog_provider_food_unique unique (provider, provider_food_id)
    );

-- Индексы для поиска
create extension if not exists pg_trgm;

create index if not exists idx_food_catalog_name_trgm
    on food_catalog using gin (name gin_trgm_ops);

create index if not exists idx_food_catalog_brand_trgm
    on food_catalog using gin (brand gin_trgm_ops);

create index if not exists idx_food_catalog_barcode
    on food_catalog (barcode);

create index if not exists idx_food_catalog_provider
    on food_catalog (provider, provider_food_id);

-- Добавляем ссылку на каталог в food_diary
alter table food_diary
    add column if not exists food_catalog_id bigint,
    add constraint food_diary_catalog_fkey
    foreign key (food_catalog_id) references food_catalog (id) on delete set null;