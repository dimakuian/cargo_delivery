package com.epam.delivery.web.command;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.ClientDao;
import com.epam.delivery.db.doa.impl.OrderDao;
import com.epam.delivery.db.entities.Client;
import com.epam.delivery.db.entities.User;
import com.epam.delivery.db.entities.bean.OrderBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class UserCabinetCommand implements Command {

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
                int recordsPerPage = 5;
                if (request.getParameter("page_number") != null)
                    page = Integer.parseInt(request.getParameter("page_number"));

                String sort = "id";
                if (request.getParameter("sort") != null)
                    sort = request.getParameter("sort");

                OrderDao orderDao = new OrderDao(connectionBuilder);
                List<OrderBean> orders = orderDao.findClientOrdersBean((page - 1) * recordsPerPage,
                        recordsPerPage, sort,client.getId());

                int noOfRecords = orderDao.getNoOfUserOrders(client.getId());
                int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
                request.setAttribute("noOfPages", noOfPages);
                request.setAttribute("currentPage", page);
                request.setAttribute("currentSort", sort);

                session.setAttribute("client", client);
                logger.trace("Set the session attribute: client --> " + client);

                session.setAttribute("clientOrders", orders);
                logger.trace("Set the session attribute: clientOrders --> " + orders);

                forward = Path.PAGE__USER_CABINET;
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
}
