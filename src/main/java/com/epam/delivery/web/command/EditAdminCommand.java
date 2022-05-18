package com.epam.delivery.web.command;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.AdminDao;
import com.epam.delivery.db.entities.Admin;
import com.epam.delivery.db.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class EditAdminCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String PARAM_NAME = "name";
    private static final String PARAM_SURNAME = "surname";
    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_ADMIN = "admin";

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

        String forward = Path.PAGE__ERROR_PAGE;

        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(ATTRIBUTE_USER);
            logger.trace("Get session attribute: user --> " + user);

            ConnectionBuilder builder = new ConnectionPool();

            String name = request.getParameter(PARAM_NAME);
            logger.trace("Request parameter: name --> " + name);

            String surname = request.getParameter(PARAM_SURNAME);
            logger.trace("Request parameter: surname --> " + surname);

            Admin admin = (Admin) session.getAttribute(ATTRIBUTE_ADMIN);
            logger.trace("Get session attribute: admin --> " + admin);
            if (admin.getName().equals(name) && admin.getSurname().equals(surname)) {
                String message = "nothing to change"; //replace
                request.getServletContext().setAttribute("message", message);
                logger.trace("Set servlet context attribute: message --> " + "successful");
                return Path.COMMAND__VIEW_ADMIN_PAGE;
            } else {
                admin.setName(name);
                admin.setSurname(surname);
            }

            AdminDao adminDao = new AdminDao(builder);
            if (adminDao.update(admin)) {
                session.setAttribute(ATTRIBUTE_ADMIN, admin);
                logger.trace("Set session attribute: admin --> " + admin);

                String message = "successful"; //replace
                request.getServletContext().setAttribute("message", message);
                logger.trace("Set servlet context attribute: message --> " + "successful");
            } else {
                String message = "unknown error"; //replace
                request.getServletContext().setAttribute("message", message);
                logger.trace("Set servlet context attribute: message --> " + "unknown error");
            }
            forward = Path.COMMAND__VIEW_ADMIN_PAGE;
        } catch (Exception exception) {
            logger.error(exception.getMessage());
        }

        logger.debug("Command finished");
        return forward;
    }
}
