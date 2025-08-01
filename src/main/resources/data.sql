-- insert into roles(description) values('Admin');
-- insert into roles(description) values('Manager');
-- insert into roles(description) values('Employee');


insert into roles(insert_date_time, insert_user_id, is_delete, last_update_date_time, last_update_user_id, description)
values (now(), 1, false, now(), 1, 'Admin'),
       (now(), 1, false, now(), 1, 'Manager'),
       (now(), 1, false, now(), 1, 'Employee');

insert into users(insert_date_time, insert_user_id, is_delete, last_update_date_time, last_update_user_id, enabled,
                  first_name, last_name, user_name, role_id,gender,pass_word)
values (now(), 1, false, now(), 1, true, 'admin1' , 'Admin', 'fatih1@gmail.com', 1,'MALE','$2a$10$9IauBe1BwjS2DR30CLXZv.e.uxGa7mI3QeT5/sorKz8GQdVzfjTya');
insert into users(insert_date_time, insert_user_id, is_delete, last_update_date_time, last_update_user_id, enabled,
                  first_name, last_name, user_name, role_id,gender,pass_word)
values (now(), 1, false, now(), 1, true, 'admin1' , 'Admin', 'fatih2@gmail.com', 2,'MALE','$2a$10$9IauBe1BwjS2DR30CLXZv.e.uxGa7mI3QeT5/sorKz8GQdVzfjTya');





