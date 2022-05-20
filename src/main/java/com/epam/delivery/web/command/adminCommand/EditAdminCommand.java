package com.epam.delivery.web.command.adminCommand;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.AdminDao;
import com.epam.delivery.db.entities.Admin;
import com.epam.delivery.db.entities.User;
import com.epam.delivery.web.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.epam.delivery.service.MessageBuilder.getLocaleMessage;


public class EditAdminCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String PARAM_NAME = "name";
    private static final String PARAM_SURNAME = "surname";
    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_ADMIN = "admin";

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

        String forward = Path.PAGE__ERROR_PAGE;
        String message;
        HttpSession session = request.getSession();

        try {
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
                message = getLocaleMessage(session, "info_message_nothing_to_change");
                request.getServletContext().setAttribute("message", message);
                logger.trace("Set servlet context attribute: message --> " + message);
                return Path.COMMAND__VIEW_ADMIN_PAGE;
            } else {
                admin.setName(name);
                admin.setSurname(surname);
            }

            AdminDao adminDao = new AdminDao(builder);
            if (adminDao.update(admin)) {
                session.setAttribute(ATTRIBUTE_ADMIN, admin);
                logger.trace("Set session attribute: admin --> " + admin);

                message = getLocaleMessage(session,"successful_change_user_parameter");
                request.getServletContext().setAttribute("message", message);
                logger.trace("Set servlet context attribute: message --> " + message);
            } else {
                message = getLocaleMessage(session, "error_message_unknown");
                request.getServletContext().setAttribute("message", message);
                logger.trace("Set servlet context attribute: message --> " + "unknown error");
            }
            forward = Path.COMMAND__VIEW_ADMIN_PAGE;
        } catch (NullPointerException nullPointerException) {
            message = getLocaleMessage(session, "error_message_can_not_read_data");
            request.setAttribute("message", nullPointerException);
            logger.error("errorMessage --> " + message);
            logger.error("Exception --> " + nullPointerException);

        } catch (Exception cannotRedoException) {
            message = getLocaleMessage(session, "error_message_unknown");
            request.setAttribute("message", message);
            logger.error("errorMessage --> " + message);
            logger.error("Exception --> " + cannotRedoException);
        }

        logger.debug("Command finished");
        return forward;
    }
}
