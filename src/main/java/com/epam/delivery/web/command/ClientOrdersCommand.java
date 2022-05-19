package com.epam.delivery.web.command;

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

public class ClientOrdersCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    /**
     * Execution method for command.
     *
     * @param request
     * @param response
     * @return Address to go once the command is executed.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("start command");

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        logger.trace("Get session attribute: user --> " + user);

        String forward = Path.PAGE__ERROR_PAGE;
        String errorMessage;
        if (user != null) {
            ConnectionBuilder connectionBuilder = new ConnectionPool();
            ClientDao clientDao = new ClientDao(connectionBuilder);
            Client client = clientDao.getByUserId(user.getId()).orElse(null);
            logger.trace("Found in DB: client --> " + client);

            if (client != null) {
                int page = 1;
                int recordsPerPage = 6;
                if (request.getParameter("page_number") != null && !request.getParameter("page_number").isEmpty())
                    page = Integer.parseInt(request.getParameter("page_number"));

                String sort = "id";
                if (request.getParameter("sort") != null)
                    sort = request.getParameter("sort");

                String filter = "";
                if (request.getParameter("filter") != null)
                    filter = request.getParameter("filter");

                System.out.println(filter);

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
            } else {
                errorMessage = "can't find this client";
                request.getServletContext().setAttribute("message", errorMessage);
                logger.error("errorMessage --> " + errorMessage);
            }
        } else {
            errorMessage = "can't find this user";
            request.getServletContext().setAttribute("message", errorMessage);
            logger.error("errorMessage --> " + errorMessage);
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

        return filterMap.containsKey(name) ? filterMap.get(name) : "";
    }
}


