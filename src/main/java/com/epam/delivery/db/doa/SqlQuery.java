package com.epam.delivery.db.doa;

public final class SqlQuery {

    //CLIENT
    public static final String SQL_QUERY__CLIENT_UPDATE_BALANCE = "UPDATE delivery.`client` SET balance=? WHERE id=?";

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
            "ORDER BY %s\n" +
            "LIMIT ?,?";


    public static final String SQL_QUERY__ORDER_UPDATE_PAY_STATUS_BY_ID =
            "UPDATE delivery.`order` SET shipping_status_id=2 WHERE id=?";

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

    public static final String SQL_QUERY__ORDER_COUNT_ALL_ORDERS = "SELECT COUNT(*) FROM `order`";

    public static final String SQL_QUERY__ORDER_COUNT_ALL_ORDERS_FILTER = "SELECT COUNT(*) FROM `order` WHERE %s";

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

    public static final String SQL_QUERY__INVOICE_SELECT_ALL_WITH_PARAM = "SELECT id, client_id, creation_datetime, " +
            "order_id, sum, invoice_status_id FROM `delivery`.`invoice` LIMIT ?,?";

    public static final String SQL_QUERY__INVOICE_DELETE_BY_ID = "DELETE FROM `delivery`.`invoice` WHERE id=?";

    public static final String SQL_QUERY__INVOICE_SELECT_CLIENT_INVOICES = "SELECT id, client_id, " +
            "creation_datetime, order_id, sum, invoice_status_id FROM `delivery`.`invoice` WHERE client_id=?";

    public static final String SQL_QUERY__INVOICE_UPDATE_SET_PAY_STATUS_BY_ID = "UPDATE `delivery`.`invoice` " +
            "SET invoice_status_id=1 WHERE id=?";

    public static final String SQL_QUERY__INVOICE_COUNT_CLIENT_NOT_PAID_INVOICES = "SELECT COUNT(*) FROM `invoice` " +
            "WHERE client_id=? AND invoice_status_id=0";

    public static final String SQL_QUERY__INVOICE_SELECT_CLIENT_INVOICES_BY_STATUS_ID = "SELECT id, client_id, " +
            "creation_datetime, order_id, sum, invoice_status_id FROM `delivery`.`invoice` WHERE client_id=? AND " +
            "invoice_status_id=?";

    public static final String SQL_QUERY__ORDER_COUNT_INVOICES = "SELECT COUNT(*) FROM `delivery`.`invoice`";

    private SqlQuery() {
    }
}
