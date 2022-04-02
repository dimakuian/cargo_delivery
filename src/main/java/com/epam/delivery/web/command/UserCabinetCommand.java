package com.epam.delivery.web.command;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.ClientDao;
import com.epam.delivery.db.doa.impl.OrderDao;
import com.epam.delivery.db.entities.Client;
import com.epam.delivery.db.entities.Order;
import com.epam.delivery.db.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
                OrderDao orderDao = new OrderDao(connectionBuilder);
                List<Order> orders = new ArrayList<>();
                orderDao.findAllByUserID(client.getId()).forEach(orders::add);
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
