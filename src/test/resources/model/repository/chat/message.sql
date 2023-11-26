insert into chat_service.user (id, "name", surname)
values (500, 'bob', 'bob'), (501, 'alice', 'alice');

insert into chat_service.chat (id) values (500);

insert into chat_service.user_chat (user_id, chat_id)
values (500, 500), (501, 500);