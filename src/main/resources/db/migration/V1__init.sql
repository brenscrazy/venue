create table users (
    id uuid not null,
    email varchar(255) not null unique,
    password varchar(255) not null,
    role varchar(255) not null check (
        role in ('USER', 'ADMIN')
    ),
    username varchar(255) not null unique,
    primary key (id)
);

create table file (
    id uuid not null,
    user_id uuid not null,
    path varchar(255) not null,
    constraint file_pk primary key (id),
    constraint uploaded_by_fk foreign key (user_id) references users
);

create table venue (
    id uuid not null,
    id_name varchar(255) unique not null,
    display_name varchar(255) not null,
    creator_username varchar(255) not null,
    constraint venue_pk primary key (id),
    constraint creator_username_fk foreign key (creator_username) references users(username)
);

create table user_venue_subscriptions(
    id uuid not null,
    user_id uuid not null,
    venue_id uuid not null,
    constraint user_venue_subscriptions_pk primary key (id),
    constraint user_fk foreign key (user_id) references users,
    constraint venue_fk foreign key (venue_id) references venue,
    constraint pair_pk unique (user_id, venue_id)
)

--create table post (
--  author_id uuid,
--  id uuid not null,
--  file_id uuid unique,
--  header varchar(255),
--  post_text varchar(255),
--  primary key (id)
--);
