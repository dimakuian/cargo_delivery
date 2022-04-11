package com.epam.delivery.web.command;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.InvoiceDao;
import com.epam.delivery.db.doa.impl.OrderDao;
import com.epam.delivery.db.entities.Client;
import com.epam.delivery.db.entities.Invoice;
import com.epam.delivery.db.entities.Order;
import com.epam.delivery.db.entities.bean.OrderBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientInvoicesCommand implements Command {
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

        HttpSession session = request.getSession(false);
        Client client = (Client) session.getAttribute("client");
        logger.trace("Get session attribute: client --> " + client);

        String forward = Path.PAGE__ERROR_PAGE;
        String errorMessage;
        ServletContext context = request.getServletContext();

        if (client != null) {
            ConnectionBuilder connectionBuilder = new ConnectionPool();
            InvoiceDao invoiceDao = new InvoiceDao(connectionBuilder);
            List<Invoice> invoices = new ArrayList<>(invoiceDao.findClientInvoices(client.getId()));
            logger.trace("Found in DB: invoices --> " + invoices);

            OrderDao orderDao = new OrderDao(connectionBuilder);
            Map<Invoice, OrderBean> invoiceOrderMap = new HashMap<>();
            for (Invoice invoice : invoices) {
                long orderID = invoice.getOrderID();
                OrderBean order = orderDao.findOrderBeanById(orderID).orElse(null);
                logger.trace("Found in DB: order --> " + order);

                if (order != null) {
                    invoiceOrderMap.put(invoice,order);
                } else {
                    errorMessage = "problem with read order"; //replace
                    context.setAttribute("message", errorMessage);
                    logger.error("errorMessage --> " + errorMessage);
                    return forward;
                }
            }

            context.setAttribute("invoicesMap", invoiceOrderMap);
            logger.trace("Set the session attribute: invoicesMap --> " + invoiceOrderMap);

            forward = Path.PAGE__CLIENT_VIEW_INVOICES;
        } else {
            errorMessage = "problem while read client invoices"; // replace

            context.setAttribute("message", errorMessage);
            logger.error("errorMessage --> " + errorMessage);
        }

        logger.debug("Command finished");
        return forward;
    }
}
