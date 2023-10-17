CREATE DATABASE IF NOT EXISTS appDB;
CREATE USER IF NOT EXISTS 'user'@'%' IDENTIFIED BY 'password';
GRANT SELECT,UPDATE,INSERT ON appDB.* TO 'user'@'%';
FLUSH PRIVILEGES;

USE appDB;

CREATE TABLE IF NOT EXISTS students (
  ID INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(20) NOT NULL,
  surname VARCHAR(40) NOT NULL,
  age INT NOT NULL,
  grade INT NOT NULL,
  PRIMARY KEY (ID)
);

INSERT INTO students (name, surname, age, grade) VALUES
    ("Даниил", "Чибиток", 20, 3),
    ("Максим", "Жаворонков", 20, 3),
    ("Сергей", "Иващенко", 20, 3),
    ("Екатерина", "Мамонова", 20, 3),
    ("Лев", "Шилов", 20, 3);