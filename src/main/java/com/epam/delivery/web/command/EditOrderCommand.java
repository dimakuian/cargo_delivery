package com.epam.delivery.web.command;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.OrderDao;
import com.epam.delivery.db.entities.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditOrderCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    /**
     * Execution method for command.
     *
     * @param request
     * @param response
     * @return Address to go once the command is executed.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("start command");

        String forward = Path.PAGE__ERROR_PAGE;
        long orderID = Long.parseLong(request.getParameter("order"));
        logger.trace("Request parameter: order --> " + orderID);

        OrderDao orderDao = new OrderDao(new ConnectionPool());
        Order order = orderDao.findById(orderID).orElse(null);
        logger.trace("Found in DB: order --> " + order);

        if (order != null) {
            request.getServletContext().setAttribute("showOrder", order);
            logger.trace("Set servlet context attribute: showOrder --> " + order);

            forward = Path.PAGE__EDIT_ORDER;
        } else {
            request.getServletContext().setAttribute("errorMessage", "problem with order");
            logger.trace("Set servlet context attribute: errorMessage --> " + "problem with order");
        }
        logger.debug("Command finished");
        return forward;
    }
}
