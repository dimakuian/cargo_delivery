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
    public static final String PAGE__CLIENT_ORDERS = "/client_orders";
    public static final String PAGE__COUNT_COAST = "/calculate_coast";
    public static final String PAGE__REGISTRATION = "/registration";
    public static final String PAGE__EDIT_ORDER = "/edit_order.jsp";
    public static final String PAGE__ADMIN_CABINET = "/WEB-INF/jsp/admin/admin_cabinet.jsp";
    public static final String PAGE__ADMIN_VIEW_ORDER = "/WEB-INF/jsp/admin/admin_view_order.jsp";
    public static final String PAGE__CLIENT_VIEW_ORDER = "/WEB-INF/jsp/client/client_view_order.jsp";
    public static final String PAGE__CLIENT_VIEW_INVOICES = "/WEB-INF/jsp/client/client_view_invoices.jsp";
    public static final String PAGE__CLIENT_PAGE = "/WEB-INF/jsp/client/client_page.jsp";
    public static final String PAGE__ADMIN_PAGE = "/WEB-INF/jsp/admin/admin_page.jsp";
    public static final String PAGE__ADMIN_INVOICES = "/WEB-INF/jsp/admin/admin_invoices.jsp";
    public static final String PAGE__REG_NEW_ADMIN = "/WEB-INF/jsp/admin/registration_new_admin.jsp";

    // commands
    public static final String COMMAND__VIEW_REGISTRATION_PAGE = "/controller?command=viewRegistrationPage";
    public static final String COMMAND__VIEW_ADMIN_PAGE = "/controller?command=viewAdminPage";
    public static final String COMMAND__SET_LOCALE = "/controller?command=setLocale";

    public static final String COMMAND_CLIENT_ORDERS = "/controller?command=clientOrders";
    public static final String COMMAND_CLIENT_INVOICES = "/controller?command=clientViewInvoices";
    public static final String COMMAND__EDIT_USER = "/controller?command=editUser";
    public static final String COMMAND__RECHARGE = "/controller?command=recharge";
    public static final String COMMAND__PAY_ORDER = "/controller?command=payOrder";
    public static final String COMMAND__EDIT_ORDER = "/controller?command=editOrder";
    public static final String COMMAND__CREATE_ORDER = "/controller?command=createOrder";
    public static final String COMMAND__VIEW_CREATE_ORDER_PAGE = "/controller?command=viewCreateOrderPage";
    public static final String COMMAND__ADMIN_CABINET = "/controller?command=adminCabinet";

}
