package com.epam.delivery.db.doa;

public final class SqlQuery {
    //USER
    public static final String SQL_QUERY__USER_INSERT = "INSERT INTO delivery.`user` (id,login,password,role_id) VALUES (DEFAULT,?,?,?)";
    public static final String SQL_QUERY__USER_UPDATE = "UPDATE delivery.`user` SET login = ?, password = ?, role_id = ? WHERE id = ?";
    public static final String SQL_QUERY__USER_SELECT_BY_ID = "SELECT id, login, password, role_id FROM delivery.`user` WHERE id = ?";
    public static final String SQL_QUERY__USER_SELECT_BY_LOGIN = "SELECT id, login, password, role_id FROM delivery.`user` WHERE login = ?";
    public static final String SQL_QUERY__USER_EXIST = "SELECT id FROM delivery.`user` WHERE id=?";
    public static final String SQL_QUERY__USER_EXIST_BY_LOGIN = "SELECT login FROM delivery.`user` WHERE login=?";
    public static final String SQL_QUERY__USER_SELECT_ALL = "SELECT id, login, password, role_id FROM delivery.`user`";
    public static final String SQL_QUERY__USER_DELETE = "DELETE FROM delivery.`user` WHERE id =?";

    //ADMIN
    public static final String SQL_QUERY__ADMIN_INSERT = "INSERT INTO delivery.`admin` (id, user_id, name, surname) VALUES (DEFAULT,?,?,?)";
    public static final String SQL_QUERY__ADMIN_UPDATE = "UPDATE delivery.`admin` SET user_id = ?, name = ?, surname = ? WHERE id = ?";
    public static final String SQL_QUERY__ADMIN_SELECT_BY_ID = "SELECT id, user_id, name, surname FROM delivery.`admin` WHERE id = ?";
    public static final String SQL_QUERY__ADMIN_SELECT_BY_USER_ID = "SELECT id, user_id, name, surname FROM delivery.`admin` WHERE user_id = ?";
    public static final String SQL_QUERY__ADMIN_EXIST = "SELECT id FROM delivery.`admin` WHERE id=?";
    public static final String SQL_QUERY__ADMIN_SELECT_ALL = "SELECT id, user_id, name, surname FROM delivery.`admin`";
    public static final String SQL_QUERY__ADMIN_DELETE = "DELETE FROM delivery.`admin` WHERE id=?";

    //CLIENT
    public static final String SQL_QUERY__CLIENT_UPDATE_BALANCE = "UPDATE delivery.`client` SET balance=? WHERE id=?";

    public static final String QL_QUERY__CLIENT_INSERT = "INSERT INTO delivery.`client` (id, user_id, name, surname, patronymic, email, phone, " +
            "balance) VALUES (DEFAULT,?,?,?,?,?,?,?)";

    public static final String QL_QUERY__CLIENT_UPDATE = "UPDATE delivery.`client` SET user_id=?,name=?,surname=?,patronymic=?,email=?,phone=?," +
            "balance=? WHERE id=?";

    public static final String QL_QUERY__CLIENT_SELECT_BY_ID = "SELECT id, user_id, name, surname, patronymic, email, phone, balance "
            + "FROM delivery.`client` WHERE id=?";

    public static final String QL_QUERY__CLIENT_SELECT_BY_USER_ID = "SELECT id, user_id, name, surname, patronymic, email, phone, balance "
            + "FROM delivery.`client` WHERE user_id=?";

    public static final String QL_QUERY__CLIENT_EXIST = "SELECT id FROM delivery.`client` WHERE id=?";

    public static final String QL_QUERY__CLIENT_EXIST_EMAIL = "SELECT email FROM delivery.`client` WHERE email=?";

    public static final String QL_QUERY__CLIENT_EXIST_PHONE = "SELECT phone FROM delivery.`client` WHERE phone=?";

    public static final String QL_QUERY__CLIENT_SELECT_ALL = "SELECT id, user_id, name, surname, patronymic, email, phone, balance "
            + "FROM delivery.`client`";

    public static final String QL_QUERY__CLIENT_DELETE = "DELETE FROM delivery.`client` WHERE id=?";

