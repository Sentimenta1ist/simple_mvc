create table item(
    id int primary key generated by default as identity,
    person_id int references person(id) on delete set null,
    name varchar(100) not null
);

select * from person;

select * from item;

insert into item(person_id, name) values (12, 'Aor');
insert into item(person_id, name) values (12, 'Aor2');
insert into item(person_id, name) values (12, 'Aor3');