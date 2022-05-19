package com.epam.delivery.web.command;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.AdminDao;
import com.epam.delivery.db.doa.impl.OrderDao;
import com.epam.delivery.db.entities.Admin;
import com.epam.delivery.db.entities.User;
import com.epam.delivery.db.entities.bean.OrderBean;
import com.epam.delivery.service.FilterBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.epam.delivery.service.EmptyParameterChecker.notEmpty;

public class AdminOrdersCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final int DEFAULT_RECORDS_PER_PAGE = 10;
    private static final String PARAM_PAGE_NUMBER = "page_number";
    private static final String PARAM_SORT = "sort";
    private static final String PARAM_NO_OF_PAGES = "noOfPages";
    private static final String PARAM_CURRENT_PAGE = "currentPage";
    private static final String PARAM_ADMIN = "admin";
    private static final String PARAM_MESSAGE = "message";
    private static final String PARAM_USER = "user";
    private static final String PARAM_RECORD_PER_PAGE = "recordPerPage";
    private static final String DEFAULT_SORT = "id ASC";
    private static final String SET_THE_REQUEST_ATTR_MESSAGE = "Set the request attribute: %s --> %s";
    private static final String ATTR_ALL_ORDERS = "allOrders";
    private static final String SET_THE_SESSION_ATTRIBUTE_MESSAGE = "Set the session attribute: %s --> %s";
    public static final int DEFAULT_FROM_SUM = 0;
    public static final String PARAM_FROM_SUM = "fromSum";
    public static final String REQUEST_PARAMETER_MESSAGE = "Request parameter: %s --> %s";
    public static final String PARAM_TO_SUM = "toSum";
    private static final String PARAM_STATUS_ID = "statusID";
    private static final String PARAM_FROM_DATE = "fromDate";
    private static final String PARAM_TO_DATE = "toDate";
    public static final String PARAM_DELIVERY_ADDRESS = "delivery_address";
    public static final String PARAM_SHIPPING_ADDRESS = "shipping_address";

    /**
     * Execution method for command.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return Address to go once the command is executed.
     */

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("start command");

        String forward;
        String errorMessage;

        int page = 1;
        int recordsPerPage = DEFAULT_RECORDS_PER_PAGE;

        String sort = DEFAULT_SORT;
        double fromSum = DEFAULT_FROM_SUM;
        double toSum;
        try {

            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute(PARAM_USER);
            logger.trace("Get session attribute: user --> " + user);

            ConnectionBuilder builder = new ConnectionPool();
            AdminDao adminDao = new AdminDao(builder);
            OrderDao orderDao = new OrderDao(builder);

            Admin admin = adminDao.getByUserId(user.getId()).orElse(null);
            logger.trace("Found in DB: admin --> " + admin);

            if (notEmpty(request, PARAM_RECORD_PER_PAGE))
                recordsPerPage = Integer.parseInt(request.getParameter(PARAM_RECORD_PER_PAGE));

            if (notEmpty(request, PARAM_PAGE_NUMBER))
                page = Integer.parseInt(request.getParameter(PARAM_PAGE_NUMBER));

            if (notEmpty(request, PARAM_SORT))
                sort = request.getParameter(PARAM_SORT);


            if (notEmpty(request, PARAM_FROM_SUM) && notEmpty(request, PARAM_TO_SUM)) {
                fromSum = Double.parseDouble(request.getParameter(PARAM_FROM_SUM));
                logger.trace(String.format(REQUEST_PARAMETER_MESSAGE, PARAM_FROM_SUM, fromSum));

                toSum = Double.parseDouble(request.getParameter(PARAM_TO_SUM));
                logger.trace(String.format(REQUEST_PARAMETER_MESSAGE, PARAM_TO_SUM, toSum));
            } else {
                toSum = orderDao.getMaxOrdersFare();
                logger.trace("Found in DB: toSum --> " + toSum);
            }


            List<OrderBean> orders;
            int noOfRecords;
            if (notEmpty(request, PARAM_FROM_DATE) || notEmpty(request, PARAM_TO_DATE)
                    || notEmpty(request, PARAM_STATUS_ID) || notEmpty(request, PARAM_DELIVERY_ADDRESS)
                    || notEmpty(request, PARAM_SHIPPING_ADDRESS)) {

                String startDayStr = request.getParameter(PARAM_FROM_DATE);
                logger.trace("Request parameter: fromDate --> " + startDayStr);


                String endDate = request.getParameter(PARAM_TO_DATE);
                logger.trace("Request parameter: toDate --> " + endDate);

                if (notEmpty(request, PARAM_DELIVERY_ADDRESS)) {
                    long deliveryID = Long.parseLong(request.getParameter(PARAM_DELIVERY_ADDRESS));
                    logger.trace("Request parameter: delivery_address --> " + deliveryID);

                    request.setAttribute(PARAM_DELIVERY_ADDRESS, deliveryID);
                    logger.trace("Set the request attribute: toDate --> " + deliveryID);
                }

                if (notEmpty(request, PARAM_SHIPPING_ADDRESS)) {
                    long shippingID = Long.parseLong(request.getParameter(PARAM_SHIPPING_ADDRESS));
                    logger.trace("Request parameter: shipping_address --> " + shippingID);

                    request.setAttribute(PARAM_SHIPPING_ADDRESS, shippingID);
                    logger.trace("Set the request attribute: toDate --> " + shippingID);
                }

                if (notEmpty(request, PARAM_STATUS_ID)) {
                    long statusID = Long.parseLong(request.getParameter(PARAM_STATUS_ID));
                    logger.trace("Request parameter: statusID --> " + statusID);

                    request.setAttribute(PARAM_STATUS_ID, statusID);
                    logger.trace("Set the request attribute: toDate --> " + statusID);
                }

                String filter = FilterBuilder.buildFilter(request);
                System.out.println(filter);
                orders = orderDao.findAllOrderBean((page - 1) * recordsPerPage, recordsPerPage, sort,
                        fromSum, toSum, filter);
                noOfRecords = orderDao.getNoOfAllOrders(fromSum, toSum, filter);

            } else {
                orders = orderDao.findAllOrderBean((page - 1) * recordsPerPage, recordsPerPage, sort,
                        fromSum, toSum);
                noOfRecords = orderDao.getNoOfAllOrders(fromSum, toSum);
            }

            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

            request.setAttribute(PARAM_NO_OF_PAGES, noOfPages);
            logger.trace(String.format(SET_THE_REQUEST_ATTR_MESSAGE, PARAM_NO_OF_PAGES, noOfPages));

            request.setAttribute(PARAM_CURRENT_PAGE, page);
            logger.trace(String.format(SET_THE_REQUEST_ATTR_MESSAGE, PARAM_CURRENT_PAGE, page));

            request.setAttribute(PARAM_SORT, sort);
            logger.trace(String.format(SET_THE_REQUEST_ATTR_MESSAGE, PARAM_SORT, sort));

            request.setAttribute(PARAM_FROM_SUM, fromSum);
            logger.trace(String.format(SET_THE_REQUEST_ATTR_MESSAGE, PARAM_FROM_SUM, fromSum));

            request.setAttribute(PARAM_TO_SUM, toSum);
            logger.trace(String.format(SET_THE_REQUEST_ATTR_MESSAGE, PARAM_TO_SUM, toSum));

            request.setAttribute(PARAM_RECORD_PER_PAGE, recordsPerPage);
            logger.trace(String.format(SET_THE_REQUEST_ATTR_MESSAGE, PARAM_RECORD_PER_PAGE, recordsPerPage));

            session.setAttribute(PARAM_ADMIN, admin);
            logger.trace(String.format(SET_THE_SESSION_ATTRIBUTE_MESSAGE, PARAM_ADMIN, admin));

            //put orders list to the request
            request.setAttribute(ATTR_ALL_ORDERS, orders);
            logger.trace(String.format(SET_THE_SESSION_ATTRIBUTE_MESSAGE, ATTR_ALL_ORDERS, orders));
            forward = Path.PAGE__ADMIN_ORDERS;

        } catch (Exception exception) {
            errorMessage = exception.getMessage();
            request.getServletContext().setAttribute(PARAM_MESSAGE, exception.getMessage());
            logger.error("errorMessage --> " + errorMessage);
            forward = Path.PAGE__ERROR_PAGE;
        }

        logger.debug("Command finished");
        return forward;
    }
}