    //LANGUAGE
    public static final String QL_QUERY__LANGUAGE_INSERT = "INSERT INTO delivery.`language` (id, short_name, full_name) VALUES (DEFAULT,?,?)";

    public static final String QL_QUERY__LANGUAGE_UPDATE = "UPDATE delivery.`language` SET short_name = ?, full_name = ? WHERE id = ?";

    public static final String QL_QUERY__LANGUAGE_SELECT_BY_ID = "SELECT id, short_name, full_name FROM delivery.`language` WHERE id = ?";

    public static final String QL_QUERY__LANGUAGE_EXIST = "SELECT id FROM delivery.`language` WHERE id=?";

    public static final String QL_QUERY__LANGUAGE_SELECT_ALL = "SELECT id, short_name, full_name FROM delivery.`language`";

    public static final String QL_QUERY__LANGUAGE_DELETE = "DELETE FROM delivery.`language` WHERE id=?";

    //LOCALITY
    public static final String SQL_QUERY__LOCALITY_INSERT = "INSERT INTO delivery.`locality` VALUES (DEFAULT,?,?,?)";

    public static final String SQL_QUERY__LOCALITY_UPDATE = "UPDATE delivery.`locality` SET name=?,lat=?,lng=? WHERE id=?";

    public static final String SQL_QUERY__LOCALITY_SELECT_BY_ID = "SELECT id, name, lat, lng FROM delivery.`locality` WHERE id =?";

    public static final String SQL_QUERY__LOCALITY_EXIST = "SELECT id FROM delivery.`locality` WHERE id =?";

    public static final String SQL_QUERY__LOCALITY_SELECT_ALL = "SELECT id, name, lat, lng FROM delivery.`locality`";

    public static final String SQL_QUERY__LOCALITY_DELETE = "DELETE FROM delivery.`locality` WHERE id=?";
    public static final String SQL_QUERY__LOCALITY_CALC_DISTANCE = "SELECT a.name AS from_city,\n" +
            "       b.name AS to_city,\n" +
            "       ROUND(\n" +
            "                   111.111 *\n" +
            "                   DEGREES(ACOS(LEAST(1.0, COS(RADIANS(a.lat))\n" +
            "                   * COS(RADIANS(b.lat))\n" +
            "                   * COS(RADIANS(a.lng - b.lng))\n" +
            "                   + SIN(RADIANS(a.lat))\n" +
            "                   * SIN(RADIANS(b.lat)))))) + 40 AS distance_in_km\n" +
            "FROM delivery.`locality` AS a\n" +
            "         JOIN delivery.`locality` AS b ON a.id <> b.id\n" +
            "WHERE a.id = ?\n" +
            "  AND b.id = ?";

    public static final String SQL_QUERY__LOCALITY_SELECT_TRANSLATE_BY_STATUS_ID =
            "SELECT locality_id, language_id, city, street, street_number FROM description_locality " +
                    "WHERE locality_id=? AND language_id=?;\n";

    //ORDER
    public static final String SQL_QUERY__ORDER_INSERT = "INSERT INTO delivery.`order` (id, shipping_address, delivery_address, " +
            "creation_time, client_id, consignee, description, distance, length, height, width, weight, volume, " +
            "fare, shipping_status_id, delivery_date) \n" +
            "VALUES (DEFAULT,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public static final String SQL_QUERY__ORDER_UPDATE = "UPDATE delivery.`order` SET shipping_address=?,delivery_address=?,creation_time=?," +
            "client_id=?,consignee=?,description=?,distance=?,length=?,width=?,height=?,weight=?,volume=?,fare=?," +
            "shipping_status_id=?,delivery_date=? WHERE id=?";

    public static final String SQL_QUERY__ORDER_SELECT_BY_ID = "SELECT id, shipping_address, delivery_address, creation_time, client_id," +
            " consignee, description, distance, length, height, width, weight, volume, fare, shipping_status_id, " +
            " delivery_date FROM delivery.`order` WHERE id=?";

    public static final String SQL_QUERY__ORDER_EXIST = "SELECT id FROM delivery.`order` WHERE id =?";

    public static final String SQL_QUERY__ORDER_SELECT_ALL = "SELECT id, shipping_address, delivery_address, " +
            "creation_time, client_id, consignee, description, distance, length, height, width, weight, volume, fare, " +
            "shipping_status_id, delivery_date FROM delivery.`order`";

