-- SQL SCRIPT TO COPY AND TEST

CREATE DATABASE IF NOT EXISTS SDJcomp2;

-- -----------------------------------------------------
-- Table `SDJcomp2`.`Marcas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SDJcomp2`.`Marcas` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `marca` VARCHAR(50) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table `SDJcomp2`.`Tipos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SDJcomp2`.`Tipos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `tipo` VARCHAR(50) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table `SDJcomp2`.`Roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SDJcomp2`.`Roles` (
  `codigo` INT NOT NULL AUTO_INCREMENT,
  `rol` VARCHAR(50) NULL,
  PRIMARY KEY (`codigo`))
ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table `SDJcomp2`.`Preguntas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SDJcomp2`.`Preguntas` (
  `codigo` INT NOT NULL AUTO_INCREMENT,
  `pregunta` VARCHAR(50) NULL,
  PRIMARY KEY (`codigo`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `SDJcomp2`.`Estudiantes`
-- ---------------------------------------------------

CREATE TABLE IF NOT EXISTS `SDJcomp2`.`Usuarios` (
  `codigo` VARCHAR(20) NOT NULL,
  `nombre` VARCHAR(250) NULL,
  `correo` VARCHAR(100) NULL,
  `clave` VARCHAR(255) NULL,
  `Pseguridad` INT NOT NULL,
  `Rseguridad` TEXT NULL,
  `Rol_id` INT NOT NULL,
  PRIMARY KEY (`codigo`),
    FOREIGN KEY (`Pseguridad`)
    REFERENCES `SDJcomp2`.`Preguntas` (`codigo`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (`Rol_id`)
    REFERENCES `SDJcomp2`.`Roles` (`codigo`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `SDJcomp2`.`Bicicletas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SDJcomp2`.`Bicicletas` (
  `idBicicleta` INT NOT NULL AUTO_INCREMENT,
  `cedulaPropietario` VARCHAR(20) NULL,
  `fechaRegistro` VARCHAR(50) NULL,
  `lugarRegistro` VARCHAR(45) NULL,
  `Marca_id` INT NULL,
  `numSerie` VARCHAR(45) NULL,
  `Tipo_id` INT NULL,
  `color` VARCHAR(45) NULL,
  `Estudiante_id` VARCHAR(20) NOT NULL,  
  PRIMARY KEY (`idBicicleta`),
    FOREIGN KEY (`Estudiante_id`)
    REFERENCES `SDJcomp2`.`Usuarios` (`codigo`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (`Marca_id`)
    REFERENCES `SDJcomp2`.`Marcas` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (`Tipo_id`)
    REFERENCES `SDJcomp2`.`Tipos` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `SDJcomp2`.`Cupos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SDJcomp2`.`Cupos` (
  `idCupo` INT NOT NULL AUTO_INCREMENT,
  `seccion` VARCHAR(45) NULL,
  `estado` TINYINT NULL,
  PRIMARY KEY (`idCupo`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `SDJcomp2`.`Parqueaderos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SDJcomp2`.`Parqueaderos` (
  `idParqueadero` INT NOT NULL AUTO_INCREMENT,
  `Bicicleta_idBicicleta` INT NOT NULL,
  `Cupo_idCupo` INT NOT NULL,
  PRIMARY KEY (`idParqueadero`),
  CONSTRAINT `fk_Parqueadero_Bicicleta`
    FOREIGN KEY (`Bicicleta_idBicicleta`)
    REFERENCES `SDJcomp2`.`Bicicletas` (`idBicicleta`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Parqueadero_Cupo1`
    FOREIGN KEY (`Cupo_idCupo`)
    REFERENCES `SDJcomp2`.`Cupos` (`idCupo`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

## TRIGGERS BICICLETAS

CREATE TABLE IF NOT EXISTS SDJcomp2.control_bicicletas (
	id INT NOT NULL AUTO_INCREMENT,
  idBicicleta INT NULL,
  cedulaPropietario VARCHAR(20) NULL,
  fechaRegistro VARCHAR(50) NULL,
  lugarRegistro VARCHAR(45) NULL,
  Marca_id INT NULL,
  numSerie VARCHAR(45) NULL,
  Tipo_id INT NULL,
  color VARCHAR(45) NULL,
  Estudiante_id VARCHAR(20) NOT NULL,
  created_date DATETIME,
  last_modified_date DATETIME,
  status VARCHAR(10),
  primary key (id))
ENGINE = InnoDB;

use sdjcomp2;

CREATE TRIGGER control_bicicletas_insert AFTER INSERT ON bicicletas FOR EACH ROW 
INSERT INTO control_bicicletas( idBicicleta, cedulaPropietario, fechaRegistro, 
lugarRegistro, Marca_id, numSerie, Tipo_id, color, Estudiante_id, created_date, last_modified_date, status) 
VALUES( NEW.idBicicleta, NEW.cedulaPropietario, NEW.fechaRegistro, NEW.lugarRegistro, 
NEW.Marca_id, NEW.numSerie, NEW.Tipo_id, NEW.color, NEW.Estudiante_id, NOW(), NOW(), 'ACTIVA');

CREATE TRIGGER control_bicicletas_update BEFORE UPDATE ON bicicletas FOR EACH ROW 
UPDATE control_bicicletas SET last_modified_date = NOW(), idBicicleta=NEW.idBicicleta, 
cedulaPropietario=NEW.cedulaPropietario, fechaRegistro=NEW.fechaRegistro,
 lugarRegistro=NEW.lugarRegistro, Marca_id=NEW.Marca_id, numSerie=NEW.numSerie, 
 Tipo_id=NEW.Tipo_id, color=NEW.color, Estudiante_id=NEW.Estudiante_id WHERE idBicicleta=NEW.idBicicleta;

CREATE TRIGGER control_bicicletas_delete BEFORE DELETE ON bicicletas FOR EACH ROW 
UPDATE control_bicicletas SET last_modified_date = NOW(), status='ELIMINADA' WHERE idBicicleta=OLD.idBicicleta;

#USUARIOS TRIGGERS

CREATE TABLE IF NOT EXISTS `SDJcomp2`.`control_usuarios` (
  id INT NOT NULL AUTO_INCREMENT,
  `codigo` VARCHAR(20) NOT NULL,
  `nombre` VARCHAR(250) NULL,
  `correo` VARCHAR(100) NULL,
  `clave` VARCHAR(255) NULL,
  `Pseguridad` INT NOT NULL,
  `Rseguridad` TEXT NULL,
  `Rol_id` INT NOT NULL,
  created_date DATETIME,
  last_modified_date DATETIME,
  status VARCHAR(10),
  primary key (id))
ENGINE = InnoDB;

CREATE TRIGGER control_usuarios_insert AFTER INSERT ON usuarios FOR EACH ROW 
INSERT INTO control_usuarios (codigo, nombre, correo, clave, Pseguridad, Rseguridad, 
Rol_id, created_date, last_modified_date, status) 
VALUES( NEW.codigo, NEW.nombre, NEW.correo, NEW.clave, NEW.Pseguridad, NEW.Rseguridad, NEW.Rol_id, NOW(), NOW(), 'ACTIVO');

CREATE TRIGGER control_usuarios_update BEFORE UPDATE ON usuarios FOR EACH ROW 
UPDATE control_usuarios SET last_modified_date = NOW(), codigo=NEW.codigo, 
nombre=NEW.nombre, correo=NEW.correo, clave=NEW.clave, Pseguridad=NEW.Pseguridad, Rseguridad=NEW.Rseguridad, 
Rol_id=NEW.Rol_id WHERE codigo=NEW.codigo;

CREATE TRIGGER control_usuarios_delete BEFORE DELETE ON usuarios FOR EACH ROW 
UPDATE control_usuarios SET last_modified_date = NOW(), status='ELIMINADO' WHERE codigo=OLD.codigo;

##PARQUEADEROS
CREATE TABLE IF NOT EXISTS `SDJcomp2`.`control_parqueaderos` (
	id INT NOT NULL AUTO_INCREMENT,
    `idCupo` INT NULL,
    `seccion` VARCHAR(45) NULL,
	`idParqueadero` INT NULL,
	`Bicicleta_idBicicleta` INT NULL,
    `cedulaPropietario` VARCHAR(20) NULL,
	`fechaRegistro` VARCHAR(50) NULL,
	`lugarRegistro` VARCHAR(45) NULL,
	`Marca_id` INT NULL,
	`numSerie` VARCHAR(45) NULL,
	`Tipo_id` INT NULL,
	`color` VARCHAR(45) NULL,
	`Estudiante_id` VARCHAR(20) NOT NULL,
    `nombre` VARCHAR(250) NULL,
	`correo` VARCHAR(100) NULL,
	arrived_time DATETIME, #arrive time
	departure_time DATETIME, #departure time
	status VARCHAR(10),
	primary key (id))
ENGINE = InnoDB;

CREATE TRIGGER control_parqueaderos_insert AFTER INSERT ON parqueaderos FOR EACH ROW 
INSERT INTO control_parqueaderos (idCupo, seccion, idParqueadero, Bicicleta_idBicicleta, cedulaPropietario, fechaRegistro, 
lugarRegistro, Marca_id, numSerie, Tipo_id, color, Estudiante_id, nombre, correo, arrived_time, departure_time, status) 
VALUES(NEW.Cupo_idCupo, (select seccion from cupos where idCupo=NEW.Cupo_idCupo), NEW.idParqueadero, NEW.Bicicleta_idBicicleta, 
(select b.cedulaPropietario from usuarios as u join bicicletas as b where b.Estudiante_id=u.codigo AND b.idBicicleta=NEW.Bicicleta_idBicicleta), 
(select b.fechaRegistro from usuarios as u join bicicletas as b where b.Estudiante_id=u.codigo AND b.idBicicleta=NEW.Bicicleta_idBicicleta),
(select b.lugarRegistro from usuarios as u join bicicletas as b where b.Estudiante_id=u.codigo AND b.idBicicleta=NEW.Bicicleta_idBicicleta),
(select b.Marca_id from usuarios as u join bicicletas as b where b.Estudiante_id=u.codigo AND b.idBicicleta=NEW.Bicicleta_idBicicleta),
(select b.numSerie from usuarios as u join bicicletas as b where b.Estudiante_id=u.codigo AND b.idBicicleta=NEW.Bicicleta_idBicicleta),
(select b.Tipo_id from usuarios as u join bicicletas as b where b.Estudiante_id=u.codigo AND b.idBicicleta=NEW.Bicicleta_idBicicleta),
(select b.color from usuarios as u join bicicletas as b where b.Estudiante_id=u.codigo AND b.idBicicleta=NEW.Bicicleta_idBicicleta),
(select b.Estudiante_id from usuarios as u join bicicletas as b where b.Estudiante_id=u.codigo AND b.idBicicleta=NEW.Bicicleta_idBicicleta),
(select u.nombre from usuarios as u join bicicletas as b where b.Estudiante_id=u.codigo AND b.idBicicleta=NEW.Bicicleta_idBicicleta),
(select u.correo from usuarios as u join bicicletas as b where b.Estudiante_id=u.codigo AND b.idBicicleta=NEW.Bicicleta_idBicicleta),
 NOW(), NOW(), 'EN USO');
 
 DELIMITER |
 
 CREATE	TRIGGER control_parqueaderos_update AFTER UPDATE ON cupos FOR EACH ROW 
 BEGIN
	IF NEW.estado = 0 THEN
		UPDATE control_parqueaderos SET status = "SIN USO", departure_time = NOW() WHERE idCupo=NEW.idCupo AND seccion=NEW.seccion;
	END IF;
END |

 DELIMITER ;
 
 #ELIMINACION MARCAS
CREATE TRIGGER control_marcas_delete BEFORE DELETE ON marcas FOR EACH ROW 
UPDATE bicicletas SET Marca_id=(SELECT id from marcas where marca="otro") WHERE Marca_id=OLD.id;
 
 #ELIMINACION TIPOS
CREATE TRIGGER control_tipos_delete BEFORE DELETE ON tipos FOR EACH ROW 
UPDATE bicicletas SET Tipo_id=(SELECT id from marcas where marca="otro") WHERE Tipo_id=OLD.id;


INSERT INTO Marcas (marca) VALUES ("otro"),("SHIMANO"),("GW"),("TITAN"),("JUPITER"),("HELICOPTER"),("CAMARON"),("TALIYA"),("LISCRAM");
INSERT INTO Tipos (tipo) VALUES ("otro"),("BMX"),("Ruta"),("Playa"),("Fija"),("Tambel"),("Monociclo"),("Triciclo"),("Patinete electrico");
INSERT INTO Roles (rol) VALUES ("Admin"),("User"),("SuperAdmin");
INSERT INTO Preguntas (pregunta) VALUES ("¿Comida favorita?"),("¿Julian es real?"),("¿Y ahora que?"),("¿Personaje Favorito de LoL?"),("¿Perro o gato?"),("¿Clima Frio o Caliente?"),("Lengueje de programación?"),("¿Insulto que no usas?");
INSERT INTO Usuarios  VALUES 
("20192578005", "Juan Suarez", "juan@correo.udistrital.edu.co","clave",2,"No",2),
('20172838741', 'Brian', 'brian@gmail.com', 'clave', 7, 'Colegio La Gaviota', 1),
('20192578094', 'josue', 'josue@correo.com', 'joselito', 1, 'hamburguesa', 3),
('20192578104', 'Miguel', 'miguel@correo.com', 'miguelo', 4, 'Cristo', 2),
('20192578014', 'Checho', 'sech@correo.com', 'clave', 5, 'gato', 2),
('20192578001', 'Dan', 'dan@correo.com', 'clave', 3, 'NPI', 1);

INSERT INTO Bicicletas (cedulaPropietario, fechaRegistro, lugarRegistro, Marca_id, numSerie, Tipo_id, color, Estudiante_id) VALUES 
("12567876", "2019/12/23","Bogota",1,"345678",3,"Azul","20192578005"),
('100795365', '2022/02/13', 'Bogota', 2, '789937', 6, 'rojo', '20192578094'),
("100023567", "2018/12/24","Bucaramanga",3,"123678",8,"Azul","20192578005"),
("100614891", "2019/05/02","Barranquilla",4,"234987",4,"Verde","20192578104"),
("1033876102", "2017/10/15","Pasto",5,"345123",9,"Curuba","20192578001"),
("2001456789", "2020/12/30","Bogota",6,"987456",5,"Morado","20192578014");

INSERT INTO Cupos (seccion, estado) VALUES ("2",1),("3",0),("1",0),("2",1),("3",0),("1",1),("2",0),("3",0),("1",0);
INSERT INTO Parqueaderos (Bicicleta_idBicicleta, Cupo_idCupo) VALUES (1,1),(2,4),(3,6);
