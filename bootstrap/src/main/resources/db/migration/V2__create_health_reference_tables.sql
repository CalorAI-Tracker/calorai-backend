create table if not exists activity_level
(
    id          smallint generated always as identity primary key,
    code        text          not null unique,
    title       text          not null,
    description text,
    factor      numeric(4, 3) not null
);

create table if not exists health_goal
(
    id                    smallint generated always as identity primary key,
    code                  text not null unique,
    title                 text not null,
    description           text,
    calorie_delta_percent numeric
);
