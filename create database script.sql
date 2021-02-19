create database if not exists module2;

use module2;

drop table if exists certificate_tags;
drop table if exists certificate_orders;
drop table if exists gift_certificate;
drop table if exists tag;
drop table if exists orders;
drop table if exists users;

create table gift_certificate (
	id int not null unique auto_increment,
    certificate_name varchar(30) not null,
    certificate_description varchar(255) not null,
    price int not null,
    duration int not null,
    create_date varchar(23) not null,
    last_update_date varchar(23) not null,
    primary key (id)
);

create table tag (
	id int not null unique auto_increment,
    tag_name varchar(30) not null unique,
    primary key (id)
);

create table certificate_tags (
	certificate_id int not null,
    tag_id int not null,
    foreign key (certificate_id) references gift_certificate(id),
    foreign key (tag_id) references tag(id)
);

create table users (
	id int not null unique auto_increment,
    email varchar(30) not null unique,
    `password` varchar(255) not null,
    first_name varchar(50) not null,
    last_name varchar(100) not null,
    role varchar(20) not null,
    primary key(id)
);

create table orders (
	id int not null unique auto_increment,
    users_id int not null,
    order_date varchar(23) not null,
    cost int not null,
    primary key(id),
    foreign key(users_id) references users(id)
);

create table certificate_orders (
	order_id int not null,
    certificate_id int not null,
    foreign key(order_id) references orders(id),
    foreign key(certificate_id) references gift_certificate(id)
);

insert into gift_certificate (certificate_name, certificate_description, price, duration, create_date, last_update_date) values 
('spa', 'using this certificate u can go to spa', 15, 15, '2020-12-12T12:00:00.000', '2020-12-12T12:00:00.000'),  -- 1
('gym', '15 days of free gym', 20, 15, '2020-12-15T13:00:00.000', '2020-12-15T13:00:00.000'), -- 2
('airplane', 'just fly', 50, 30, '2020-12-15T13:00:00.000', '2020-12-15T13:00:00.000'), -- 3
('ocean', 'swim with shark', 20, 15, '2020-12-15T13:00:00.000', '2020-12-15T13:00:00.000'), -- 4 
('some certificate', 'do what u want', 10000, 100000, '2020-12-15T13:00:00.000', '2020-12-15T13:00:00.000'), -- 5
('read', 'visit library', 0, 1000, '2020-12-15T13:00:00.000', '2020-12-15T13:00:00.000'), -- 6 
('write', 'try to write with your hand', 30, 30, '2020-12-15T13:00:00.000', '2020-12-15T13:00:00.000'), -- 7
('china', 'visit comunists', 40, 4000, '2020-12-15T13:00:00.000', '2020-12-15T13:00:00.000'), -- 8
('tea', 'go and drink tea', 20, 20, '2020-12-15T13:00:00.000', '2020-12-15T13:00:00.000'), -- 9
('phone', 'talk to your friends', 40, 20, '2020-12-15T13:00:00.000', '2020-12-15T13:00:00.000'), -- 10
('drive', 'drive a car', 50, 10, '2020-12-15T13:00:00.000', '2020-12-15T13:00:00.000'); -- 11

insert into tag (tag_name) values
('chill'), -- 1
('sport'), -- 2
('relax'); -- 3
insert into tag(tag_name) value ('fun');   -- 4
insert into tag(tag_name) value ('trash'); -- 5
insert into tag(tag_name) values
('danger'), -- 6
('mind'), -- 7
('crazy'); -- 8 

insert into certificate_tags(certificate_id, tag_id) values
(9, 1),
(9, 3),
(10, 4),
(11, 2),
(11, 6),
(11, 4),
(1, 1),
(1, 3), 
(2, 2),
(2, 4),
(1, 5),
(3, 5),
(3, 8),
(4, 2),
(4, 5),
(4, 6),
(5, 7),
(6, 1),
(6, 3),
(7, 4),
(8, 7),
(8, 6);

-- password = `email` without @email.com
insert into users(email, `password`, first_name, last_name, role) values 
('mishamba@email.com', '$2y$12$r/M8JBQhR9mZKsjgv.5NTuGx7igWTum7.6GHMr1ecNNAU67l0kj3q', 'mihail', 'nenahov', 'USER'), -- 1
('user@email.com', '$2y$12$s8skGtNCjV1W2JQdyAnBcu4OAFZFnlo6He3ux9TCfL2UZxUNoiqA.', 'user', 'userov', 'USER'), -- 2
('smth@email.com', '$2y$12$AeQ5HzVR.H1uEtZwaoxbfOpEKHP5a5h.rSok6UQustE7sIWTtYTby', 'some', 'user', 'USER'), -- 3
('igor@email.com', '$2y$12$//rbn7wVZRp5WFCCmHzWMeZrwgzD.ARyuZzWn.DxwFlztp0aPT.Za', 'igor', 'blinov', 'ADMIN'), -- 4 
('slava@email.com', '$2y$12$yo8NVZCH0StKQNxelBjfsOU9.BaX3be07UFnv56DVImI3H0CC8oN2', 'slava', 'marlov', 'USER'), -- 5
('egor@email.com', '$2y$12$XoeON40ZpLQ3r3baicj/3epVR5NrB8twbQ33XMLp4w51GblCt47dq', 'egor', 'zhukov', 'USER'); -- 6

insert into orders(users_id, order_date, cost) values
(1, '2020-12-15T13:00:00.000', 15), -- 1
(2, '2020-12-16T13:00:00.000', 50), -- 2
(3, '2020-12-16T13:00:00.000', 10), -- 3
(1, '2020-12-16T13:00:00.000', 15), -- 4
(4, '2020-12-16T13:00:00.000', 30), -- 5
(4, '2020-12-16T13:00:00.000', 500), -- 6 
(5, '2020-12-16T13:00:00.000', 200), -- 7
(6, '2020-12-16T13:00:00.000', 100), -- 8
(5, '2020-12-16T13:00:00.000', 40); -- 9

insert into certificate_orders(order_id, certificate_id) values
(1, 1),
(2, 2),
(2, 2),
(2, 2),
(2, 1),
(3, 2),
(4, 2),
(5, 5), 
(6, 3),
(7, 8),
(7, 4),
(7, 4),
(8, 1),
(9, 3),
(9, 9),
(8, 10),
(7, 11);