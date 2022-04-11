package com.epam.delivery.db.doa;

public final class SqlQuery {

    //CLIENT
    public static final String SQL_QUERY__CLIENT_UPDATE_BALANCE = "UPDATE delivery.`client` SET balance=? WHERE id=?";

    //ORDER
    public static final String SQL_QUERY__ORDER_UPDATE_PAY_STATUS_BY_ID =
            "UPDATE delivery.`order` SET shipping_status_id=2 WHERE id=?";


    //INVOICE
    public static final String SQL_QUERY__INVOICE_INSERT = "INSERT INTO `delivery`.`invoice` (id, client_id, " +
            "creation_datetime, order_id, sum, invoice_status_id) VALUES (DEFAULT,?,?,?,?,?)";

    public static final String SQL_QUERY__INVOICE_UPDATE = "UPDATE `delivery`.`invoice` SET client_id =?, " +
            "creation_datetime=?, order_id=?, sum=?, invoice_status_id=? WHERE id=?";

    public static final String SQL_QUERY__INVOICE_SELECT_BY_ID = "SELECT id, client_id, creation_datetime, order_id, " +
            "sum, invoice_status_id FROM `delivery`.`invoice` WHERE id=?";

    public static final String SQL_QUERY__INVOICE_EXIST_BY_ID = "SELECT id FROM `delivery`.`invoice` WHERE id=?";

    public static final String SQL_QUERY__INVOICE_SELECT_ALL = "SELECT id, client_id, creation_datetime, order_id, " +
            "sum, invoice_status_id FROM `delivery`.`invoice`";

    public static final String SQL_QUERY__INVOICE_DELETE_BY_ID = "DELETE FROM `delivery`.`invoice` WHERE id=?";

    public static final String SQL_QUERY__INVOICE_SELECT_CLIENT_INVOICES_BY_ID = "SELECT id, client_id, " +
            "creation_datetime, order_id, sum, invoice_status_id FROM `delivery`.`invoice` WHERE client_id=?";

    public static final String SQL_QUERY__INVOICE_UPDATE_SET_PAY_STATUS_BY_ID = "UPDATE `delivery`.`invoice` " +
            "SET invoice_status_id=1 WHERE id=?";

    public static final String SQL_QUERY__INVOICE_COUNT_CLIENT_NOT_PAID_INVOICES = "SELECT COUNT(*) FROM `invoice` " +
            "WHERE client_id=? AND invoice_status_id=0";

    private SqlQuery() {
    }
}
