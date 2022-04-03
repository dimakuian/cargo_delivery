DROP SCHEMA IF EXISTS `delivery`;
CREATE SCHEMA IF NOT EXISTS `delivery`; -- DEFAULT CHARACTER SET utf8 ;

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
    `login`    VARCHAR(45) UNIQUE NOT NULL UNIQUE,
    `password` VARCHAR(45)        NOT NULL,
    `role_id`  INT                NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
        ON DELETE CASCADE
        ON UPDATE RESTRICT
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
        ON DELETE CASCADE
        ON UPDATE RESTRICT
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
    `balance`    DOUBLE      DEFAULT 0,
    CONSTRAINT `fk_client_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
        ON DELETE CASCADE ON UPDATE RESTRICT,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `shipping_status`;

CREATE TABLE IF NOT EXISTS `shipping_status`
(
    `id`   INT         NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL UNIQUE,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `shipping_status_description`;

CREATE TABLE IF NOT EXISTS `shipping_status_description`
(
    `shipping_status_id` INT         NOT NULL,
    `language_id`        INT         NOT NULL,
    `description`        VARCHAR(45) NOT NULL,
    CONSTRAINT `fk_shipping_status_description_id` FOREIGN KEY (`shipping_status_id`)
        REFERENCES `shipping_status` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
    CONSTRAINT `fk_shipping_status_language_id` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`)
        ON DELETE CASCADE ON UPDATE RESTRICT
);



DROP TABLE IF EXISTS `locality`;

CREATE TABLE IF NOT EXISTS `locality`
(
    `id`   INT         NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL UNIQUE,
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
    CONSTRAINT `fk_locality_id` FOREIGN KEY (`locality_id`) REFERENCES `locality` (`id`)
        ON DELETE CASCADE ON UPDATE RESTRICT,
    CONSTRAINT `fk_language_id` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`)
        ON DELETE CASCADE ON UPDATE RESTRICT
);

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
    `delivery_date`      TIMESTAMP              DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_shipping_address` FOREIGN KEY (`shipping_address`) REFERENCES `locality` (id)
        ON DELETE CASCADE ON UPDATE RESTRICT,
    CONSTRAINT `fk_delivery_address` FOREIGN KEY (`delivery_address`) REFERENCES `locality` (id)
        ON DELETE CASCADE ON UPDATE RESTRICT,
    CONSTRAINT `fk_shipping_status_id` FOREIGN KEY (`shipping_status_id`) REFERENCES `shipping_status` (`id`)
        ON DELETE CASCADE ON UPDATE RESTRICT,
    CONSTRAINT `fk_client_id` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`)
        ON DELETE CASCADE ON UPDATE RESTRICT
);

DROP TABLE IF EXISTS `invoice_status`;

CREATE TABLE IF NOT EXISTS `invoice_status`
(
    `id`   INT                NOT NULL,
    `name` VARCHAR(45) UNIQUE NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `invoice`;

CREATE TABLE IF NOT EXISTS `invoice`
(
    `id`                INT           NOT NULL AUTO_INCREMENT,
    `client_id`         INT           NOT NULL,
    `creation_datetime` DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `order_id`          INT           NOT NULL UNIQUE,
    `sum`               DECIMAL(8, 2) NOT NULL DEFAULT 0,
    `invoice_status_id` INT           NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_invoice_client_id` FOREIGN KEY (`client_id`) REFERENCES `client` (id)
        ON DELETE CASCADE ON UPDATE RESTRICT,
    CONSTRAINT `fk_order_id` FOREIGN KEY (`order_id`) REFERENCES `order` (id)
        ON DELETE CASCADE ON UPDATE RESTRICT,
    CONSTRAINT `fk_invoice_status_id` FOREIGN KEY (`invoice_status_id`) REFERENCES `invoice_status` (id)
        ON DELETE CASCADE ON UPDATE RESTRICT
);

INSERT INTO `role` (id, name)
VALUES (0, 'admin');

INSERT INTO `role` (id, name)
VALUES (1, 'client');


INSERT INTO `user` (id, login, password, role_id)
VALUES (DEFAULT, 'user1', '24c9e15e52afc47c225b757e7bee1f9d', 1),
       (DEFAULT, 'user2', '7e58d63b60197ceb55a1c487989a3720', 1),
       (DEFAULT, 'user3', '92877af70a45fd6a2ed7fe81e1236b78', 1),
       (DEFAULT, 'user4', '3f02ebe3d7929b091e3d8ccfde2f3bc6', 1),
       (DEFAULT, 'admin1', 'e00cf25ad42683b3df678c61f42c6bda', 0),
       (DEFAULT, 'admin2', 'c84258e9c39059a89ab77d846ddab909', 0),
       (DEFAULT, 'admin3', '32cacb2f994f6b42183a1300d9a3e8d6', 0);

INSERT INTO `admin` (id, user_id, name, surname)
VALUES (DEFAULT, 6, 'John', 'Jonson'),
       (DEFAULT, 7, 'Anatolij', 'Sych');

INSERT INTO `client` (id, user_id, name, surname, patronymic, email, phone)
VALUES (DEFAULT, 1, 'Borys', 'Horbenko', 'Stefanovych', 'mail1@example.com', '+380671111111'),
       (DEFAULT, 3, 'Marta', 'Semenova', 'Artemivna', 'mail3_wagner@example.com.ua', '+380671111113'),
       (DEFAULT, 4, 'Zoya', 'Bozhko', 'Havrylivna', 'mail4@gmail.com', '+380671111114');

INSERT INTO `language` (id, short_name, full_name)
VALUES (DEFAULT, 'EN', 'English');
INSERT INTO `language` (id, short_name, full_name)
VALUES (DEFAULT, 'UA', 'Ukraine');

