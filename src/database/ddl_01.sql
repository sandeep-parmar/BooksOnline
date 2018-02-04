create database bookDB;
\
CREATE TABLE `bookdb`.`books` (
  `bookid` VARCHAR(100) NOT NULL,
  `booktitle` VARCHAR(500) NOT NULL,
  `bookauthor` VARCHAR(500) NOT NULL,
  `bookdesc` VARCHAR(2000) NOT NULL,
  `thumbnail` VARCHAR(500) NOT NULL,
  PRIMARY KEY(`bookid`)
);
\
CREATE TABLE `bookdb`.`user` (
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `mobile` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `salt` VARCHAR(45) NOT NULL,
  `uuid` VARCHAR(45) NOT NULL,
  `active` INTEGER UNSIGNED NOT NULL
)
\