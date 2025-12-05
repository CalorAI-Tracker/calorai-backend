create table if not exists users
(
    id             bigserial primary key,
    email          varchar(100) unique,
    password_hash  varchar(255),
    created_at     timestamptz not null default now(),
    last_login_at  timestamptz,
    email_verified boolean,
    auth_provider  text,
    provider_id    text,
    enabled        boolean,
    name           text
);

create table if not exists role
(
    id          bigserial primary key,
    name        text not null unique,
    description text
);

create table if not exists user_roles
(
    user_id bigint not null,
    role_id bigint not null,
    constraint user_roles_pkey primary key (user_id, role_id),
    constraint user_roles_user_id_fkey foreign key (user_id)
        references users (id) on delete cascade,
    constraint user_roles_role_id_fkey foreign key (role_id)
        references role (id) on delete cascade
);

insert into role (name, description)
values ('USER', 'Стандартный пользователь'),
       ('ADMIN', 'Администратор'),
       ('DEVELOPER', 'Разработчик') on conflict (id) do nothing;

create table if not exists refresh_tokens
(
    id              bigserial primary key,
    jti             uuid         not null unique,
    user_id         bigint       not null,
    token_hash      varchar(255) not null,
    issued_at       timestamptz  not null,
    expires_at      timestamptz  not null,
    revoked         boolean      not null default false,
    replaced_by_jti uuid,
    device_id       varchar(100),
    user_agent      text,
    ip_address      varchar(45),
    constraint refresh_tokens_user_id_fkey foreign key (user_id)
        references users (id) on delete cascade
);
