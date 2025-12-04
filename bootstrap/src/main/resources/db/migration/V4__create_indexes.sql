create index if not exists idx_health_history_user_date
    on health_history (user_id asc, measured_at desc);

create index if not exists ix_refresh_tokens_user
    on refresh_tokens (user_id);

create unique index if not exists ux_refresh_tokens_jti
    on refresh_tokens (jti);