    public static final String SQL_QUERY__ORDER_SELECT_ALL_FOR_CLIENT = "SELECT id, shipping_address, delivery_address, " +
            "creation_time, client_id, consignee, description, distance, length, height, width, weight, volume, fare, " +
            "shipping_status_id, delivery_date FROM delivery.`order`WHERE client_id=?";

    public static final String SQL_QUERY__ORDER_DELETE = "DELETE FROM delivery.`order` WHERE id=?";

    public static final String SQL_QUERY__ORDER_SELECT_ALL_ORDER_BEAN = "SELECT o.id,\n" +
            "       shipping_address,\n" +
            "       CONCAT_WS(' ', sa_ua.city, sa_ua.street, sa_ua.street_number) AS shipping_address_UA,\n" +
            "       CONCAT_WS(' ', sa_en.city, sa_en.street, sa_en.street_number) AS shipping_address_EN,\n" +
            "       delivery_address,\n" +
            "       CONCAT_WS(' ', da_ua.city, da_ua.street, da_ua.street_number) AS delivery_address_UA,\n" +
            "       CONCAT_WS(' ', da_en.city, da_en.street, da_en.street_number) AS delivery_address_EN,\n" +
            "       o.creation_time,\n" +
            "       o.client_id,\n" +
            "       c.name,\n" +
            "       c.surname,\n" +
            "       c.patronymic,\n" +
            "       o.consignee,\n" +
            "       o.description,\n" +
            "       o.distance,\n" +
            "       o.length,\n" +
            "       o.height,\n" +
            "       o.width,\n" +
            "       o.weight,\n" +
            "       o.volume,\n" +
            "       o.fare,\n" +
            "       ssd_ua.shipping_status_id                                     AS status_id,\n" +
            "       ssd_ua.description                                            AS status_ua,\n" +
            "       ssd_en.description                                            AS status_en,\n" +
            "       o.delivery_date\n" +
            "FROM `order` o\n" +
            "         JOIN description_locality sa_ua ON sa_ua.locality_id = o.shipping_address\n" +
            "         JOIN description_locality sa_en ON sa_en.locality_id = o.shipping_address\n" +
            "         JOIN description_locality da_ua ON da_ua.locality_id = o.delivery_address\n" +
            "         JOIN description_locality da_en ON da_en.locality_id = o.delivery_address\n" +
            "         JOIN client c on o.client_id = c.id\n" +
            "         JOIN shipping_status_description ssd_ua on o.shipping_status_id = ssd_ua.shipping_status_id\n" +
            "         JOIN shipping_status_description ssd_en on o.shipping_status_id = ssd_en.shipping_status_id\n" +
            "WHERE sa_ua.language_id = 2\n" +
            "  AND sa_en.language_id = 1\n" +
            "  AND da_ua.language_id = 2\n" +
            "  AND da_en.language_id = 1\n" +
            "  AND ssd_ua.language_id = 2\n" +
            "  AND ssd_en.language_id = 1\n" +
            "  AND (fare BETWEEN ? AND ?)\n" +
            "ORDER BY %s\n" +
            "LIMIT ?,?";

