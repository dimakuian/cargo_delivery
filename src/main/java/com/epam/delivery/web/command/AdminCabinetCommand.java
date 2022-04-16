package com.epam.delivery.web.command;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.AdminDao;
import com.epam.delivery.db.doa.impl.OrderDao;
import com.epam.delivery.db.entities.Admin;
import com.epam.delivery.db.entities.Role;
import com.epam.delivery.db.entities.User;
import com.epam.delivery.db.entities.bean.OrderBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

                int page = 1;
                int recordsPerPage = 6;
                if (request.getParameter("page_number") != null)
                    page = Integer.parseInt(request.getParameter("page_number"));

                String sort = "status_id ASC";
                if (request.getParameter("sort") != null)
                    sort = request.getParameter("sort");

                OrderDao orderDao = new OrderDao(builder);

                // get orders list
                List<OrderBean> orders = orderDao.findAllOrderBean((page - 1) * recordsPerPage, recordsPerPage, sort);

                int noOfRecords = orderDao.getNoOfAllOrders(null); //replace
                int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
                request.setAttribute("noOfPages", noOfPages);
                request.setAttribute("currentPage", page);
                request.setAttribute("currentSort", sort);

                session.setAttribute("admin", admin);
                logger.trace("Set the session attribute: admin --> " + admin);

                //put orders list to the request
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
