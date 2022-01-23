DROP DATABASE IF EXISTS `delivery` ;
CREATE DATABASE IF NOT EXISTS `delivery`; -- DEFAULT CHARACTER SET utf8 ;

USE `delivery` ;

DROP TABLE IF EXISTS `language`;

CREATE TABLE IF NOT EXISTS `language`(
    `id` INT NOT NULL AUTO_INCREMENT,
    `short_name` VARCHAR(2) NOT NULL,
    `full_name` VARCHAR(45),
    PRIMARY KEY (`id`)
    );

DROP TABLE IF EXISTS `role`;

CREATE TABLE IF NOT EXISTS `role`(
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) UNIQUE NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `user`;

CREATE TABLE IF NOT EXISTS `user` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `login` VARCHAR (45) NOT NULL,
    `password` VARCHAR (45) NOT NULL,
    `role_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_role_id` FOREIGN KEY (`role_id`) REFERENCES `role`(`id`),
    UNIQUE KEY `login_UNIQUE` (`login`)
    );

DROP TABLE IF EXISTS `manager`;

CREATE TABLE IF NOT EXISTS `manager` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `name` VARCHAR (45) NOT NULL,
    `surname` VARCHAR (45) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_manager_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
    );

DROP TABLE IF EXISTS `person`;

CREATE TABLE IF NOT EXISTS `person` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `name` VARCHAR (45) NOT NULL,
    `surname` VARCHAR (45) NOT NULL,
    `patronymic` VARCHAR (45) NOT NULL,
    `email` VARCHAR(45) NOT NULL,
    `phone` VARCHAR (45) NOT NULL,
    CONSTRAINT `fk_person_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    PRIMARY KEY (`id`)
    );


DROP TABLE IF EXISTS `shipping_status`;

CREATE TABLE IF NOT EXISTS `shipping_status` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    `language` INT NOT NULL DEFAULT 1,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_shipping_status_language` FOREIGN KEY (`language`) REFERENCES `language` (`id`)
    );

DROP TABLE IF EXISTS `payment_status`;

CREATE TABLE IF NOT EXISTS `payment_status` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    `language` INT NOT NULL DEFAULT 1,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_payment_status_language` FOREIGN KEY (`language`) REFERENCES `language` (`id`)
);


DROP TABLE IF EXISTS `order`;

CREATE TABLE IF NOT EXISTS `order` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `shipping_address` VARCHAR(45) NOT NULL,
    `delivery_address` VARCHAR(45) NOT NULL,
    `creation_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `person_id` INT NOT NULL,
    `consignee` VARCHAR(45) NOT NULL,
    `description` VARCHAR(45) NOT NULL,
    `distance` DOUBLE NOT NULL,
    `length` DOUBLE NOT NULL DEFAULT 1,
    `height` DOUBLE NOT NULL DEFAULT 1,
    `width` DOUBLE NOT NULL DEFAULT 1,
    `weight` DOUBLE NOT NULL DEFAULT 0,
    `volume` DOUBLE NOT NULL DEFAULT 0,
    `fare` DECIMAL(8,2) NOT NULL DEFAULT 0,
    `shipping_status_id` INT NOT NULL,
    `payment_status_id` INT NOT NULL,
    `delivery_date` DATE,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_shipping_status_id` FOREIGN KEY (`shipping_status_id`) REFERENCES `shipping_status` (`id`),
    CONSTRAINT `fk_payment_status_id` FOREIGN KEY (`payment_status_id`) REFERENCES `payment_status` (`id`),
    CONSTRAINT `fk_person_id` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`)
    );

INSERT INTO role (id, name) VALUES (DEFAULT,'admin');
INSERT INTO role (id, name) VALUES (DEFAULT,'person');











