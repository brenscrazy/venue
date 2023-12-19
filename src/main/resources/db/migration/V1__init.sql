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
  creator_id uuid not null,
  name varchar(255) not null,
  constraint venue_pk primary key (id)
);

--create table post (
--  author_id uuid,
--  id uuid not null,
--  file_id uuid unique,
--  header varchar(255),
--  post_text varchar(255),
--  primary key (id)
--);
