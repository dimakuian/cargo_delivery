package com.epam.delivery.web.command;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.InvoiceDao;
import com.epam.delivery.db.doa.impl.OrderDao;
import com.epam.delivery.db.entities.Client;
import com.epam.delivery.db.entities.Invoice;
import com.epam.delivery.db.entities.InvoiceStatus;
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
            List<Invoice> allInvoices = new ArrayList<>(invoiceDao.findClientInvoices(client.getId()));
            logger.trace("Found in DB: allInvoices --> " + allInvoices);

            List<Invoice> newInvoices = new ArrayList<>(invoiceDao.findClientInvoicesByStatus(client.getId(), InvoiceStatus.CREATED));
            logger.trace("Found in DB: newInvoices --> " + newInvoices);

            OrderDao orderDao = new OrderDao(connectionBuilder);
            Map<Invoice, OrderBean> allInvoiceOrderMap = new HashMap<>();
            allInvoices.forEach(invoice -> allInvoiceOrderMap.put(invoice,
                    orderDao.findOrderBeanById(invoice.getOrderID()).get())); //replace


            Map<Invoice, OrderBean> newInvoiceOrderMap = new HashMap<>();
            newInvoices.forEach(invoice -> newInvoiceOrderMap
                    .put(invoice, orderDao.findOrderBeanById(invoice.getOrderID()).get()));

            context.setAttribute("allInvoicesMap", allInvoiceOrderMap);
            logger.trace("Set the session attribute: allInvoicesMap --> " + allInvoiceOrderMap);

            context.setAttribute("newInvoicesMap", newInvoiceOrderMap);
            logger.trace("Set the session attribute: newInvoicesMap --> " + newInvoiceOrderMap);

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
