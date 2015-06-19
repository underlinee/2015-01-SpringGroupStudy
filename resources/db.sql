DROP SCHEMA IF EXISTS `spring_test` ;
CREATE SCHEMA IF NOT EXISTS `spring_test` ;
DROP TABLE IF EXISTS `spring_test`.`users` ;
CREATE TABLE IF NOT EXISTS `spring_test`.`users` (
  id varchar(10) primary key,	
  name varchar(20) not null,
  password varchar(10) not null,
  level tinyint not null,
  login int not null,
  recommend int not null
);

USE spring_test;
INSERT into users VALUES('erin314', '이경륜', '1111', 1, 40, 50);
INSERT into users VALUES('erin315', '이경륜', '1111', 1, 20, 20);