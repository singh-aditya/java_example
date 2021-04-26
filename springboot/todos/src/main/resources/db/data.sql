insert into authorities (name) values ('READ');
insert into authorities (name) values ('WRITE');
insert into authorities (name) values ('DELETE');
insert into roles (name) values ('ROLE_SYSADMIN');
insert into roles (name) values ('ROLE_ADMIN');
insert into roles (name) values ('ROLE_USER');
insert into roles_authorities (roles_id, authorities_id) values (1, 1);
insert into roles_authorities (roles_id, authorities_id) values (1, 2);
insert into roles_authorities (roles_id, authorities_id) values (1, 3);
insert into roles_authorities (roles_id, authorities_id) values (2, 1);
insert into roles_authorities (roles_id, authorities_id) values (2, 2);
insert into roles_authorities (roles_id, authorities_id) values (2, 3);
insert into roles_authorities (roles_id, authorities_id) values (3, 1);
insert into roles_authorities (roles_id, authorities_id) values (3, 2);

-- Insert admin with password admin123
insert into users (email, encrypted_password, first_name, last_name, user_id, roles_id)
    values('admin@company.com', '$2a$10$du2nmK2KSij1fo5drDVsxO5HJ04k0mVC9.dqhO84x5YiQUkepny5O', 'admin', 'admin', 'admin_123', 2);
-- Insert user with password user123
insert into users (email, encrypted_password, first_name, last_name, user_id, roles_id)
    values('user1@company.com', '$2a$10$PKuigufeT.mDX8BsCjhx/.f5plwHDZwHRKjThvdYmgTnWsPT.22A2', 'user1', 'user', 'user_1', 3);

insert into todos (description, is_done, target_date, users_id) values ('todo', false, '2022-04-22T15:00:00+05:30', 2);