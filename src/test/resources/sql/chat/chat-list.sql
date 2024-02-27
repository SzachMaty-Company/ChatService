insert into chat_service."user" (id, global_user_id, "username") values (11, 'global-id-11', 'tomisław apoloniusz curuś bachleda farel');
insert into chat_service."user" (id, global_user_id, "username") values (12, 'global-id-12', 'yeti');
insert into chat_service."user" (id, global_user_id, "username") values (13, 'global-id-13', 'chłopiec z dolnego pokładu');

insert into chat_service.chat (id) values (11);
insert into chat_service.chat (id) values (12);

insert into chat_service.user_chat (user_id, chat_id) values (11, 11);
insert into chat_service.user_chat (user_id, chat_id) values (12, 11);

insert into chat_service.message (sender_id, chat_id, message) values (11, 11, 'chat 11 message 11');
insert into chat_service.message (sender_id, chat_id, message) values (12, 11, 'chat 11 message 2');
insert into chat_service.message (sender_id, chat_id, message) values (11, 11, 'chat 11 message 3');

insert into chat_service.user_chat (user_id, chat_id) values (11, 12);
insert into chat_service.user_chat (user_id, chat_id) values (13, 12);

insert into chat_service.message (sender_id, chat_id, message) values (12, 12, 'chat 2 message 11');
insert into chat_service.message (sender_id, chat_id, message) values (13, 12, 'chat 2 message 2');
insert into chat_service.message (sender_id, chat_id, message) values (12, 12, 'chat 2 message 3');