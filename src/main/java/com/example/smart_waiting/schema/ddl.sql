create table User
(
    user_id int auto_increment,
    email varchar(20) not null,
    password varchar(20) not null,
    name varchar(10) not null,
    phone varchar(14) not null,
    constraint user_id_uindex
        primary key (user_id)
);

create table Market
(
    id int auto_increment,
    owner int not null,
    name varchar(20) not null,
    registrationNum varchar(10) not null,
    zipCode int not null,
    detail_address varchar(20) not null,
    open_hour int not null,
    close_hour int not null,
    constraint market_id_uindex
        primary key (id)
);

ALTER TABLE `Market`
ADD INDEX `ownerKey_idx` (`owner` ASC) VISIBLE;
;

ALTER TABLE `Market`
ADD CONSTRAINT `ownerKey`
  FOREIGN KEY (`owner`)
  REFERENCES `User` (`user_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;