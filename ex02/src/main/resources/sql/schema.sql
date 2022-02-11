drop table if exists users;
CREATE TABLE users(
                      id SERIAL PRIMARY KEY,
                      name varchar(50) not null,
                      surname varchar(50) not null,
                      phone varchar(50) unique not null,
                      email varchar(50) not null,
                      password varchar(100) not null,
                      avatar varchar(100) not null);

drop table if exists auth;
CREATE TABLE auth(
                      email varchar(50) not null,
                      date varchar(50) not null,
                      time varchar(50) not null,
                      ip varchar(50) not null);