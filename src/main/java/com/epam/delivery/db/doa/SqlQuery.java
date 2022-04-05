package com.epam.delivery.db.doa;

public final class SqlQuery {

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

    private SqlQuery() {
    }
}
