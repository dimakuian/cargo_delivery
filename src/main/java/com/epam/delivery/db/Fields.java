package com.epam.delivery.db;

public final class Fields {

    // entities
    public static final String ENTITY__ID = "id";

    //invoice
    public static final String INVOICE__CREATION_DATETIME = "creation_datetime";
    public static final String INVOICE__CLIENT_ID = "client_id";
    public static final String INVOICE__ORDER_ID = "order_id";
    public static final String INVOICE__SUM = "sum";
    public static final String INVOICE__STATUS_ID = "invoice_status_id";

    // beans
    public static final String USER_ORDER_BEAN__ORDER_ID = "id";
    public static final String USER_ORDER_BEAN__SHIPPING_ID = "shipping_address";
    public static final String USER_ORDER_BEAN__SHIPPING_ADDRESS_UA = "shipping_address_UA";
    public static final String USER_ORDER_BEAN__SHIPPING_ADDRESS_EN = "shipping_address_EN";
    public static final String USER_ORDER_BEAN__DELIVERY_ID = "delivery_address";
    public static final String USER_ORDER_BEAN__DELIVERY_ADDRESS_UA = "delivery_address_UA";
    public static final String USER_ORDER_BEAN__DELIVERY_ADDRESS_EN = "delivery_address_EN";
    public static final String USER_ORDER_BEAN__DELIVERY_CREATION_TIME = "creation_time";
    public static final String USER_ORDER_BEAN__DELIVERY_CLIENT_ID = "client_id";
    public static final String USER_ORDER_BEAN__DELIVERY_CLIENT_NAME = "name";
    public static final String USER_ORDER_BEAN__DELIVERY_CLIENT_SURNAME = "surname";
    public static final String USER_ORDER_BEAN__DELIVERY_CLIENT_PATRONYMIC = "patronymic";
    public static final String USER_ORDER_BEAN__DELIVERY_CLIENT_CONSIGNEE = "consignee";
    public static final String USER_ORDER_BEAN__DELIVERY_CLIENT_DESCRIPTION = "description";
    public static final String USER_ORDER_BEAN__DELIVERY_CLIENT_DISTANCE = "distance";
    public static final String USER_ORDER_BEAN__DELIVERY_CLIENT_LENGTH = "length";
    public static final String USER_ORDER_BEAN__DELIVERY_HEIGHT = "height";
    public static final String USER_ORDER_BEAN__DELIVERY_WIDTH = "width";
    public static final String USER_ORDER_BEAN__DELIVERY_WEIGHT = "weight";
    public static final String USER_ORDER_BEAN__DELIVERY_VOLUME = "volume";
    public static final String USER_ORDER_BEAN__DELIVERY_FARE = "fare";
    public static final String USER_ORDER_BEAN__DELIVERY_STATUS_ID = "status_id";
    public static final String USER_ORDER_BEAN__DELIVERY_STATUS_UA = "status_ua";
    public static final String USER_ORDER_BEAN__DELIVERY_STATUS_EN = "status_en";
    public static final String USER_ORDER_BEAN__DELIVERY_DELIVERY_DATE = "delivery_date";


    private Fields() {
    }

}
