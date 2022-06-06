create table person(
    id int generated by default as identity primary key,
    name varchar(100) not null,
    age int,
    email varchar(100)
);

insert into person(name, age, email) values ('tom1', 25, 'tom1@gmail.com');
insert into person(name, age, email) values ('tom2', 26, 'tom2@gmail.com');
insert into person(name, age, email) values ('tom3', 27, 'tom3@gmail.com');

select * from person;


alter table person add column created_at timestamp,
    add column updated_ad timestamp,
    add column created_who varchar;