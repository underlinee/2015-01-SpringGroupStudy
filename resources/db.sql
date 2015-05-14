DROP SCHEMA IF EXISTS `spring_test` ;
CREATE SCHEMA IF NOT EXISTS `spring_test` ;
DROP TABLE IF EXISTS `spring_test`.`users` ;
CREATE TABLE IF NOT EXISTS `spring_test`.`users` (
  `id` VARCHAR(45),
  `name` VARCHAR(20),
  `password` VARCHAR(45),
  PRIMARY KEY (`ID`)
);
USE springbook;
INSERT into users VALUES('erin314', '이경륜', '1111');