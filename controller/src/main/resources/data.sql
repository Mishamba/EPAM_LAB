insert into gift_certificate (_name, _description, price, duration, create_date, last_update_date) values
('spa', 'using this certificate u can go to spa', 15, 15, '2020-12-12T12:00:00.000', '2020-12-12T12:00:00.000'), -- id 1
('gym', '15 days of free gym', 20, 15, '2020-12-15T13:00:00.000', '2020-12-15T13:00:00.000');  					 -- id 2

insert into tag (_name) values
('chill'), -- id 1
('sport'), -- id 2
('relax'); -- id 3
insert into tag(_name) value ('fun'); -- id 4
insert into tag(_name) value ('trash'); -- id 5

insert into certificate_tags(certificate_id, tag_id) values
(1, 1),
(1, 3),
(2, 2);