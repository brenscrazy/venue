create table users (
    id uuid not null,
    email varchar(255) not null unique,
    password varchar(255) not null,
    role varchar(255) not null check (
        role in ('USER')
    ),
    username varchar(255) not null unique,
    primary key (id)
);

create table venue (
    id uuid not null,
    id_name varchar(255) unique not null,
    display_name varchar(255) not null,
    creator_id uuid not null,
    constraint venue_pk primary key (id),
    constraint creator_id_fk foreign key (creator_id) references users(id)
);

create table user_venue_subscriptions(
    id uuid not null,
    user_id uuid not null,
    venue_id uuid not null,
    constraint user_venue_subscriptions_pk primary key (id),
    constraint user_fk foreign key (user_id) references users(id),
    constraint venue_subscriptions_fk foreign key (venue_id) references venue(id),
    constraint pair_pk unique (user_id, venue_id)
);

create table events(
    id uuid not null,
    takes_place_at_venue_id uuid not null,
    event_date timestamp not null,
    display_name varchar(255) not null,
    constraint events_pk primary key (id),
    constraint venue_events_fk foreign key (takes_place_at_venue_id) references venue(id)
);