    public static final String SQL_QUERY__ORDER_BEAN_SELECT_ALL_WITH_ALL_FILTER = "SELECT o.id,\n" +
            "       shipping_address,\n" +
            "       CONCAT_WS(' ', sa_ua.city, sa_ua.street, sa_ua.street_number) AS shipping_address_UA,\n" +
            "       CONCAT_WS(' ', sa_en.city, sa_en.street, sa_en.street_number) AS shipping_address_EN,\n" +
            "       delivery_address,\n" +
            "       CONCAT_WS(' ', da_ua.city, da_ua.street, da_ua.street_number) AS delivery_address_UA,\n" +
            "       CONCAT_WS(' ', da_en.city, da_en.street, da_en.street_number) AS delivery_address_EN,\n" +
            "       o.creation_time,\n" +
            "       o.client_id,\n" +
            "       c.name,\n" +
            "       c.surname,\n" +
            "       c.patronymic,\n" +
            "       o.consignee,\n" +
            "       o.description,\n" +
            "       o.distance,\n" +
            "       o.length,\n" +
            "       o.height,\n" +
            "       o.width,\n" +
            "       o.weight,\n" +
            "       o.volume,\n" +
            "       o.fare,\n" +
            "       ssd_ua.shipping_status_id                                     AS status_id,\n" +
            "       ssd_ua.description                                            AS status_ua,\n" +
            "       ssd_en.description                                            AS status_en,\n" +
            "       o.delivery_date\n" +
            "FROM `order` o\n" +
            "         JOIN description_locality sa_ua ON sa_ua.locality_id = o.shipping_address\n" +
            "         JOIN description_locality sa_en ON sa_en.locality_id = o.shipping_address\n" +
            "         JOIN description_locality da_ua ON da_ua.locality_id = o.delivery_address\n" +
            "         JOIN description_locality da_en ON da_en.locality_id = o.delivery_address\n" +
            "         JOIN client c on o.client_id = c.id\n" +
            "         JOIN shipping_status_description ssd_ua on o.shipping_status_id = ssd_ua.shipping_status_id\n" +
            "         JOIN shipping_status_description ssd_en on o.shipping_status_id = ssd_en.shipping_status_id\n" +
            "WHERE sa_ua.language_id = 2\n" +
            "  AND sa_en.language_id = 1\n" +
            "  AND da_ua.language_id = 2\n" +
            "  AND da_en.language_id = 1\n" +
            "  AND ssd_ua.language_id = 2\n" +
            "  AND ssd_en.language_id = 1\n" +
            "  AND (fare BETWEEN ? AND ?)\n" +
            "%s\n" +
            "ORDER BY %s\n" +
            "LIMIT ?,?";

    public static final String SQL_QUERY__ORDER_SELECT_MAX_FARE = "SELECT MAX(fare) FROM `delivery`.`order`";

    public static final String SQL_QUERY__SELECT_USER_ORDERS_BEAN = "SELECT o.id,\n" +
            "       shipping_address,\n" +
            "       CONCAT_WS(' ', sa_ua.city, sa_ua.street, sa_ua.street_number) AS shipping_address_UA,\n" +
            "       CONCAT_WS(' ', sa_en.city, sa_en.street, sa_en.street_number) AS shipping_address_EN,\n" +
            "       delivery_address,\n" +
            "       CONCAT_WS(' ', da_ua.city, da_ua.street, da_ua.street_number) AS delivery_address_UA,\n" +
            "       CONCAT_WS(' ', da_en.city, da_en.street, da_en.street_number) AS delivery_address_EN,\n" +
            "       o.creation_time,\n" +
            "       o.client_id,\n" +
            "       c.name,\n" +
            "       c.surname,\n" +
            "       c.patronymic,\n" +
            "       o.consignee,\n" +
            "       o.description,\n" +
            "       o.distance,\n" +
            "       o.length,\n" +
            "       o.height,\n" +
            "       o.width,\n" +
            "       o.weight,\n" +
            "       o.volume,\n" +
            "       o.fare,\n" +
            "       ssd_ua.shipping_status_id                                     AS status_id,\n" +
            "       ssd_ua.description                                            AS status_ua,\n" +
            "       ssd_en.description                                            AS status_en,\n" +
            "       o.delivery_date\n" +
            "FROM `order` o\n" +
            "         JOIN description_locality sa_ua ON sa_ua.locality_id = o.shipping_address\n" +
            "         JOIN description_locality sa_en ON sa_en.locality_id = o.shipping_address\n" +
            "         JOIN description_locality da_ua ON da_ua.locality_id = o.delivery_address\n" +
            "         JOIN description_locality da_en ON da_en.locality_id = o.delivery_address\n" +
            "         JOIN client c on o.client_id = c.id\n" +
            "         JOIN shipping_status_description ssd_ua on o.shipping_status_id = ssd_ua.shipping_status_id\n" +
            "         JOIN shipping_status_description ssd_en on o.shipping_status_id = ssd_en.shipping_status_id\n" +
            "WHERE sa_ua.language_id = 2\n" +
            "  AND sa_en.language_id = 1\n" +
            "  AND da_ua.language_id = 2\n" +
            "  AND da_en.language_id = 1\n" +
            "  AND ssd_ua.language_id = 2\n" +
            "  AND ssd_en.language_id = 1\n" +
            "  AND client_id = ?\n" +
            "ORDER BY %s\n" +
            "LIMIT ?,?";

