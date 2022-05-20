package com.epam.delivery.web.command.clientCommand;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.ClientDao;
import com.epam.delivery.db.doa.impl.InvoiceDao;
import com.epam.delivery.db.doa.impl.OrderDao;
import com.epam.delivery.db.entities.Client;
import com.epam.delivery.db.entities.Invoice;
import com.epam.delivery.db.entities.User;
import com.epam.delivery.db.entities.bean.OrderBean;
import com.epam.delivery.service.MessageBuilder;
import com.epam.delivery.web.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static com.epam.delivery.service.EmptyParameterChecker.notEmpty;

public class ClientOrdersCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String PARAM_USER = "user";
    private static final String PARAM_PAGE_NUMBER = "page_number";
    private static final String DEFAULT_ID = "id";
    private static final String PARAM_SORT = "sort";
    private static final String PARAM_FILTER = "filter";

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

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(PARAM_USER);
        logger.trace("Get session attribute: user --> " + user);

        String forward = Path.PAGE__ERROR_PAGE;
        String errorMessage;

        int page = 1;
        int recordsPerPage = 6;

        try {

            ConnectionBuilder connectionBuilder = new ConnectionPool();
            ClientDao clientDao = new ClientDao(connectionBuilder);
            Client client = clientDao.getByUserId(user.getId()).orElse(null);
            logger.trace("Found in DB: client --> " + client);

            if (notEmpty(request, PARAM_PAGE_NUMBER))
                page = Integer.parseInt(request.getParameter(PARAM_PAGE_NUMBER));

            String sort = DEFAULT_ID;

            if (notEmpty(request, PARAM_SORT))
                sort = request.getParameter(PARAM_SORT);

            String filter = "";
            if (notEmpty(request, PARAM_FILTER))
                filter = request.getParameter(PARAM_FILTER);


            OrderDao orderDao = new OrderDao(connectionBuilder);
            List<OrderBean> orders = orderDao.findClientOrdersBean((page - 1) * recordsPerPage,
                    recordsPerPage, sort, client.getId(), getFilter(filter));

            InvoiceDao invoiceDao = new InvoiceDao(connectionBuilder);
            ArrayList<Invoice> invoices = new ArrayList<>(invoiceDao.findClientInvoices(client.getId()));
            logger.trace("Found in DB: client's invoices --> " + invoices);

            int noOfNotPaidClientInvoices = invoiceDao.getNoOfNotPaidClientInvoices(client.getId());
            logger.trace("Found in DB: client's number invoices to pay --> " + noOfNotPaidClientInvoices);
            request.setAttribute("notPaidInvoices", noOfNotPaidClientInvoices);

            int noOfRecords = orderDao.getNoOfUserOrders(client.getId(), filter);
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            request.setAttribute("noOfPages", noOfPages);
            logger.trace("Set the servlet attribute: noOfPages --> " + noOfPages);

            request.setAttribute("currentPage", page);
            logger.trace("Set the servlet attribute: currentPage --> " + page);

            request.setAttribute("currentSort", sort);
            logger.trace("Set the servlet attribute: currentSort --> " + sort);

            request.setAttribute("currentFilter", filter);
            logger.trace("Set the servlet attribute: currentFilter --> " + filter);

            request.setAttribute("clientInvoices", invoices);
            logger.trace("Set the servlet attribute: clientInvoices --> " + invoices);

            request.setAttribute("clientOrders", orders);
            logger.trace("Set the session attribute: clientOrders --> " + orders);

            session.setAttribute("client", client);

            forward = Path.PAGE__CLIENT_ORDERS;

        } catch (NumberFormatException numberFormatException) {
            errorMessage = MessageBuilder.getLocaleMessage(session, "error_message_number");
            request.setAttribute("message", numberFormatException);
            logger.error("errorMessage --> " + errorMessage);
            logger.error("Exception --> " + numberFormatException);

        } catch (Exception cannotRedoException) {
            errorMessage = MessageBuilder.getLocaleMessage(session, "error_message_unknown");
            request.setAttribute("message", errorMessage);
            logger.error("errorMessage --> " + errorMessage);
            logger.error("Exception --> " + cannotRedoException);
        }
        logger.debug("Command finished");
        return forward;
    }

    private String getFilter(String name) {
        Map<String, String> filterMap = new LinkedHashMap<>();

        //status filter
        filterMap.put("created", "shipping_status_id=1");
        filterMap.put("paid", "shipping_status_id=2");
        filterMap.put("confirmed", "shipping_status_id=3");
        filterMap.put("preparing to ship", "shipping_status_id=4");
        filterMap.put("in the way", "shipping_status_id=5");
        filterMap.put("delivered", "shipping_status_id=6");
        filterMap.put("canceled", "shipping_status_id=7");

        //shipping address filter
        IntStream.range(1, 16).forEach(i -> filterMap.put("shipping_department" + i, "shipping_address=" + i));

        //delivery address filter
        IntStream.range(1, 16).forEach(i -> filterMap.put("delivery_department" + i, "delivery_address=" + i));

        return filterMap.getOrDefault(name, "");
    }
}


