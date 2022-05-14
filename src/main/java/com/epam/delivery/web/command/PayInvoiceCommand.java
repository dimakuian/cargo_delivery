package com.epam.delivery.web.command;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.InvoiceDao;
import com.epam.delivery.db.doa.impl.OrderDao;
import com.epam.delivery.db.entities.Client;
import com.epam.delivery.db.entities.Invoice;
import com.epam.delivery.db.entities.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PayInvoiceCommand implements Command {

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

        Long invoiceID = Long.parseLong(request.getParameter("invoice"));
        logger.trace("Request parameter: invoiceID --> " + invoiceID);

        String procedure = request.getParameter("procedure");
        logger.trace("Request parameter: procedure --> " + procedure);

        ConnectionBuilder connectionBuilder = new ConnectionPool();
        OrderDao orderDao = new OrderDao(connectionBuilder);
        Order order = orderDao.findById(orderID).orElse(null);
        logger.trace("Found in DB: order --> " + order);

        InvoiceDao invoiceDao = new InvoiceDao(connectionBuilder);
        Invoice invoice = invoiceDao.findById(invoiceID).orElse(null);
        logger.trace("Found in DB: invoice --> " + invoice);

        String message = "";
        if (order != null && client != null && invoice != null && procedure != null) {
            double currentBalance = client.getBalance();

            switch (procedure.trim().toLowerCase()) {
                case "pay":
                    if (currentBalance < invoice.getSum()) {
                        message = "you don't have enough money";

                    } else if (invoiceDao.payClientInvoice(invoice, client)) {
                        order.setStatusID(2L);
                        message = "successful";
                    } else {
                        message = "unknown error";
                        request.getServletContext().setAttribute("message", message);
                        logger.trace("Set servlet context attribute: message --> " + message);
                        return forward;
                    }
                    break;
                case "decline":
                    invoice.setInvoiceStatusID(2);
                    order.setStatusID(7L);
                    invoiceDao.update(invoice);
                    break;
            }
            orderDao.update(order);
            request.getServletContext().setAttribute("message", message);
            logger.trace("Set servlet context attribute: message --> " + message);
            forward = Path.COMMAND_CLIENT_INVOICES;

        } else {
            request.getServletContext().setAttribute("errorMessage", "problem with user");
            logger.trace("Set servlet context attribute: errorMessage --> " + "problem with user");
        }
        logger.debug("Command finished");
        return forward;
    }

}