    public static final String SQL_QUERY__SELECT_USER_ORDERS_BEAN_FILTER = "SELECT o.id,\n" +
            "       shipping_address,\n" +
            "       CONCAT_WS(' ', sa_ua.city, sa_ua.street, sa_ua.street_number) AS shipping_address_UA,\n" +
            "       CONCAT_WS(' ', sa_en.city, sa_en.street, sa_en.street_number) AS shipping_address_EN,\n" +
            "       delivery_address,\n" +
            "       CONCAT_WS(' ', da_ua.city, da_ua.street, da_ua.street_number) AS delivery_address_UA,\n" +
            "       CONCAT_WS(' ', da_en.city, da_en.street, da_en.street_number) AS delivery_address_EN,\n" +
            "       o.creation_time,\n" +
            "       o.client_id,\n" +
            "       c.name,\n" +
            "       c.surname,\n" +
            "       c.patronymic,\n" +
            "       o.consignee,\n" +
            "       o.description,\n" +
            "       o.distance,\n" +
            "       o.length,\n" +
            "       o.height,\n" +
            "       o.width,\n" +
            "       o.weight,\n" +
            "       o.volume,\n" +
            "       o.fare,\n" +
            "       ssd_ua.shipping_status_id                                     AS status_id,\n" +
            "       ssd_ua.description                                            AS status_ua,\n" +
            "       ssd_en.description                                            AS status_en,\n" +
            "       o.delivery_date\n" +
            "FROM `order` o\n" +
            "         JOIN description_locality sa_ua ON sa_ua.locality_id = o.shipping_address\n" +
            "         JOIN description_locality sa_en ON sa_en.locality_id = o.shipping_address\n" +
            "         JOIN description_locality da_ua ON da_ua.locality_id = o.delivery_address\n" +
            "         JOIN description_locality da_en ON da_en.locality_id = o.delivery_address\n" +
            "         JOIN client c on o.client_id = c.id\n" +
            "         JOIN shipping_status_description ssd_ua on o.shipping_status_id = ssd_ua.shipping_status_id\n" +
            "         JOIN shipping_status_description ssd_en on o.shipping_status_id = ssd_en.shipping_status_id\n" +
            "WHERE sa_ua.language_id = 2\n" +
            "  AND sa_en.language_id = 1\n" +
            "  AND da_ua.language_id = 2\n" +
            "  AND da_en.language_id = 1\n" +
            "  AND ssd_ua.language_id = 2\n" +
            "  AND ssd_en.language_id = 1\n" +
            "  AND o.%s\n" +
            "  AND client_id = ?\n" +
            "ORDER BY %s\n" +
            "LIMIT ?,?";

    public static final String SQL_QUERY__SELECT_ORDER_BEAN_BY_ID = "SELECT o.id,\n" +
            "       shipping_address,\n" +
            "       CONCAT_WS(' ', sa_ua.city, sa_ua.street, sa_ua.street_number) AS shipping_address_UA,\n" +
            "       CONCAT_WS(' ', sa_en.city, sa_en.street, sa_en.street_number) AS shipping_address_EN,\n" +
            "       delivery_address,\n" +
            "       CONCAT_WS(' ', da_ua.city, da_ua.street, da_ua.street_number) AS delivery_address_UA,\n" +
            "       CONCAT_WS(' ', da_en.city, da_en.street, da_en.street_number) AS delivery_address_EN,\n" +
            "       o.creation_time,\n" +
            "       o.client_id,\n" +
            "       c.name,\n" +
            "       c.surname,\n" +
            "       c.patronymic,\n" +
            "       o.consignee,\n" +
            "       o.description,\n" +
            "       o.distance,\n" +
            "       o.length,\n" +
            "       o.height,\n" +
            "       o.width,\n" +
            "       o.weight,\n" +
            "       o.volume,\n" +
            "       o.fare,\n" +
            "       ssd_ua.shipping_status_id                                     AS status_id,\n" +
            "       ssd_ua.description                                            AS status_ua,\n" +
            "       ssd_en.description                                            AS status_en,\n" +
            "       o.delivery_date\n" +
            "FROM `order` o\n" +
            "         JOIN description_locality sa_ua ON sa_ua.locality_id = o.shipping_address\n" +
            "         JOIN description_locality sa_en ON sa_en.locality_id = o.shipping_address\n" +
            "         JOIN description_locality da_ua ON da_ua.locality_id = o.delivery_address\n" +
            "         JOIN description_locality da_en ON da_en.locality_id = o.delivery_address\n" +
            "         JOIN client c on o.client_id = c.id\n" +
            "         JOIN shipping_status_description ssd_ua on o.shipping_status_id = ssd_ua.shipping_status_id\n" +
            "         JOIN shipping_status_description ssd_en on o.shipping_status_id = ssd_en.shipping_status_id\n" +
            "WHERE sa_ua.language_id = 2\n" +
            "  AND sa_en.language_id = 1\n" +
            "  AND da_ua.language_id = 2\n" +
            "  AND da_en.language_id = 1\n" +
            "  AND ssd_ua.language_id = 2\n" +
            "  AND ssd_en.language_id = 1\n" +
            "  AND o.id = ?";

