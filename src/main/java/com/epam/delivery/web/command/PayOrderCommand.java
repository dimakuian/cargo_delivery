package com.epam.delivery.web.command;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.ClientDao;
import com.epam.delivery.db.doa.impl.OrderDao;
import com.epam.delivery.db.doa.impl.ShippingStatusDao;
import com.epam.delivery.db.entities.Client;
import com.epam.delivery.db.entities.Order;
import com.epam.delivery.db.entities.ShippingStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PayOrderCommand implements Command {

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
        HttpSession session = request.getSession();
        Client client = (Client) session.getAttribute("client");
        logger.trace("Get session attribute: client --> " + client);

        long orderID = Long.parseLong(request.getParameter("order"));
        logger.trace("Request parameter: order --> " + orderID);

        ConnectionBuilder connectionBuilder = new ConnectionPool();
        OrderDao orderDao = new OrderDao(connectionBuilder);
        Order order = orderDao.findById(orderID).orElse(null);
        logger.trace("Found in DB: order --> " + order);

        String message;
        if (order != null && client != null) {
            double currentBalance = client.getBalance();
            if (currentBalance > order.getFare()) {
                ClientDao clientDao = new ClientDao(connectionBuilder);
                client.setBalance(currentBalance - order.getFare());
                if (!clientDao.update(client)) return forward; //replace test variant

                ShippingStatusDao shippingStatusDao = new ShippingStatusDao(connectionBuilder);
                ShippingStatus paid = shippingStatusDao.findById(2L).orElse(null);
                logger.trace("Found in DB: status --> " + paid);

                order.setStatusID(paid.getId());
                if (!orderDao.update(order)) return forward; //replace test variant
                message = "successful";
            } else {
                message = "you don't have enough money";
            }
            request.getServletContext().setAttribute("message", message);
            logger.trace("Set servlet context attribute: message --> " + message);

            forward = Path.COMMAND__USER_CABINET;
        } else {
            request.getServletContext().setAttribute("errorMessage", "problem with user");
            logger.trace("Set servlet context attribute: errorMessage --> " + "problem with user");
        }
        logger.debug("Command finished");
        return forward;
    }
}
