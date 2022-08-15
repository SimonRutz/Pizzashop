drop table if exists pizza_ordering;
drop table if exists pizza;
drop table if exists orders;

create table pizza (
    id int unsigned unique auto_increment,
    name varchar(30) not null,
    price double not null,
    primary key (id)
);


create table orders (
    id int unsigned unique auto_increment,
    orderDateTime datetime not null,
    phoneNumber varchar(30) not null,
    address varchar(50) not null,
    primary key (id)
);

create table pizza_ordering (
    amount int unsigned not null,
    pizza_ID int unsigned not null,
    orders_ID int unsigned default null,
    foreign key (pizza_id) references pizza(id),
    foreign key (orders_id) references orders(id)
);

insert into pizza (name, price) values ('Margherita', '15.99');
insert into pizza (name, price) values ('Prosciutto', '17.99');
insert into pizza (name, price) values ('Salami', '17.99');
insert into pizza (name, price) values ('Diavola', '18.99');
insert into pizza (name, price) values ('Calzone', '19.99');