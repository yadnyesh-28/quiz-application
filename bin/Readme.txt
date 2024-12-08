Quiz Application

Hello InnoByte Services team,
    I hope you are doing well.I developed assigned task named Quiz Application.In that I have made four tables mainly user table for login purpose,
topic table for insert topic, questions table for insert questions according to topic, result table is optional for me to stored result.As i unable to solve 
that result table to store result with their userId and userName.As main purpose of Quiz App is to admin can do crud operation on topic and questions.
And user can take quizzes using options of topic with timing.after selected option on next button clicked it selected option correct or not it also provided.
User password is stored with hashing. Admin has fixed username and password.And user has login credentials that he have to put unique username and password if it's 
exist in database.
 first we have to create table in database and admin has to register username and password as it given below.
admin has=> username ->  admin and password-> root
then admin can add topic and add question which topic available in database. which he can edit or delete.
user has to register first then  he can login for quiz and to select topic and take quiz. after quiz his feedback 
is also provided.
I have  given below database queries to create table with select queries, update and delete.
ThankYou,
Yadnyesh Kolpe.


create database quiz_app;
 use quiz_app;

create table user(
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL
);


insert into user values(default,"Admin","root");
insert into login valaues(default,"admin","root");

create table topic (
    topic_id INT AUTO_INCREMENT PRIMARY KEY,
    topic_name VARCHAR(255) NOT NULL
);

create table questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    topic_id INT,
    question TEXT NOT NULL,
    option1 VARCHAR(255),
    option2 VARCHAR(255),
    option3 VARCHAR(255),
    option4 VARCHAR(255),
    correct_option Int,
    FOREIGN KEY (topic_id) REFERENCES topic(topic_id) ON DELETE CASCADE
);


// second result table;
CREATE TABLE result (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userId INT ,
    username VARCHAR(100),
    correct INT ,
    wrong INT ,
    score INT ,
    total INT 
);


url = "jdbc:mysql://localhost:3306/quiz_app"; 
username = "root";
 password = "root";

insert into questions (topic_id, question, option1, option2, option3, option4, correct_option) values (6,"what is java","it is programming language","it is a concept of oops","it is a library","NOTA",1);
insert into topic (topic_name) values (".Net");
insert into topic (topic_name) values (".Net");
select * from questions;
select * from topic;
select * from user;
select * from result;
update questions set question= "what is React.js?" where id = 4;
select * from questions where topic_id = 6;
update topic set topic_name = "Java" where topic_id = 6;
delete from topic where topic_id = 6;
delete from questions where id = 
select password , user_id from user where username = "admin";
select * from user where username = "admin";
insert into user (username, password) values ("root", "root")  <- it is in hashpassword we must have to put through java swing and javacode;





