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
