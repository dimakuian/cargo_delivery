DROP DATABASE IF EXISTS `delivery`;
CREATE DATABASE IF NOT EXISTS `delivery`; -- DEFAULT CHARACTER SET utf8 ;

USE `delivery`;

DROP TABLE IF EXISTS `language`;

CREATE TABLE IF NOT EXISTS `language`
(
    `id`         INT               NOT NULL AUTO_INCREMENT,
    `short_name` VARCHAR(2) UNIQUE NOT NULL,
    `full_name`  VARCHAR(45),
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `role`;

CREATE TABLE IF NOT EXISTS `role`
(
    `id`   INT                NOT NULL,
    `name` VARCHAR(45) UNIQUE NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `user`;

CREATE TABLE IF NOT EXISTS `user`
(
    `id`       INT                NOT NULL AUTO_INCREMENT,
    `login`    VARCHAR(45) UNIQUE NOT NULL,
    `password` VARCHAR(45)        NOT NULL,
    `role_id`  INT                NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
    UNIQUE KEY `login_UNIQUE` (`login`)
);

DROP TABLE IF EXISTS `admin`;

CREATE TABLE IF NOT EXISTS `admin`
(
    `id`      INT         NOT NULL AUTO_INCREMENT,
    `user_id` INT         NOT NULL,
    `name`    VARCHAR(45) NOT NULL,
    `surname` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_manager_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

DROP TABLE IF EXISTS `client`;

CREATE TABLE IF NOT EXISTS `client`
(
    `id`         INT         NOT NULL AUTO_INCREMENT,
    `user_id`    INT         NOT NULL,
    `name`       VARCHAR(45) NOT NULL,
    `surname`    VARCHAR(45) NOT NULL,
    `patronymic` VARCHAR(45) DEFAULT NULL,
    `email`      VARCHAR(45) DEFAULT NULL,
    `phone`      VARCHAR(45) DEFAULT NULL,
    CONSTRAINT `fk_client_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `shipping_status`;

CREATE TABLE IF NOT EXISTS `shipping_status`
(
    `id`      INT         NOT NULL AUTO_INCREMENT,
    `name_EN` VARCHAR(45) NOT NULL,
    `name_UK` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `payment_status`;

CREATE TABLE IF NOT EXISTS `payment_status`
(
    `id`      INT         NOT NULL AUTO_INCREMENT,
    `name_EN` VARCHAR(45) NOT NULL,
    `name_UK` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `locality`;

CREATE TABLE IF NOT EXISTS `locality`
(
    `id`   INT         NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `description_locality`;

CREATE TABLE IF NOT EXISTS `description_locality`
(
    `locality_id`   INT         NOT NULL,
    `language_id`   INT         NOT NULL,
    `city`          VARCHAR(45) NOT NULL,
    `street`        VARCHAR(45) NOT NULL,
    `street_number` VARCHAR(45) NOT NULL,
    CONSTRAINT `fk_locality_id` FOREIGN KEY (`locality_id`) REFERENCES `locality` (`id`),
    CONSTRAINT `fk_language_id` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`)
);

DROP TABLE IF EXISTS tariff;

CREATE TABLE IF NOT EXISTS `tariff`
(
    weight             DOUBLE NOT NULL,
    price_up_to_500_km INT    NOT NULL,
    price_up_to_700_km INT    NOT NULL,
    price_up_to_900_km INT    NOT NULL,
    price_up_to_1200_km   INT    NOT NULL,
    price_up_to_1500_km   INT    NOT NULL,
    PRIMARY KEY (weight)
);

DROP TABLE IF EXISTS `order`;

CREATE TABLE IF NOT EXISTS `order`
(
    `id`                 INT           NOT NULL AUTO_INCREMENT,
    `shipping_address`   INT           NOT NULL,
    `delivery_address`   INT           NOT NULL,
    `creation_time`      DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `client_id`          INT           NOT NULL,
    `consignee`          VARCHAR(45)   NOT NULL,
    `description`        VARCHAR(45)   NOT NULL,
    `distance`           DOUBLE        NOT NULL,
    `length`             DOUBLE        NOT NULL DEFAULT 1,
    `height`             DOUBLE        NOT NULL DEFAULT 1,
    `width`              DOUBLE        NOT NULL DEFAULT 1,
    `weight`             DOUBLE        NOT NULL DEFAULT 0,
    `volume`             DOUBLE        NOT NULL DEFAULT 0,
    `fare`               DECIMAL(8, 2) NOT NULL DEFAULT 0,
    `shipping_status_id` INT           NOT NULL,
    `payment_status_id`  INT           NOT NULL,
    `delivery_date`      DATE,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_shipping_address` FOREIGN KEY (`shipping_address`) REFERENCES `locality` (id),
    CONSTRAINT `fk_delivery_address` FOREIGN KEY (`delivery_address`) REFERENCES `locality` (id),
    CONSTRAINT `fk_shipping_status_id` FOREIGN KEY (`shipping_status_id`) REFERENCES `shipping_status` (`id`),
    CONSTRAINT `fk_payment_status_id` FOREIGN KEY (`payment_status_id`) REFERENCES `payment_status` (`id`),
    CONSTRAINT `fk_client_id` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`)
);

INSERT INTO role (id, name)
VALUES (0, 'admin');

INSERT INTO role (id, name)
VALUES (1, 'client');


INSERT INTO user (id, login, password, role_id)
VALUES (DEFAULT, 'dimakuian', 'pass123', 1),
       (DEFAULT, 'john99', 'pass321', 0);

INSERT INTO admin (id, user_id, name, surname)
VALUES (DEFAULT, 2, 'John', 'Jonson');

INSERT INTO language (id, short_name, full_name)
VALUES (DEFAULT, 'EN', 'English');
INSERT INTO language (id, short_name, full_name)
VALUES (DEFAULT, 'UA', 'Ukraine');

INSERT INTO locality (id, name)
VALUES (DEFAULT, 'Kiev');
INSERT INTO locality (id, name)
VALUES (DEFAULT, 'Lviv');
INSERT INTO locality (id, name)
VALUES (DEFAULT, 'Odesa');
INSERT INTO locality (id, name)
VALUES (DEFAULT, 'Ternopil');
INSERT INTO locality (id, name)
VALUES (DEFAULT, 'Rivne');

INSERT INTO tariff (weight, price_up_to_500_km, price_up_to_700_km, price_up_to_900_km, price_up_to_1200_km, price_up_to_1500_km)
VALUES (0.5, 35, 39, 46, 53, 70),
       (1, 40, 44, 52, 60, 80),
       (2, 45, 50, 59, 68, 90),
       (5, 50, 55, 65, 75, 100),
       (10, 55, 61, 72, 83, 110),
       (20, 70, 77, 91, 105, 140),
       (30, 85, 94, 111, 128, 170),
       (40, 100, 110, 130, 150, 200),
       (50, 120, 132, 156, 180, 240),
       (60, 150, 165, 195, 225, 300),
       (70, 180, 198, 234, 270, 360),
       (80, 210, 231, 273, 315, 420),
       (90, 250, 275, 325, 375, 500),
       (100, 300, 330, 390, 450, 600);















