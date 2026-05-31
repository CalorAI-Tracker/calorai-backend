ALTER TABLE food_diary
DROP COLUMN IF EXISTS kcal,
    DROP COLUMN IF EXISTS protein_g,
    DROP COLUMN IF EXISTS fat_g,
    DROP COLUMN IF EXISTS carbs_g,
    DROP COLUMN IF EXISTS fiber_g,
    DROP COLUMN IF EXISTS sugar_g,
    DROP COLUMN IF EXISTS sodium_mg,
    DROP COLUMN IF EXISTS entry_name,
    DROP COLUMN IF EXISTS brand,
    DROP COLUMN IF EXISTS barcode,
    DROP COLUMN IF EXISTS provider_code,
    DROP COLUMN IF EXISTS provider_food_id;

-- Сначала чистим старые данные
TRUNCATE TABLE food_diary;

-- Теперь можно ставить NOT NULL
ALTER TABLE food_diary
    ALTER COLUMN food_catalog_id SET NOT NULL;