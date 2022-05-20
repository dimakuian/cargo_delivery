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

    //admin
    public static final String ADMIN__ID = "id";
    public static final String ADMIN__USER_ID = "user_id";
    public static final String ADMIN__NAME = "name";
    public static final String ADMIN__SURNAME = "surname";

    //client
    public static final String CLIENT__ID = "id";
    public static final String CLIENT__USER_ID = "user_id";
    public static final String CLIENT__NAME = "name";
    public static final String CLIENT__SURNAME = "surname";
    public static final String CLIENT__PATRONYMIC = "patronymic";
    public static final String CLIENT__EMAIL = "email";
    public static final String CLIENT__PHONE = "phone";
    public static final String CLIENT__BALANCE = "balance";

    //language
    public static final String LANGUAGE__ID = "id";
    public static final String LANGUAGE__SHORT_NAME = "short_name";
    public static final String LANGUAGE__FULL_NAME = "full_name";

    //locality
    public static final String LOCALITY__ID = "id";
    public static final String LOCALITY__NAME = "name";
    public static final String LOCALITY__LAT = "lat";
    public static final String LOCALITY__LNG = "lng";
    public static final String LOCALITY__DISTANCE_IN_KM = "distance_in_km";
    public static final String LOCALITY__BEAN_CITY = "city";
    public static final String LOCALITY__BEAN_STREET = "street";
    public static final String LOCALITY__BEAN_STREET_NUMBER = "street_number";

    //order
    public static final String ORDER__ID = "id";
    public static final String ORDER__CREATION_TIME = "creation_time";
    public static final String ORDER__CONSIGNEE = "consignee";
    public static final String ORDER__LENGTH = "length";
    public static final String ORDER__HEIGHT = "height";
    public static final String ORDER__WIDTH = "width";
    public static final String ORDER__WEIGHT = "weight";
    public static final String ORDER__VOLUME = "volume";
    public static final String ORDER__DELIVERY_DATE = "delivery_date";
    public static final String ORDER__DESCRIPTION = "description";
    public static final String ORDER__DISTANCE = "distance";
    public static final String ORDER__FARE = "fare";
    public static final String ORDER__CLIENT_ID = "client_id";
    public static final String ORDER__SHIPPING_ADDRESS = "shipping_address";
    public static final String ORDER__DELIVERY_ADDRESS = "delivery_address";
    public static final String ORDER__SHIPPING_STATUS_ID = "shipping_status_id";

    //shipping_status
    public static final String SHIPPING_STATUS__ID = "id";
    public static final String SHIPPING_STATUS__NAME = "name";

    //user
    public static final String USER__ID = "id";
    public static final String USER__LOGIN = "login";
    public static final String USER__PASSWORD = "password";
    public static final String USER__ROLE_ID = "role_id";


    private Fields() {
    }

}
