insert into chat_service."user" (id, global_user_id, "username") values (1, 'global-id-1', 'tomisław apoloniusz curuś bachleda farel');
insert into chat_service."user" (id, global_user_id, "username") values (2, 'global-id-2', 'yeti');
insert into chat_service."user" (id, global_user_id, "username") values (3, 'global-id-3', 'chłopiec z dolnego pokładu');

insert into chat_service.chat (id) values (1);

insert into chat_service.user_chat (user_id, chat_id) values (1, 1);
insert into chat_service.user_chat (user_id, chat_id) values (2, 1);