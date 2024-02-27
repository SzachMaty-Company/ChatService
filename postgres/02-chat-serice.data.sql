\c dev;

insert into chat_service."user" (id, global_user_id, "username") values (1, 'global-id-1', 'tomisław apoloniusz curuś bachleda farel');
insert into chat_service."user" (id, global_user_id, "username") values (2, 'global-id-2', 'yeti');
insert into chat_service."user" (id, global_user_id, "username") values (3, 'global-id-3', 'chłopiec z dolnego pokładu');

insert into chat_service.chat (id) values (1);
insert into chat_service.chat (id) values (2);

insert into chat_service.user_chat (user_id, chat_id) values (1, 1);
insert into chat_service.user_chat (user_id, chat_id) values (2, 1);

insert into chat_service.message (sender_id, chat_id, message) values (1, 1, 'chat 1 message 1');
insert into chat_service.message (sender_id, chat_id, message) values (2, 1, 'chat 1 message 2');
insert into chat_service.message (sender_id, chat_id, message) values (1, 1, 'chat 1 message 3');

insert into chat_service.user_chat (user_id, chat_id) values (2, 2);
insert into chat_service.user_chat (user_id, chat_id) values (3, 2);

insert into chat_service.message (sender_id, chat_id, message) values (2, 2, 'chat 2 message 1');
insert into chat_service.message (sender_id, chat_id, message) values (3, 2, 'chat 2 message 2');
insert into chat_service.message (sender_id, chat_id, message) values (2, 2, 'chat 2 message 3');

alter table chat_service."user" alter column id restart with 2000;
