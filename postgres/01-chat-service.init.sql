create schema chat_service;

create table if not exists chat_service.user (
    id int generated always as identity primary key,
    "name" text not null,
    "surname" text not null
);

create table if not exists chat_service.message (
    id int generated always as identity primary key,
    "timestamp" timestamp null default now(),
    message text not null
);

create table if not exists chat_service.chat (
    id int generated always as identity primary key
);

create table if not exists chat_service.chat_user (
    user_id int not null,
    chat_id int not null,

    constraint fk_user foreign key (user_id) references chat_service.user (id),
    constraint fk_chat foreign key (chat_id) references chat_service.chat (id),
    constraint pk_chat_user primary key (user_id, chat_id)
);
