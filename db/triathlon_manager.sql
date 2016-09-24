-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema triathlon_manager
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema triathlon_manager
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `triathlon_manager` DEFAULT CHARACTER SET utf8 ;
USE `triathlon_manager` ;

-- -----------------------------------------------------
-- Table `triathlon_manager`.`tournament`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `triathlon_manager`.`tournament` (
  `t_id` INT NOT NULL AUTO_INCREMENT,
  `t_name` VARCHAR(200) NOT NULL,
  `t_desc` VARCHAR(1000) NOT NULL,
  PRIMARY KEY (`t_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `triathlon_manager`.`race`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `triathlon_manager`.`race` (
  `r_id` INT NOT NULL AUTO_INCREMENT,
  `r_name` VARCHAR(200) NOT NULL,
  `r_desc` VARCHAR(1000) NOT NULL,
  `r_startdate` DATE NOT NULL,
  `r_enddate` DATE NULL,
  `r_national` TINYINT(1) NULL DEFAULT 0,
  `tournament_id` INT NULL,
  PRIMARY KEY (`r_id`),
  INDEX `fk_race_trournament_idx` (`tournament_id` ASC),
  CONSTRAINT `fk_race_tournament`
    FOREIGN KEY (`tournament_id`)
    REFERENCES `triathlon_manager`.`tournament` (`t_id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `triathlon_manager`.`club`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `triathlon_manager`.`club` (
  `c_id` INT NOT NULL AUTO_INCREMENT,
  `c_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`c_id`),
  UNIQUE INDEX `c_name_UNIQUE` (`c_name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `triathlon_manager`.`contestant`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `triathlon_manager`.`contestant` (
  `c_id` INT NOT NULL AUTO_INCREMENT,
  `c_name` VARCHAR(100) NOT NULL,
  `c_birthyear` SMALLINT NOT NULL,
  `c_gender` ENUM('MALE','FEMALE') NOT NULL,
  `c_fromtown` VARCHAR(100) NULL,
  `club_id` INT NULL,
  PRIMARY KEY (`c_id`),
  INDEX `fk_contestant_club1_idx` (`club_id` ASC),
  CONSTRAINT `fk_contestant_club1`
    FOREIGN KEY (`club_id`)
    REFERENCES `triathlon_manager`.`club` (`c_id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `triathlon_manager`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `triathlon_manager`.`category` (
  `c_id` INT NOT NULL AUTO_INCREMENT,
  `c_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`c_id`),
  UNIQUE INDEX `c_name_UNIQUE` (`c_name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `triathlon_manager`.`agegroup`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `triathlon_manager`.`agegroup` (
  `a_id` INT NOT NULL AUTO_INCREMENT,
  `a_name` VARCHAR(100) NOT NULL,
  `a_startyear` SMALLINT NOT NULL,
  `a_displayorder` TINYINT NOT NULL,
  PRIMARY KEY (`a_id`),
  UNIQUE INDEX `a_name_UNIQUE` (`a_name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `triathlon_manager`.`familyentry`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `triathlon_manager`.`familyentry` (
  `f_id` INT NOT NULL AUTO_INCREMENT,
  `f_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`f_id`),
  UNIQUE INDEX `f_name_UNIQUE` (`f_name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `triathlon_manager`.`invoice`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `triathlon_manager`.`invoice` (
  `i_id` INT NOT NULL AUTO_INCREMENT,
  `i_customername` VARCHAR(150) NOT NULL,
  `i_customeraddress` VARCHAR(250) NOT NULL,
  `i_paymentmethod` ENUM('CASH', 'TRANSFER') NOT NULL,
  PRIMARY KEY (`i_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `triathlon_manager`.`entry`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `triathlon_manager`.`entry` (
  `race_id` INT NOT NULL,
  `contestant_id` INT NOT NULL,
  `e_racenum` VARCHAR(5) NOT NULL,
  `e_entrytime` DATETIME NOT NULL,
  `e_racetime` TIME NULL,
  `e_racetimemods` VARCHAR(1000) NULL,
  `e_resultmods` VARCHAR(500) NULL,
  `e_finishtime` DATETIME NULL,
  `e_status` ENUM('CHECKED', 'FINISHED', 'NOTPRESENT', 'DSQ', 'DNF', 'PRE', 'NOTSTARTED') NOT NULL,
  `e_licencenum` VARCHAR(50) NULL,
  `e_preentry` TINYINT(1) NOT NULL DEFAULT 0,
  `e_paid` TINYINT(1) NOT NULL DEFAULT 0,
  `e_remainingpayment` INT NULL DEFAULT 0,
  `category_id` INT NOT NULL,
  `agegroup_id` INT NOT NULL,
  `familyentry_id` INT NULL,
  `invoice_id` INT NULL,
  PRIMARY KEY (`race_id`, `e_racenum`),
  INDEX `fk_race_has_contestant_contestant1_idx` (`contestant_id` ASC),
  INDEX `fk_race_has_contestant_race1_idx` (`race_id` ASC),
  INDEX `fk_entry_category1_idx` (`category_id` ASC),
  INDEX `fk_entry_agegroup1_idx` (`agegroup_id` ASC),
  INDEX `fk_entry_familyentry1_idx` (`familyentry_id` ASC),
  INDEX `fk_entry_invoice1_idx` (`invoice_id` ASC),
  CONSTRAINT `fk_entry_race1`
    FOREIGN KEY (`race_id`)
    REFERENCES `triathlon_manager`.`race` (`r_id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_entry_contestant1`
    FOREIGN KEY (`contestant_id`)
    REFERENCES `triathlon_manager`.`contestant` (`c_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_entry_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `triathlon_manager`.`category` (`c_id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_entry_agegroup1`
    FOREIGN KEY (`agegroup_id`)
    REFERENCES `triathlon_manager`.`agegroup` (`a_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_entry_familyentry1`
    FOREIGN KEY (`familyentry_id`)
    REFERENCES `triathlon_manager`.`familyentry` (`f_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_entry_invoice1`
    FOREIGN KEY (`invoice_id`)
    REFERENCES `triathlon_manager`.`invoice` (`i_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `triathlon_manager`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `triathlon_manager`.`user` (
  `u_username` VARCHAR(100) NOT NULL,
  `u_displayname` VARCHAR(100) NOT NULL,
  `u_password` VARCHAR(128) NOT NULL,
  UNIQUE INDEX `u_username_UNIQUE` (`u_username` ASC),
  PRIMARY KEY (`u_username`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `triathlon_manager`.`usergroup`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `triathlon_manager`.`usergroup` (
  `ug_name` VARCHAR(50) NOT NULL,
  `u_username` VARCHAR(100) NOT NULL,
  INDEX `fk_usegroup_user1_idx` (`u_username` ASC),
  PRIMARY KEY (`ug_name`, `u_username`),
  CONSTRAINT `fk_usegroup_user1`
    FOREIGN KEY (`u_username`)
    REFERENCES `triathlon_manager`.`user` (`u_username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `triathlon_manager`.`licence`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `triathlon_manager`.`licence` (
  `l_id` INT NOT NULL AUTO_INCREMENT,
  `l_name` VARCHAR(100) NOT NULL,
  `l_licencenum` VARCHAR(50) NOT NULL,
  `l_birthplace` VARCHAR(100) NOT NULL,
  `l_birthyear` SMALLINT NOT NULL,
  PRIMARY KEY (`l_id`),
  INDEX `idx_name` (`l_name` ASC),
  INDEX `idx_licencenum` (`l_licencenum`(10) ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `triathlon_manager`.`resultmod`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `triathlon_manager`.`resultmod` (
  `r_id` INT NOT NULL AUTO_INCREMENT,
  `r_idname` VARCHAR(50) NOT NULL,
  `r_name` VARCHAR(100) NOT NULL,
  `r_plus` TINYINT(1) NOT NULL,
  `r_time` TIME NOT NULL,
  PRIMARY KEY (`r_id`),
  UNIQUE INDEX `r_idname_UNIQUE` (`r_idname` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `triathlon_manager`.`raceadjustment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `triathlon_manager`.`raceadjustment` (
  `race_id` INT NOT NULL,
  `resultmod_id` INT NOT NULL,
  `category_id` INT NOT NULL,
  `ra_gender` ENUM('MALE', 'FEMALE') NOT NULL,
  PRIMARY KEY (`race_id`, `resultmod_id`, `category_id`),
  INDEX `fk_raceadjustment_resultmod1_idx` (`resultmod_id` ASC),
  INDEX `fk_raceadjustment_category1_idx` (`category_id` ASC),
  CONSTRAINT `fk_raceadjustment_race1`
    FOREIGN KEY (`race_id`)
    REFERENCES `triathlon_manager`.`race` (`r_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_raceadjustment_resultmod1`
    FOREIGN KEY (`resultmod_id`)
    REFERENCES `triathlon_manager`.`resultmod` (`r_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_raceadjustment_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `triathlon_manager`.`category` (`c_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
