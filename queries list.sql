use module2 ;

select * from gift_certificate;

select SUM(cost) FROM orders WHERE user_id = 1;

select * from orders where users_id = 4;
select * from certificate_orders;

select * from users;

select tag_name, count(tag_name) as timesUsed from orders join (
select users_id, sum(cost) as sum from orders group by users_id order by sum desc limit 1) as id_cost
on orders.users_id = id_cost.users_id
join certificate_orders as c_o
on c_o.order_id = orders.id
join certificate_tags as c_t
on c_t.certificate_id = c_o.certificate_id
join tag 
on tag.id = c_t.tag_id
group by tag_name
order by timesUsed DESC;

explain select * from users;

select users_id, sum(cost) as sum from orders group by users_id order by sum desc;