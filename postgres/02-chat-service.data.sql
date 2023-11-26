insert into chat_service."user" (id, "name", surname)
values (500, 'bob', 'bob'),
       (501, 'alice', 'alice'),
       (502, 'aaa', 'aaa'),
       (503, 'bbb', 'bbb'),
       (504, 'ccc', 'ccc');

insert into chat_service.chat (id)
values (1000),
       (1001),
       (1002);

insert into chat_service.user_chat (user_id, chat_id)
values (500, 1000), -- bob with alice
       (501, 1000), -- bob with alice
       (502, 1001), -- aaa with bbb
       (503, 1001), -- aaa with bbb
       (504, 1002), -- ccc with aaa
       (502, 1002); -- ccc with aaa

insert into chat_service.message (sender_id, chat_id, message)
values (500, 1000, 'hi, alice'),
       (501, 1000, 'hi, bob'),
       (502, 1001, 'hi bbb'),
       (503, 1001, 'hi aaa'),
       (504, 1002, 'hi aaa'),
       (502, 1002, 'hi ccc');

alter table chat_service."user" alter column id restart with 2000;
alter table chat_service.chat alter column id restart with 2000;
