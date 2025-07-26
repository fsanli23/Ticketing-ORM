-- insert into roles(description) values('Admin');
-- insert into roles(description) values('Manager');
-- insert into roles(description) values('Employee');


insert into roles(insert_date_time, insert_user_id, is_delete, last_update_date_time, last_update_user_id, description)
values (now(), 1, false, now(), 1, 'Admin'),
       (now(), 1, false, now(), 1, 'Manager'),
       (now(), 1, false, now(), 1, 'Employee');

insert into users(insert_date_time, insert_user_id, is_delete, last_update_date_time, last_update_user_id, enabled,
                  first_name, last_name, user_name, role_id,gender)
values (now(), 1, false, now(), 1, true, 'admin1' , 'admin', 'admin5@gmail.com', 1,'MALE');





