
create table person
(
    id   int primary key generated by default as identity,
    name varchar(100) not null,
    age  int
);

create table item
(
    id   int primary key generated by default as identity,
    name varchar(100) not null,
    person_id int references person (id) on delete cascade
);

select * from person;

select * from item;

insert into person(name, age) VALUES ('Tom1', 30);
insert into person(name, age) VALUES ('Tom2', 40);
insert into person(name, age) VALUES ('Tom3', 50);

insert into item(name, person_id) VALUES ('Item1', 1);
insert into item(name, person_id) VALUES ('Item2', 1);
insert into item(name, person_id) VALUES ('Item3', 2);
