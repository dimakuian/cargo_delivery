package com.epam.delivery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Path {

    /*
     * Path holder (jsp pages, controller commands).
     */
    private Path() {
    }

    // pages
    public static final String PAGE__ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
    public static final String PAGE__CREATE_ORDER = "/WEB-INF/jsp/client/create_order.jsp";
    public static final String PAGE__CLIENT_CABINET = "/WEB-INF/jsp/client/client_cabinet.jsp";
    public static final String PAGE__COUNT_COAST = "/WEB-INF/jsp/calculate_сost.jsp";
    public static final String PAGE__REGISTRATION = "/WEB-INF/jsp/registration.jsp";
    public static final String PAGE__EDIT_ORDER = "/edit_order.jsp";
    public static final String PAGE__ADMIN_CABINET = "/WEB-INF/jsp/admin/admin_cabinet.jsp";
    public static final String PAGE__ADMIN_VIEW_ORDER = "/WEB-INF/jsp/admin/admin_view_order.jsp";
    public static final String PAGE__CLIENT_VIEW_ORDER = "/WEB-INF/jsp/client/client_view_order.jsp";
    public static final String PAGE__CLIENT_VIEW_INVOICES = "/WEB-INF/jsp/client/client_view_invoices.jsp";



    // commands
    public static final String COMMAND__LOGIN = "/controller?command=login";
    public static final String COMMAND__LOGOUT = "/controller?command=logout";
    public static final String COMMAND__VIEW_REGISTRATION_PAGE = "/controller?command=viewRegistrationPage";
    public static final String COMMAND__REGISTRATION = "/controller?command=registration";
    public static final String COMMAND__CALCULATE_COAST = "/controller?command=calculateCost";
    public static final String COMMAND__VIEW_CALCULATE_COAST = "/controller?command=viewCalculateCost";
    public static final String COMMAND__SET_LOCALE = "/controller?command=setLocale";

    public static final String COMMAND_CLIENT_CABINET = "/controller?command=clientCabinet";
    public static final String COMMAND__EDIT_USER = "/controller?command=editUser";
    public static final String COMMAND__RECHARGE = "/controller?command=recharge";
    public static final String COMMAND__PAY_ORDER = "/controller?command=payOrder";
    public static final String COMMAND__EDIT_ORDER = "/controller?command=editOrder";
    public static final String COMMAND__CREATE_ORDER = "/controller?command=createOrder";
    public static final String COMMAND__VIEW_CREATE_ORDER_PAGE = "/controller?command=viewCreateOrderPage";

    public static final String COMMAND__ADMIN_CABINET = "/controller?command=adminCabinet";

}
