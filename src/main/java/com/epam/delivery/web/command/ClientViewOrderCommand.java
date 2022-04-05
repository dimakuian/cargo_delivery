package com.epam.delivery.web.command;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.OrderDao;
import com.epam.delivery.db.entities.Admin;
import com.epam.delivery.db.entities.Client;
import com.epam.delivery.db.entities.bean.OrderBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

public class ClientViewOrderCommand implements Command {

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
        String message;

        HttpSession session = request.getSession();
        Client client = (Client) session.getAttribute("client");
        logger.trace("Get session attribute: client --> " + client);

        long orderID = Long.parseLong(request.getParameter("orderID"));
        logger.trace("Request parameter: orderID --> " + orderID);

        ConnectionBuilder builder = new ConnectionPool();
        OrderDao orderDao = new OrderDao(builder);
        OrderBean order = orderDao.findOrderBeanById(orderID).orElse(null);
        logger.trace("Found in DB: order --> " + order);

        ServletContext context = request.getServletContext();

        if (client == null || order == null) {
            message = "can't found admin or order";//replace local
            context.setAttribute("message", message);
            return forward;
        }

        context.setAttribute("orderBean", order);
        forward = Path.PAGE__CLIENT_VIEW_ORDER;

        logger.debug("Command finished");
        return forward;
    }
}
