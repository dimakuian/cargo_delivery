package com.epam.delivery.web.command;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.ClientDao;
import com.epam.delivery.db.doa.impl.OrderDao;
import com.epam.delivery.db.doa.impl.ShippingStatusDao;
import com.epam.delivery.db.entities.Admin;
import com.epam.delivery.db.entities.Client;
import com.epam.delivery.db.entities.Order;
import com.epam.delivery.db.entities.ShippingStatus;
import com.epam.delivery.service.EmailSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ChangeOrderStatusCommand implements Command {

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

        Admin admin = (Admin) session.getAttribute("admin");
        logger.trace("Get session attribute: admin --> " + admin);

        long orderID = Long.parseLong(request.getParameter("order"));
        logger.trace("Request parameter: order --> " + orderID);


        Long status_id = Long.valueOf(request.getParameter("status_id"));
        logger.trace("Request parameter: status_id --> " + status_id);

        ConnectionBuilder builder = new ConnectionPool();
        OrderDao orderDao = new OrderDao(builder);
        Order order = orderDao.findById(orderID).orElse(null);
        logger.trace("Found in DB: order --> " + order);

        ClientDao clientDao = new ClientDao(builder);
        Client client = clientDao.findById(order.getClientID()).orElse(null);
        logger.trace("Found in DB: client --> " + client);


        String message;
        if (order != null && admin != null && status_id != null && client != null) {
            ShippingStatusDao statusDao = new ShippingStatusDao(builder);

            ShippingStatus status = statusDao.findById(order.getStatusID()).orElse(null);
            ShippingStatus newStatus = statusDao.findById(status_id).orElse(null);

            if (!status.equals(newStatus)) {
                if (newStatus.getId() != 1L || newStatus.getId() != 7L) {
                    String mailSubject = "Change status"; //replace with localization field
                    String mailText = "status in your order have been changed";//replace with localization field
                    order.setStatusID(newStatus.getId());
                    if (!orderDao.update(order)) return forward; //replace, test variant
                    message = "successful";
                    EmailSender.sendMail(client.getEmail(), mailSubject, mailText);
                } else {
                    message = "you can't change this order status";
                    request.getServletContext().setAttribute("message", message);
                    logger.trace("Set servlet context attribute: message --> " + message);
                    return forward;
                }
            } else {
                message = "this order have this status now";
            }
            request.getServletContext().setAttribute("message", message);
            logger.trace("Set servlet context attribute: message --> " + message);

            forward = Path.COMMAND__ADMIN_ORDERS;
        } else {
            request.getServletContext().setAttribute("errorMessage", "problem with user");
            logger.trace("Set servlet context attribute: errorMessage --> " + "problem with user");
        }
        logger.debug("Command finished");
        return forward;
    }
}
