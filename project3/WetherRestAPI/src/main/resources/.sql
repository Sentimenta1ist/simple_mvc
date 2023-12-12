create table sensor
(
    id    int generated by default as identity primary key,
    name  varchar(100) not null
);

drop table sensor;

drop table measurement;

create table measurement
(
    id int generated by default as identity primary key,
    sensor_id int references sensor(id),
    value float not null,
    raining bool,
    curr_time timestamp
);

select * from sensor;

select * from measurement;

insert into measurement(sensor_id, value, raining, curr_time)
VALUES (1, 23.4, true, '2020-10-05 14:01:10-08');

insert into measurement(sensor_id, value, raining, curr_time)
VALUES (1, 23.4, false, '2020-10-05 14:01:10-08');

insert into measurement(sensor_id, value, raining, curr_time)
VALUES (1, 23.4, true, '2020-10-12 14:01:10-08');

insert into measurement(sensor_id, value, raining, curr_time)
VALUES (1, 23.4, false, '2020-05-05 14:01:10-08');

insert into measurement(sensor_id, value, raining, curr_time)
VALUES (1, 23.4, true, '2020-11-05 14:01:10-08');

insert into measurement(sensor_id, value, raining, curr_time)
VALUES (1, 23.4, true, '2020-11-20 14:01:10-08');

insert into measurement(sensor_id, value, raining, curr_time)
VALUES (1, 23.4, false, '2020-11-21 14:01:10-08');

delete from measurement where true;

delete from sensor where true;

select
    date_trunc('day', curr_time),
    count(*)
from measurement
where raining = true
group by date_trunc('day', curr_time);