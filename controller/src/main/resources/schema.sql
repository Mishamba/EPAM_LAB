create table gift_certificate (
	id int not null unique auto_increment,
    _name varchar(30) not null,
    _description varchar(255) not null,
    price int not null,
    duration int not null,
    create_date varchar(23) not null,
    last_update_date varchar(23) not null,
    primary key (id)
);

create table tag (
	id int not null unique auto_increment,
    _name varchar(30) not null,
    primary key (id)
);

create table certificate_tags (
	certificate_id int not null,
    tag_id int not null,
    foreign key (certificate_id) references gift_certificate(id),
    foreign key (tag_id) references tag(id)
);