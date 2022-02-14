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
    CONSTRAINT `fk_client_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
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
    `lat`  DECIMAL(10, 8),
    `lng`  DECIMAL(11, 8),
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

# DROP TABLE IF EXISTS tariff;

# CREATE TABLE IF NOT EXISTS `tariff`
# (
#     weight              DOUBLE NOT NULL,
#     price_up_to_500_km  INT    NOT NULL,
#     price_up_to_700_km  INT    NOT NULL,
#     price_up_to_900_km  INT    NOT NULL,
#     price_up_to_1200_km INT    NOT NULL,
#     price_up_to_1500_km INT    NOT NULL,
#     PRIMARY KEY (weight)
# );

DROP TABLE IF EXISTS `order`;

CREATE TABLE IF NOT EXISTS `order`
(
    `id`                 INT           NOT NULL AUTO_INCREMENT,
    `shipping_address`   INT           NOT NULL,
    `delivery_address`   INT           NOT NULL,
    `creation_time`      TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `client_id`          INT           NOT NULL,
    `consignee`          VARCHAR(45)   NOT NULL,
    `description`        VARCHAR(45)   NOT NULL,
    `distance`           DOUBLE        NOT NULL,
    `length`             DOUBLE        NOT NULL DEFAULT 1,
    `height`             DOUBLE        NOT NULL DEFAULT 1,
    `width`              DOUBLE        NOT NULL DEFAULT 1,
    `weight`             DOUBLE        NOT NULL DEFAULT 0.5,
    `volume`             DOUBLE        NOT NULL DEFAULT 1,
    `fare`               DECIMAL(8, 2) NOT NULL DEFAULT 0,
    `shipping_status_id` INT           NOT NULL,
    `payment_status_id`  INT           NOT NULL,
    `delivery_date`      TIMESTAMP     DEFAULT NULL,
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
VALUES (DEFAULT, 'dimakuian', '32250170a0dca92d53ec9624f336ca24', 1),
       (DEFAULT, 'john99', '32250170a0dca92d53ec9624f336ca24', 0);

INSERT INTO admin (id, user_id, name, surname)
VALUES (DEFAULT, 2, 'John', 'Jonson');

INSERT INTO client (id, user_id, name, surname, patronymic, email, phone)
VALUES (DEFAULT, 1, 'Dmytro', 'Kuian', 'Bogdanovich', 'mail@example.com', '+380671111111');

INSERT INTO language (id, short_name, full_name)
VALUES (DEFAULT, 'EN', 'English');
INSERT INTO language (id, short_name, full_name)
VALUES (DEFAULT, 'UA', 'Ukraine');

INSERT INTO locality (id, name, lat, lng)
VALUES (DEFAULT, 'Kiev department №2', 50.430152159229465, 30.400358449390378),
       (DEFAULT, 'Lviv department №1', 49.85580301226521, 24.019571021514157),
       (DEFAULT, 'Odesa department №3', 46.47743026963285, 30.700937087405833),
       (DEFAULT, 'Ternopil department №4', 49.551989782699984, 25.57133777998255),
       (DEFAULT, 'Rivne department №5', 50.612594968969354, 26.246152539988664);

INSERT INTO shipping_status (id, name_EN, name_UK)
VALUES (DEFAULT, 'created', 'створений'),
       (DEFAULT, 'confirmed', 'підтверджений');

INSERT INTO payment_status (id, name_EN, name_UK)
VALUES (DEFAULT, 'paid', 'оплачений'),
       (DEFAULT, 'not paid', 'не оплачений');



# INSERT INTO tariff (weight, price_up_to_500_km, price_up_to_700_km, price_up_to_900_km, price_up_to_1200_km,
#                     price_up_to_1500_km)
# VALUES (0.5, 35, 39, 46, 53, 70),
#        (1, 40, 44, 52, 60, 80),
#        (2, 45, 50, 59, 68, 90),
#        (5, 50, 55, 65, 75, 100),
#        (10, 55, 61, 72, 83, 110),
#        (20, 70, 77, 91, 105, 140),
#        (30, 85, 94, 111, 128, 170),
#        (40, 100, 110, 130, 150, 200),
#        (50, 120, 132, 156, 180, 240),
#        (60, 150, 165, 195, 225, 300),
#        (70, 180, 198, 234, 270, 360),
#        (80, 210, 231, 273, 315, 420),
#        (90, 250, 275, 325, 375, 500),
#        (100, 300, 330, 390, 450, 600);

# SELECT a.name AS from_city,
#        b.name AS to_city,
#        ROUND(
#                    111.111 *
#                    DEGREES(ACOS(LEAST(1.0, COS(RADIANS(a.lat))
#                    * COS(RADIANS(b.lat))
#                    * COS(RADIANS(a.lng - b.lng))
#                    + SIN(RADIANS(a.lat))
#                    * SIN(RADIANS(b.lat)))))) + 40 AS distance_in_km
# FROM locality AS a
#          JOIN locality AS b ON a.id <> b.id
# WHERE a.id = ?
#   AND b.id = ?;












