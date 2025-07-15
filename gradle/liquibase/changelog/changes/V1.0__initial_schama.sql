create sequence users_seq start with 1 increment by 50;

    create table posts (
        content varchar(255) not null,
        id varchar(255) not null,
        primary key (id)
    );

    create table users (
        created_at date,
        updated_at date,
        id bigint not null,
        password varchar(24) not null,
        email varchar(50) not null unique,
        name varchar(100) not null,
        role varchar(255) check (role in ('USER','ADMIN')),
        primary key (id)
    )
