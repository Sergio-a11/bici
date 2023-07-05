-- SQL SCRIPT TO COPY AND TEST

CREATE DATABASE IF NOT EXISTS parkinglot;

-- -----------------------------------------------------
-- Table `parkinglot`.`brands`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `parkinglot`.`Brands` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `brand` VARCHAR(50) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table `parkinglot`.`Types`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `parkinglot`.`Types` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(50) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table `parkinglot`.`Roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `parkinglot`.`Roles` (
  `codigo` INT NOT NULL AUTO_INCREMENT,
  `rol` VARCHAR(50) NULL,
  PRIMARY KEY (`codigo`))
ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table `parkinglot`.`questions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `parkinglot`.`Questions` (
  `codigo` INT NOT NULL AUTO_INCREMENT,
  `question` VARCHAR(50) NULL,
  PRIMARY KEY (`codigo`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `parkinglot`.`Estudiantes`
-- ---------------------------------------------------

CREATE TABLE IF NOT EXISTS `parkinglot`.`Users` (
  `codigo` VARCHAR(20) NOT NULL,
  `name` VARCHAR(250) NULL,
  `email` VARCHAR(100) NULL,
  `password` VARCHAR(255) NULL,
  `question` INT NOT NULL,
  `answer` TEXT NULL,
  `Rol_id` INT NOT NULL,
  PRIMARY KEY (`codigo`),
    FOREIGN KEY (`question`)
    REFERENCES `parkinglot`.`questions` (`codigo`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (`Rol_id`)
    REFERENCES `parkinglot`.`Roles` (`codigo`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `parkinglot`.`Bikes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `parkinglot`.`Bikes` (
  `idBike` INT NOT NULL AUTO_INCREMENT,
  `OwnderId` VARCHAR(20) NULL,
  `DateRegistered` VARCHAR(50) NULL,
  `Place` VARCHAR(45) NULL,
  `brand_id` INT NULL,
  `numSerie` VARCHAR(45) NULL,
  `type_id` INT NULL,
  `color` VARCHAR(45) NULL,
  `UserId` VARCHAR(20) NOT NULL,  
  PRIMARY KEY (`idBike`),
    FOREIGN KEY (`UserId`)
    REFERENCES `parkinglot`.`Users` (`codigo`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (`brand_id`)
    REFERENCES `parkinglot`.`brands` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (`type_id`)
    REFERENCES `parkinglot`.`Types` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `parkinglot`.`Slots`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `parkinglot`.`Slots` (
  `idSlot` INT NOT NULL AUTO_INCREMENT,
  `section` VARCHAR(45) NULL,
  `state` TINYINT NULL,
  PRIMARY KEY (`idSlot`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `parkinglot`.`Parkings`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `parkinglot`.`Parkings` (
  `idParking` INT NOT NULL AUTO_INCREMENT,
  `idBike` INT NOT NULL,
  `idSlot` INT NOT NULL,
  PRIMARY KEY (`idParking`),
  CONSTRAINT `fk_BikeParking`
    FOREIGN KEY (`idBike`)
    REFERENCES `parkinglot`.`Bikes` (`idBike`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Parking`
    FOREIGN KEY (`idSlot`)
    REFERENCES `parkinglot`.`Slots` (`idSlot`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

## TRIGGERS Bikes

CREATE TABLE IF NOT EXISTS parkinglot.control_Bikes (
	id INT NOT NULL AUTO_INCREMENT,
  idBike INT NULL,
  OwnderId VARCHAR(20) NULL,
  DateRegistered VARCHAR(50) NULL,
  Place VARCHAR(45) NULL,
  brand_id INT NULL,
  numSerie VARCHAR(45) NULL,
  type_id INT NULL,
  color VARCHAR(45) NULL,
  UserId VARCHAR(20) NOT NULL,
  created_date DATETIME,
  last_modified_date DATETIME,
  status VARCHAR(10),
  primary key (id))
ENGINE = InnoDB;

use parkinglot;

CREATE TRIGGER control_Bikes_insert AFTER INSERT ON Bikes FOR EACH ROW 
INSERT INTO control_Bikes( idBike, OwnderId, DateRegistered, 
Place, brand_id, numSerie, type_id, color, UserId, created_date, last_modified_date, status) 
VALUES( NEW.idBike, NEW.OwnderId, NEW.DateRegistered, NEW.Place, 
NEW.brand_id, NEW.numSerie, NEW.type_id, NEW.color, NEW.UserId, NOW(), NOW(), 'ACTIVE');

CREATE TRIGGER control_Bikes_update BEFORE UPDATE ON Bikes FOR EACH ROW 
UPDATE control_Bikes SET last_modified_date = NOW(), idBike=NEW.idBike, 
OwnderId=NEW.OwnderId, DateRegistered=NEW.DateRegistered,
 Place=NEW.Place, brand_id=NEW.brand_id, numSerie=NEW.numSerie, 
 type_id=NEW.type_id, color=NEW.color, UserId=NEW.UserId WHERE idBike=NEW.idBike;

CREATE TRIGGER control_Bikes_delete BEFORE DELETE ON Bikes FOR EACH ROW 
UPDATE control_Bikes SET last_modified_date = NOW(), status='DELETED' WHERE idBike=OLD.idBike;

#Users TRIGGERS

CREATE TABLE IF NOT EXISTS `parkinglot`.`control_Users` (
  id INT NOT NULL AUTO_INCREMENT,
  `codigo` VARCHAR(20) NOT NULL,
  `name` VARCHAR(250) NULL,
  `email` VARCHAR(100) NULL,
  `password` VARCHAR(255) NULL,
  `question` INT NOT NULL,
  `answer` TEXT NULL,
  `Rol_id` INT NOT NULL,
  created_date DATETIME,
  last_modified_date DATETIME,
  status VARCHAR(10),
  primary key (id))
ENGINE = InnoDB;

CREATE TRIGGER control_Users_insert AFTER INSERT ON Users FOR EACH ROW 
INSERT INTO control_Users (codigo, name, email, password, question, answer, 
Rol_id, created_date, last_modified_date, status) 
VALUES( NEW.codigo, NEW.name, NEW.email, NEW.password, NEW.question, NEW.answer, NEW.Rol_id, NOW(), NOW(), 'ACTIVE');

CREATE TRIGGER control_Users_update BEFORE UPDATE ON Users FOR EACH ROW 
UPDATE control_Users SET last_modified_date = NOW(), codigo=NEW.codigo, 
name=NEW.name, email=NEW.email, password=NEW.password, question=NEW.question, answer=NEW.answer, 
Rol_id=NEW.Rol_id WHERE codigo=NEW.codigo;

CREATE TRIGGER control_Users_delete BEFORE DELETE ON Users FOR EACH ROW 
UPDATE control_Users SET last_modified_date = NOW(), status='DELETED' WHERE codigo=OLD.codigo;

##Parkings
CREATE TABLE IF NOT EXISTS `parkinglot`.`control_Parkings` (
	id INT NOT NULL AUTO_INCREMENT,
    `idSlot` INT NULL,
    `section` VARCHAR(45) NULL,
	`idParking` INT NULL,
	`idBike` INT NULL,
    `OwnderId` VARCHAR(20) NULL,
	`DateRegistered` VARCHAR(50) NULL,
	`Place` VARCHAR(45) NULL,
	`brand_id` INT NULL,
	`numSerie` VARCHAR(45) NULL,
	`type_id` INT NULL,
	`color` VARCHAR(45) NULL,
	`UserId` VARCHAR(20) NOT NULL,
    `name` VARCHAR(250) NULL,
	`email` VARCHAR(100) NULL,
	arrived_time DATETIME, #arrive time
	departure_time DATETIME, #departure time
	status VARCHAR(10),
	primary key (id))
ENGINE = InnoDB;

CREATE TRIGGER control_Parkings_insert AFTER INSERT ON Parkings FOR EACH ROW 
INSERT INTO control_Parkings (idSlot, section, idParking, idBike, OwnderId, DateRegistered, 
Place, brand_id, numSerie, type_id, color, UserId, name, email, arrived_time, departure_time, status) 
VALUES(NEW.idSlot, (select section from Slots where idSlot=NEW.idSlot), NEW.idParking, NEW.idBike, 
(select b.OwnderId from Users as u join Bikes as b where b.UserId=u.codigo AND b.idBike=NEW.idBike), 
(select b.DateRegistered from Users as u join Bikes as b where b.UserId=u.codigo AND b.idBike=NEW.idBike),
(select b.Place from Users as u join Bikes as b where b.UserId=u.codigo AND b.idBike=NEW.idBike),
(select b.brand_id from Users as u join Bikes as b where b.UserId=u.codigo AND b.idBike=NEW.idBike),
(select b.numSerie from Users as u join Bikes as b where b.UserId=u.codigo AND b.idBike=NEW.idBike),
(select b.type_id from Users as u join Bikes as b where b.UserId=u.codigo AND b.idBike=NEW.idBike),
(select b.color from Users as u join Bikes as b where b.UserId=u.codigo AND b.idBike=NEW.idBike),
(select b.UserId from Users as u join Bikes as b where b.UserId=u.codigo AND b.idBike=NEW.idBike),
(select u.name from Users as u join Bikes as b where b.UserId=u.codigo AND b.idBike=NEW.idBike),
(select u.email from Users as u join Bikes as b where b.UserId=u.codigo AND b.idBike=NEW.idBike),
 NOW(), NOW(), 'IN USE');
 
 DELIMITER |
 
 CREATE	TRIGGER control_Parkings_update AFTER UPDATE ON Slots FOR EACH ROW 
 BEGIN
	IF NEW.state = 0 THEN
		UPDATE control_Parkings SET status = "FREE", departure_time = NOW() WHERE idSlot=NEW.idSlot AND section=NEW.section;
	END IF;
END |

 DELIMITER ;
 
 #ELIMINACION brandS
CREATE TRIGGER control_brands_delete BEFORE DELETE ON brands FOR EACH ROW 
UPDATE Bikes SET brand_id=(SELECT id from brands where brand="other") WHERE brand_id=OLD.id;
 
 #ELIMINACION Types
CREATE TRIGGER control_Types_delete BEFORE DELETE ON Types FOR EACH ROW 
UPDATE Bikes SET type_id=(SELECT id from brands where brand="other") WHERE type_id=OLD.id;


INSERT INTO brands (brand) VALUES ("other"),('Specialized'),
  ('Trek'),
  ('Giant'),
  ('Cannondale'),
  ('Scott'),
  ('Cervelo'),
  ('Bianchi'),
  ('Santa Cruz'),
  ('Fuji'),
  ('Pinarello');

INSERT INTO Types (type) VALUES ("other"),('Mountain Bike'),
  ('Road Bike'),
  ('Hybrid Bike'),
  ('City Bike'),
  ('BMX Bike'),
  ('Touring Bike'),
  ('Cruiser Bike'),
  ('Electric Bike'),
  ('Folding Bike'),
  ('Gravel Bike');

INSERT INTO Roles (rol) VALUES ("Admin"),("User"),("SuperAdmin");
INSERT INTO questions (question) VALUES ('What is your favorite color?'),
  ('What city were you born in?'),
  ('What is your mothers maiden name?'),
  ('What was the name of your first pet?'),
  ('What is your favorite movie?'),
  ('What is your favorite book?'),
  ('What was your high school mascot?'),
  ('What is the name of your favorite teacher?'),
  ('What is your favorite food?'),
  ('What is your favorite sport?');
INSERT INTO Users  VALUES 
("20192578005", "Juan Suarez", "juan@email.udistrital.edu.co","password",2,"No",2),
('20172838741', 'Brian', 'brian@gmail.com', 'password', 7, 'Colegio La Gaviota', 1),
('20192578094', 'josue', 'josue@email.com', 'joselito', 1, 'hamburguesa', 3),
('20192578104', 'Miguel', 'miguel@email.com', 'miguelo', 4, 'Cristo', 3),
('20192578014', 'Checho', 'sech@email.com', 'password', 5, 'gato', 3),
('20192578001', 'Dan', 'dan@email.com', 'password', 3, 'NPI', 3);

INSERT INTO Bikes (OwnderId, DateRegistered, Place, brand_id, numSerie, type_id, color, UserId) VALUES 
("12567876", "2019/12/23","Bogota",1,"345678",3,"Azul","20192578005"),
('100795365', '2022/02/13', 'Bogota', 2, '789937', 6, 'rojo', '20192578094'),
("100023567", "2018/12/24","Bucaramanga",3,"123678",8,"Azul","20192578005"),
("100614891", "2019/05/02","Barranquilla",4,"234987",4,"Verde","20192578104"),
("1033876102", "2017/10/15","Pasto",5,"345123",9,"Curuba","20192578001"),
("2001456789", "2020/12/30","Bogota",6,"987456",5,"Morado","20192578014");

INSERT INTO Slots (section, state) VALUES ("2",1),("3",0),("1",0),("2",1),("3",0),("1",1),("2",0),("3",0),("1",0);
INSERT INTO Parkings (idBike, idSlot) VALUES (1,1),(2,4),(3,6);
