DROP DATABASE IF EXISTS BookShop;
CREATE DATABASE IF NOT EXISTS BookShop;
USE BookShop;

DROP TABLE IF EXISTS User;
CREATE TABLE User (
	userId VARCHAR(50) NOT NULL,
	userSecret VARCHAR(50) NOT NULL,
	PRIMARY KEY(userId) );
    

DROP TABLE IF EXISTS Book;
CREATE TABLE Book(
	bookName VARCHAR(50) NOT NULL,
	bookAuthor VARCHAR(50) NOT NULL,
	bookPrice DECIMAL(6,2) NOT NULL,
	PRIMARY KEY(bookName) );

DROP TABLE IF EXISTS Record;
CREATE TABLE Record(
	userId VARCHAR(50) NOT NULL,
    requestType VARCHAR(50) NOT NULL,
    timeSt TIMESTAMP NOT NULL,
	accessStatus VARCHAR(50) NOT NULL,
	PRIMARY KEY(timeSt) );



INSERT INTO Book(bookName, bookAuthor, bookPrice) VALUES("book1", "author1", 10);
INSERT INTO Book(bookName, bookAuthor, bookPrice) VALUES("book2", "author1", 20);
INSERT INTO Book(bookName, bookAuthor, bookPrice) VALUES("book3", "author1", 30);

SELECT * from User;

SELECT * from Book;

select * from Record;
