create table User
(
    id int auto_increment,
    email varchar(20) not null,
    password varchar(20) not null,
    name varchar(10) not null,
    phone varchar(14) not null,
    constraint member_id_uindex
        primary key (id)
);