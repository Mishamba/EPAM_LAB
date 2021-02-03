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
    user_name varchar(30) not null,
    primary key(id)
);

create table orders (
	id int not null unique auto_increment,
    users_id int not null,
    order_date varchar(23) not null,
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
('spa', 'using this certificate u can go to spa', 15, 15, '2020-12-12T12:00:00.000', '2020-12-12T12:00:00.000'), -- id 1
('gym', '15 days of free gym', 20, 15, '2020-12-15T13:00:00.000', '2020-12-15T13:00:00.000');  					 -- id 2

insert into tag (tag_name) values
('chill'), -- id 1
('sport'), -- id 2
('relax'); -- id 3
insert into tag(tag_name) value ('fun'); -- id 4
insert into tag(tag_name) value ('trash'); -- id 5

insert into certificate_tags(certificate_id, tag_id) values
(1, 1),
(1, 3), 
(2, 2);