INSERT INTO `locality` (id, name, lat, lng)
VALUES (DEFAULT, 'Kiev department №1', 50.430152159229465, 30.400358449390378),
       (DEFAULT, 'Lviv department №2', 49.85580301226521, 24.019571021514157),
       (DEFAULT, 'Odesa department №3', 46.47743026963285, 30.700937087405833),
       (DEFAULT, 'Ternopil department №4', 49.551989782699984, 25.57133777998255),
       (DEFAULT, 'Rivne department №5', 50.612594968969354, 26.246152539988664),
       (DEFAULT, 'Lutsk department №6', 50.73078011016832, 25.29546575120349),
       (DEFAULT, 'Kovel department №7', 51.19893769525515, 24.678678351324603),
       (DEFAULT, 'Chernivtsi department №8', 48.26790354152539, 25.928226407906312),
       (DEFAULT, 'Khmelnytskyi department №9', 49.42123992709202, 26.989182060186323),
       (DEFAULT, 'Vinnytsia department №10', 49.227985950780884, 28.45126010263941),
       (DEFAULT, 'Zhytomyr department №11', 50.24725716515697, 28.680031580973704),
       (DEFAULT, 'Dnipro department №12', 48.46696886348005, 34.97749206319865),
       (DEFAULT, 'Zaporizhzhya department №13', 47.83373266474125, 35.15006225982181),
       (DEFAULT, 'Mykolaiv department №14', 46.95604226441692, 32.032779749536004),
       (DEFAULT, 'Ivano-Frankivsk department №15', 48.91735027526064, 24.7003315862229),
       (DEFAULT, 'Mukachevo department №16', 48.444585453958084, 22.724035901090634);

INSERT INTO description_locality (locality_id, language_id, city, street, street_number)
VALUES (1, 1, 'Kiev', 'null', '0'),
       (1, 2, 'Київ', 'null', '0'),
       (2, 1, 'Lviv', 'null', '0'),
       (2, 2, 'Львів', 'null', '0'),
       (3, 1, 'Odesa', 'null', '0'),
       (3, 2, 'Одеса', 'null', '0'),
       (4, 1, 'Ternopil', 'null', '0'),
       (4, 2, 'Тернопіль', 'null', '0'),
       (5, 1, 'Rivne', 'null', '0'),
       (5, 2, 'Рівне', 'null', '0'),
       (6, 1, 'Lutsk', 'null', '0'),
       (6, 2, 'Луцьк', 'null', '0'),
       (7, 1, 'Kovel', 'null', '0'),
       (7, 2, 'Ковель', 'null', '0'),
       (8, 1, 'Chernivtsi', 'null', '0'),
       (8, 2, 'Чернівці', 'null', '0'),
       (9, 1, 'Khmelnytskyi', 'null', '0'),
       (9, 2, 'Хмельницький', 'null', '0'),
       (10, 1, 'Vinnytsia', 'null', '0'),
       (10, 2, 'Вінниця', 'null', '0'),
       (11, 1, 'Zhytomyr', 'null', '0'),
       (11, 2, 'Житомир', 'null', '0'),
       (12, 1, 'Dnipro', 'null', '0'),
       (12, 2, 'Дніпро', 'null', '0'),
       (13, 1, 'Zaporizhzhya', 'null', '0'),
       (13, 2, 'Запоріжжя', 'null', '0'),
       (14, 1, 'Mykolaiv', 'null', '0'),
       (14, 2, 'Миколаїв', 'null', '0'),
       (15, 1, 'Ivano-Frankivsk', 'null', '0'),
       (15, 2, 'Івано-Франківськ', 'null', '0'),
       (16, 1, 'Mukachevo', 'null', '0'),
       (16, 2, 'Мукачево', 'null', '0');

INSERT INTO `shipping_status` (id, name)
VALUES (DEFAULT, 'created'),
       (DEFAULT, 'paid'),
       (DEFAULT, 'confirmed'),
       (DEFAULT, 'preparing to ship'),
       (DEFAULT, 'in the way'),
       (DEFAULT, 'delivered'),
       (DEFAULT, 'canceled');

INSERT INTO `shipping_status_description` (shipping_status_id, language_id, description)
VALUES (1, 1, 'created'),
       (1, 2, 'створений'),
       (2, 1, 'paid'),
       (2, 2, 'оплачений'),
       (3, 1, 'confirmed'),
       (3, 2, 'підтверджений'),
       (4, 1, 'preparing to ship'),
       (4, 2, 'готується до відправлення'),
       (5, 1, 'in the way'),
       (5, 2, 'в дорозі'),
       (6, 1, 'delivered'),
       (6, 2, 'доставлений'),
       (7, 1, 'canceled'),
       (7, 2, 'скасований');

INSERT INTO `invoice_status` (id, name)
VALUES (0, 'created'),(1,'paid'),(2,'declined');

INSERT INTO `order` (id, shipping_address, delivery_address, creation_time, client_id, consignee, description, distance,
                     length, height, width, weight, volume, fare, shipping_status_id, delivery_date)
VALUES (DEFAULT, 1, 2, DEFAULT, 1, 'Test', 'Description', 300, 2, 2, 2, 3, 8, 30, 1, DEFAULT),
       (DEFAULT, 1, 2, DEFAULT, 1, 'Test2', 'Description2', 300, 2, 2, 2, 3, 8, 30, 1, DEFAULT);

INSERT INTO `invoice` (id, client_id, creation_datetime, order_id, sum, invoice_status_id)
VALUES (DEFAULT, 1, DEFAULT, 1, 30, 0);
















