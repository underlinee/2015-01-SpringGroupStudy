DROP SCHEMA IF EXISTS `springbook` ;
CREATE SCHEMA IF NOT EXISTS `springbook` ;
DROP TABLE IF EXISTS `springbook`.`users` ;
CREATE TABLE IF NOT EXISTS `springbook`.`users` (
  `id` VARCHAR(45),
  `name` VARCHAR(20),
  `password` VARCHAR(45),
  PRIMARY KEY (`ID`)
);
USE springbook;
INSERT into USER VALUES('erin314', '이경륜', '1111');