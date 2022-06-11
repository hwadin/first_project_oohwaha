-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema Project_01
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Project_01
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Project_01` DEFAULT CHARACTER SET utf8 ;
USE `Project_01` ;

-- -----------------------------------------------------
-- Table `Project_01`.`member`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Project_01`.`member` ;

CREATE TABLE IF NOT EXISTS `Project_01`.`member` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `id` VARCHAR(20) NOT NULL,
  `pw` VARCHAR(45) NOT NULL,
  `name` VARCHAR(15) NOT NULL,
  `age` INT NULL,
  `addr` VARCHAR(10) NULL,
  `is_owner` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`no`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Project_01`.`cafe`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Project_01`.`cafe` ;

CREATE TABLE IF NOT EXISTS `Project_01`.`cafe` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `addr` VARCHAR(45) NOT NULL,
  `addr_detail` VARCHAR(45) NOT NULL,
  `owner` INT NOT NULL,
  PRIMARY KEY (`no`),
  INDEX `fk_cafe_member1_idx` (`owner` ASC) VISIBLE,
  CONSTRAINT `fk_cafe_member1`
    FOREIGN KEY (`owner`)
    REFERENCES `Project_01`.`member` (`no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Project_01`.`menu`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Project_01`.`menu` ;

CREATE TABLE IF NOT EXISTS `Project_01`.`menu` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `price` INT NOT NULL,
  `discount` INT NOT NULL,
  `cafe` INT NOT NULL,
  PRIMARY KEY (`no`),
  INDEX `fk_menu_cafe1_idx` (`cafe` ASC) VISIBLE,
  CONSTRAINT `fk_menu_cafe1`
    FOREIGN KEY (`cafe`)
    REFERENCES `Project_01`.`cafe` (`no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Project_01`.`reservation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Project_01`.`reservation` ;

CREATE TABLE IF NOT EXISTS `Project_01`.`reservation` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `cafe` INT NOT NULL,
  `member` INT NOT NULL,
  `time` DATETIME NOT NULL,
  `is_dutch` TINYINT NULL DEFAULT 0,
  `is_apply` TINYINT NULL,
  PRIMARY KEY (`no`),
  INDEX `fk_reservation_cafe1_idx` (`cafe` ASC) VISIBLE,
  INDEX `fk_reservation_member1_idx` (`member` ASC) VISIBLE,
  CONSTRAINT `fk_reservation_cafe1`
    FOREIGN KEY (`cafe`)
    REFERENCES `Project_01`.`cafe` (`no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_reservation_member1`
    FOREIGN KEY (`member`)
    REFERENCES `Project_01`.`member` (`no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Project_01`.`reservation_menu`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Project_01`.`reservation_menu` ;

CREATE TABLE IF NOT EXISTS `Project_01`.`reservation_menu` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `reservation` INT NOT NULL,
  `menu` INT NOT NULL,
  `amount` INT NOT NULL,
  PRIMARY KEY (`no`),
  INDEX `fk_reservation_menu_reservation1_idx` (`reservation` ASC) VISIBLE,
  INDEX `fk_reservation_menu_menu1_idx` (`menu` ASC) VISIBLE,
  CONSTRAINT `fk_reservation_menu_reservation1`
    FOREIGN KEY (`reservation`)
    REFERENCES `Project_01`.`reservation` (`no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_reservation_menu_menu1`
    FOREIGN KEY (`menu`)
    REFERENCES `Project_01`.`menu` (`no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Project_01`.`member_schedule`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Project_01`.`member_schedule` ;

CREATE TABLE IF NOT EXISTS `Project_01`.`member_schedule` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `member` INT NOT NULL,
  `start_time` DATETIME NOT NULL,
  `end_time` DATETIME NOT NULL,
  `title` VARCHAR(45) NULL,
  `detail` VARCHAR(45) NULL,
  PRIMARY KEY (`no`),
  INDEX `fk_member_schedule_member1_idx` (`member` ASC) VISIBLE,
  CONSTRAINT `fk_member_schedule_member1`
    FOREIGN KEY (`member`)
    REFERENCES `Project_01`.`member` (`no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Project_01`.`cafe_schedule`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Project_01`.`cafe_schedule` ;

CREATE TABLE IF NOT EXISTS `Project_01`.`cafe_schedule` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `cafe` INT NOT NULL,
  `start_time` DATETIME NOT NULL,
  `end_time` DATETIME NOT NULL,
  `dayofweek` INT NULL,
  PRIMARY KEY (`no`),
  INDEX `fk_cafe_schedule_cafe1_idx` (`cafe` ASC) VISIBLE,
  CONSTRAINT `fk_cafe_schedule_cafe1`
    FOREIGN KEY (`cafe`)
    REFERENCES `Project_01`.`cafe` (`no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Project_01`.`reservation_member`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Project_01`.`reservation_member` ;

CREATE TABLE IF NOT EXISTS `Project_01`.`reservation_member` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `reservation` INT NOT NULL,
  `participant` INT NOT NULL,
  PRIMARY KEY (`no`),
  INDEX `fk_reservation_member_reservation1_idx` (`reservation` ASC) VISIBLE,
  INDEX `fk_reservation_member_member1_idx` (`participant` ASC) VISIBLE,
  CONSTRAINT `fk_reservation_member_reservation1`
    FOREIGN KEY (`reservation`)
    REFERENCES `Project_01`.`reservation` (`no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_reservation_member_member1`
    FOREIGN KEY (`participant`)
    REFERENCES `Project_01`.`member` (`no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Project_01`.`frndList`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Project_01`.`frndList` ;

CREATE TABLE IF NOT EXISTS `Project_01`.`frndList` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `member` INT NOT NULL,
  `friend` INT NOT NULL,
  `is_invited` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`no`),
  INDEX `fk_frndList_member1_idx` (`member` ASC) VISIBLE,
  INDEX `fk_frndList_member2_idx` (`friend` ASC) VISIBLE,
  CONSTRAINT `fk_frndList_member1`
    FOREIGN KEY (`member`)
    REFERENCES `Project_01`.`member` (`no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_frndList_member2`
    FOREIGN KEY (`friend`)
    REFERENCES `Project_01`.`member` (`no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Project_01`.`inviteList`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Project_01`.`inviteList` ;

CREATE TABLE IF NOT EXISTS `Project_01`.`inviteList` (
  `no` INT NOT NULL AUTO_INCREMENT,
  `code` INT NOT NULL,
  `member` INT NOT NULL,
  `participant` INT NOT NULL,
  `is_invited` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`no`),
  INDEX `fk_inviteList_member1_idx` (`member` ASC) VISIBLE,
  INDEX `fk_inviteList_member2_idx` (`participant` ASC) VISIBLE,
  CONSTRAINT `fk_inviteList_member1`
    FOREIGN KEY (`member`)
    REFERENCES `Project_01`.`member` (`no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_inviteList_member2`
    FOREIGN KEY (`participant`)
    REFERENCES `Project_01`.`member` (`no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

insert into member values(null, 'master', '1234', 'master', null, null, null);   
insert into member values(null, 'raltlsdn', '1234', '김신우', 33, '부산시 동래구', false);
insert into member values(null, 'owner', '1234', 'ownerTest', 55, '부산시 동래구', true);

insert into cafe values(null, '우화하카페', '부산시 동래구', '온천동 130-89', (select no from member where id='owner'));

insert into member_schedule values(null, (select no from member where id='raltlsdn'), '2022-06-15 13:00:00', '2022-06-19 15:00:00', '테스트일정', '테스트를 위함');

insert into frndList values(null, (select no from member where id='raltlsdn'), (select no from member where id='owner'), false);