    public static final String SQL_QUERY__ORDER_COUNT_ALL_ORDERS = "SELECT COUNT(*) FROM `order` " +
            "WHERE (fare BETWEEN ? AND ?)";

    public static final String SQL_QUERY__ORDER_COUNT_ALL_ORDERS_WITH_FILTER = "SELECT COUNT(*)\n" +
            "FROM `order`\n" +
            "WHERE (fare BETWEEN ? AND ?) \n" +
            " %s";


    public static final String SQL_QUERY__ORDER_COUNT_USER_ORDERS = "SELECT COUNT(*) FROM `order` WHERE client_id=?";

    public static final String SQL_QUERY__ORDER_COUNT_USER_ORDERS_FILTER = "SELECT COUNT(*) FROM `order` " +
            "WHERE client_id=? AND %s";


    //INVOICE
    public static final String SQL_QUERY__INVOICE_INSERT = "INSERT INTO `delivery`.`invoice` (id, client_id, " +
            "creation_datetime, order_id, sum, invoice_status_id) VALUES (DEFAULT,?,?,?,?,?)";

    public static final String SQL_QUERY__INVOICE_UPDATE = "UPDATE `delivery`.`invoice` SET client_id =?, " +
            "creation_datetime=?, order_id=?, sum=?, invoice_status_id=? WHERE id=?";

    public static final String SQL_QUERY__INVOICE_SELECT_BY_ID = "SELECT id, client_id, creation_datetime, order_id, " +
            "sum, invoice_status_id FROM `delivery`.`invoice` WHERE id=?";

    public static final String SQL_QUERY__INVOICE_EXIST_BY_ID = "SELECT id FROM `delivery`.`invoice` WHERE id=?";

    public static final String SQL_QUERY__INVOICE_SELECT_ALL = "SELECT id, client_id, creation_datetime, order_id, sum, " +
            "invoice_status_id FROM `delivery`.`invoice`";

    public static final String SQL_QUERY__INVOICE_SELECT_ALL_WITH_STATUS = "SELECT id, client_id, creation_datetime, " +
            "order_id, sum, invoice_status_id FROM `delivery`.`invoice`\n" +
            "WHERE  (sum BETWEEN ? AND ?) AND invoice_status_id =? ORDER BY %s LIMIT ?,?";

    public static final String SQL_QUERY__INVOICE_SELECT_ALL_WITH_STATUS_AND_DATE = "SELECT id, client_id, creation_datetime, " +
            "order_id, sum, invoice_status_id FROM `delivery`.`invoice` WHERE  (sum BETWEEN ? AND ?) " +
            "AND invoice_status_id =? AND (DATE(`creation_datetime`) BETWEEN ? AND ?) ORDER BY %s LIMIT ?,?";

