package com.epam.delivery.web.command;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.AdminDao;
import com.epam.delivery.db.doa.impl.OrderDao;
import com.epam.delivery.db.entities.Admin;
import com.epam.delivery.db.entities.User;
import com.epam.delivery.db.entities.bean.OrderBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

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
    private static final String DEFAULT_SORT = "status_id ASC";
    private static final String SET_THE_REQUEST_ATTR_MESSAGE = "Set the request attribute: %s --> %s";
    private static final String ATTR_ALL_ORDERS = "allOrders";
    private static final String SET_THE_SESSION_ATTRIBUTE_MESSAGE = "Set the session attribute: %s --> %s";

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

        try {
            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute(PARAM_USER);
            logger.trace("Get session attribute: user --> " + user);

            ConnectionBuilder builder = new ConnectionPool();
            AdminDao adminDao = new AdminDao(builder);

            Admin admin = adminDao.getByUserId(user.getId()).orElse(null);
            logger.trace("Found in DB: admin --> " + admin);

            if (request.getParameter(PARAM_RECORD_PER_PAGE) != null)
                recordsPerPage = Integer.parseInt(request.getParameter(PARAM_RECORD_PER_PAGE));

            if (request.getParameter(PARAM_PAGE_NUMBER) != null)
                page = Integer.parseInt(request.getParameter(PARAM_PAGE_NUMBER));

            if (request.getParameter(PARAM_SORT) != null)
                sort = request.getParameter(PARAM_SORT);

            OrderDao orderDao = new OrderDao(builder);

            // get orders list
            List<OrderBean> orders = orderDao.findAllOrderBean((page - 1) * recordsPerPage, recordsPerPage, sort);

            int noOfRecords = orderDao.getNoOfAllOrders(null);// replace throw
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

            request.setAttribute(PARAM_NO_OF_PAGES, noOfPages);
            logger.trace(String.format(SET_THE_REQUEST_ATTR_MESSAGE, PARAM_NO_OF_PAGES, noOfPages));

            request.setAttribute(PARAM_CURRENT_PAGE, page);
            logger.trace(String.format(SET_THE_REQUEST_ATTR_MESSAGE, PARAM_CURRENT_PAGE, page));

            request.setAttribute(PARAM_SORT, sort);
            logger.trace(String.format(SET_THE_REQUEST_ATTR_MESSAGE, PARAM_SORT, sort));

            request.setAttribute(PARAM_RECORD_PER_PAGE, recordsPerPage);
            logger.trace(String.format(SET_THE_REQUEST_ATTR_MESSAGE, PARAM_RECORD_PER_PAGE, recordsPerPage));

            session.setAttribute(PARAM_ADMIN, admin);
            logger.trace(String.format(SET_THE_SESSION_ATTRIBUTE_MESSAGE, PARAM_ADMIN, admin));

            //put orders list to the request
            session.setAttribute(ATTR_ALL_ORDERS, orders);
            logger.trace(String.format(SET_THE_SESSION_ATTRIBUTE_MESSAGE, ATTR_ALL_ORDERS, orders));
            forward = Path.PAGE__ADMIN_CABINET;

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
