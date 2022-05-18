package com.epam.delivery.web.command;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.AdminDao;
import com.epam.delivery.db.doa.impl.UserDao;
import com.epam.delivery.db.entities.Admin;
import com.epam.delivery.db.entities.Role;
import com.epam.delivery.db.entities.User;
import com.epam.delivery.service.PasswordEncoder;
import com.epam.delivery.service.ValidateInput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.delivery.Path.COMMAND__VIEW_REG_NEW_ADMIN;
import static com.epam.delivery.service.MessageBuilder.getLocaleMessage;

public class RegNewAdminCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    private static final String NAME_REGEX = "^[a-zA-Zа-яА-Я]+$";
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,12}$";
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_CONFIRM_PASSWORD = "confirm_password";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_SURNAME = "surname";
    private static final String MESSAGE_TAKEN_LOGIN = "registration_new_admin.jsp.message.taken_login";
    private static final String MESSAGE_INVALID_PASS = "registration_new_admin.jsp.message.invalid_pass";
    private static final String MESSAGE_INVALID_NAME = "registration_new_admin.jsp.message.invalid_name";
    private static final String MESSAGE_INVALID_SURNAME = "registration_new_admin.jsp.message.invalid_surname";
    private static final String PARAM_MESSAGE = "message";
    private static final String MESSAGE_UNKNOWN_ERROR = "registration_new_admin.jsp.message.unknown_error";
    private static final String MESSAGE_SUCCESSFUL = "registration_new_admin.jsp.message.successful";

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
        String errorMessage;
        String forward = COMMAND__VIEW_REG_NEW_ADMIN;
        HttpSession session = request.getSession(false);
        ServletContext context = request.getServletContext();

        try {
            ConnectionBuilder connectionBuilder = new ConnectionPool();

            String login = request.getParameter(PARAM_LOGIN);
            logger.trace("Get request parameter: login --> " + login);

            String password = request.getParameter(PARAM_PASSWORD);
            String confirm_password = request.getParameter(PARAM_CONFIRM_PASSWORD);

            String name = request.getParameter(PARAM_NAME);
            logger.trace("Get request parameter: name --> " + name);

            String surname = request.getParameter(PARAM_SURNAME);
            logger.trace("Get request parameter: surname --> " + surname);

            //check if exist login
            UserDao userDao = new UserDao(connectionBuilder);
            if (userDao.existsByLogin(login)) {
                errorMessage = getLocaleMessage(session, MESSAGE_TAKEN_LOGIN) + login;
                context.setAttribute(PARAM_MESSAGE, errorMessage);
                logger.trace("Set servlet context attribute: message --> " + errorMessage);
                return forward;
            }

            //check password
            if (!ValidateInput.isValid(password, PASSWORD_REGEX) && !password.equals(confirm_password)) {
                errorMessage = getLocaleMessage(session, MESSAGE_INVALID_PASS);
                context.setAttribute(PARAM_MESSAGE, errorMessage);
                logger.trace("Set servlet context attribute: message --> " + errorMessage);
                return forward;
            }

            //check name
            if (!ValidateInput.isValid(name, NAME_REGEX)) {
                errorMessage = getLocaleMessage(session, MESSAGE_INVALID_NAME);
                context.setAttribute(PARAM_MESSAGE, errorMessage);
                logger.trace("Set servlet context attribute: message --> " + errorMessage);
                return forward;
            }

            //check surname
            if (!ValidateInput.isValid(surname, NAME_REGEX)) {
                errorMessage = getLocaleMessage(session, MESSAGE_INVALID_SURNAME);
                context.setAttribute(PARAM_MESSAGE, errorMessage);
                logger.trace("Set servlet context attribute: message --> " + errorMessage);
                return forward;
            }

            User user = new User(login, PasswordEncoder.getHash(password), Role.ADMIN.ordinal());
            Admin admin = new Admin(name, surname);
            AdminDao adminDao = new AdminDao(connectionBuilder);

            if (!adminDao.insert(user, admin)) {
                errorMessage = getLocaleMessage(session, MESSAGE_UNKNOWN_ERROR);
                context.setAttribute(PARAM_MESSAGE, errorMessage);
                logger.trace("Set servlet context attribute: message --> " + errorMessage);

                forward = Path.PAGE__ERROR_PAGE;
                return forward;
            }

            String message = getLocaleMessage(session, MESSAGE_SUCCESSFUL);
            context.setAttribute(PARAM_MESSAGE, message);
            logger.trace("Set servlet context attribute: message --> " + message);

        } catch (Exception exception) {
            errorMessage = "problem with input type";
            context.setAttribute(PARAM_MESSAGE, errorMessage);
            logger.trace("Set servlet context attribute: message --> " + errorMessage);
            forward = Path.PAGE__ERROR_PAGE;
        }

        logger.debug("Command finished");
        return forward;
    }
}
