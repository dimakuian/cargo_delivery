package com.epam.delivery.web.command;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.AdminDao;
import com.epam.delivery.db.doa.impl.OrderDao;
import com.epam.delivery.db.entities.Admin;
import com.epam.delivery.db.entities.Order;
import com.epam.delivery.db.entities.Role;
import com.epam.delivery.db.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class AdminCabinetCommand implements Command {

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

        Role role = (Role) session.getAttribute("role");

        String forward = Path.PAGE__ERROR_PAGE;
        String errorMessage;
        if (user != null) {
            ConnectionBuilder builder = new ConnectionPool();
            AdminDao adminDao = new AdminDao(builder);

            Admin admin = adminDao.getByUserId(user.getId()).orElse(null);
            logger.trace("Found in DB: admin --> " + admin);

            if (admin != null && role == Role.ADMIN) {
                OrderDao orderDao = new OrderDao(builder);
                List<Order> orders = new ArrayList<>();
                orderDao.findAll().forEach(orders::add);
                session.setAttribute("admin", admin);
                logger.trace("Set the session attribute: admin --> " + admin);

                session.setAttribute("allOrders", orders);
                logger.trace("Set the session attribute: allOrders --> " + orders);

                forward = Path.PAGE__ADMIN_CABINET;
            } else {
                errorMessage = "can't find this admin";
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