    public static final String SQL_QUERY__INVOICE_SELECT_ALL_WITH_DATE = "SELECT id, client_id, " +
            "creation_datetime, order_id, sum, invoice_status_id FROM `delivery`.`invoice`\n" +
            "WHERE  (sum BETWEEN ? AND ?) AND (DATE(`creation_datetime`) BETWEEN ? AND ?) " +
            "ORDER BY %s LIMIT ?,?";

    public static final String SQL_QUERY__INVOICE_SELECT_ALL_WITH_FILTER = "SELECT id, client_id, creation_datetime, " +
            "order_id, sum, invoice_status_id FROM `delivery`.`invoice`WHERE `sum` BETWEEN ? AND ? ORDER BY %s LIMIT ?,?";

    public static final String SQL_QUERY__INVOICE_DELETE_BY_ID = "DELETE FROM `delivery`.`invoice` WHERE id=?";

    public static final String SQL_QUERY__INVOICE_SELECT_CLIENT_INVOICES = "SELECT id, client_id, " +
            "creation_datetime, order_id, sum, invoice_status_id FROM `delivery`.`invoice` WHERE client_id=?";

    public static final String SQL_QUERY__INVOICE_UPDATE_SET_PAY_STATUS_BY_ID = "UPDATE `delivery`.`invoice` " +
            "SET invoice_status_id=1 WHERE id=?";

    public static final String SQL_QUERY__INVOICE_COUNT_CLIENT_NOT_PAID_INVOICES = "SELECT COUNT(*) FROM `invoice` " +
            "WHERE client_id=? AND invoice_status_id=0";

    public static final String SQL_QUERY__INVOICE_SELECT_MAX_SUM = "SELECT MAX(sum) FROM `delivery`.`invoice`";

    public static final String SQL_QUERY__INVOICE_SELECT_CLIENT_INVOICES_BY_STATUS_ID = "SELECT id, client_id, " +
            "creation_datetime, order_id, sum, invoice_status_id FROM `delivery`.`invoice` WHERE client_id=? AND " +
            "invoice_status_id=?";

    public static final String SQL_QUERY__ORDER_COUNT_INVOICES = "SELECT COUNT(*) FROM `delivery`.`invoice`";

    public static final String SQL_QUERY__ORDER_COUNT_INVOICES_WITH_STATUS = "SELECT COUNT(*) FROM `delivery`.`invoice` " +
            "WHERE (`sum` BETWEEN ? AND ?) AND `invoice_status_id`=?";

    public static final String SQL_QUERY__ORDER_COUNT_INVOICES_WITH_DATE = "SELECT COUNT(*) FROM `delivery`.`invoice` " +
            "WHERE (`sum` BETWEEN ? AND ?) AND (DATE(`creation_datetime`) BETWEEN ? AND ?) ";

    public static final String SQL_QUERY__ORDER_COUNT_INVOICES_WITH_STATUS_AND_DATE = "SELECT COUNT(*) FROM `delivery`.`invoice` " +
            "WHERE (sum BETWEEN ? AND ?) AND invoice_status_id =? AND (DATE(`creation_datetime`) BETWEEN ? AND ?)";

    //SHIPPING_STATUS

    public static final String SQL_QUERY__SHIPPING_STATUS_INSERT = "INSERT INTO delivery.`shipping_status` (id, name) VALUES (DEFAULT,?)";

    public static final String SQL_QUERY__SHIPPING_STATUS_UPDATE = "UPDATE delivery.`shipping_status` SET name=? WHERE id=?";

    public static final String SQL_QUERY__SHIPPING_STATUS_SELECT_BY_ID = "SELECT id, name from delivery.`shipping_status` WHERE id=?";

    public static final String SQL_QUERY__SHIPPING_STATUS_EXIST = "SELECT id FROM delivery.`shipping_status` WHERE id=?";

    public static final String SQL_QUERY__SHIPPING_STATUS_SELECT_ALL = "select id, name from delivery.`shipping_status`";

    public static final String SQL_QUERY__SHIPPING_STATUS_DELETE = "DELETE FROM delivery.`shipping_status` WHERE id=?";

    public static final String SQL_QUERY__SHIPPING_STATUS_SELECT_TRANSLATE_BY_STATUS_ID = "SELECT description FROM delivery.`shipping_status_description` " +
            "WHERE shipping_status_id = ? AND language_id=?";


    private SqlQuery() {
    }
}
