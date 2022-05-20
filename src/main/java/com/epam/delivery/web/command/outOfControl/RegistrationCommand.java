package com.epam.delivery.web.command.outOfControl;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.ClientDao;
import com.epam.delivery.db.doa.impl.UserDao;
import com.epam.delivery.db.entities.Client;
import com.epam.delivery.db.entities.Role;
import com.epam.delivery.db.entities.User;
import com.epam.delivery.service.PasswordEncoder;
import com.epam.delivery.service.ValidateInput;
import com.epam.delivery.web.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.delivery.service.MessageBuilder.getLocaleMessage;

public class RegistrationCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    private static final String EMAIL_REGEX = "^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final String NAME_REGEX = "^[a-zA-Zа-яА-Я]+$";
    private static final String TEL_REGEX = "^(\\+(380){1}[0-9]{9}){1}$";
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,12}$";
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_CONFIRM_PASSWORD = "confirm_password";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_SURNAME = "surname";
    private static final String PARAM_PATRONYMIC = "patronymic";
    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_TEL = "tel";

    /**
     * Execution method for command.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return Address to go once the command is executed.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("start command");

        ConnectionBuilder connectionBuilder = new ConnectionPool();
        String login = request.getParameter(PARAM_LOGIN);
        logger.trace("Get request parameter: login --> " + login);

        String password = request.getParameter(PARAM_PASSWORD);
        String confirm_password = request.getParameter(PARAM_CONFIRM_PASSWORD);
        String name = request.getParameter(PARAM_NAME);
        logger.trace("Get request parameter: name --> " + name);

        String surname = request.getParameter(PARAM_SURNAME);
        logger.trace("Get request parameter: surname --> " + surname);

        String patronymic = request.getParameter(PARAM_PATRONYMIC);
        logger.trace("Get request parameter: patronymic --> " + patronymic);

        String email = request.getParameter(PARAM_EMAIL);
        logger.trace("Get request parameter: email --> " + email);

        String tel = request.getParameter(PARAM_TEL);
        logger.trace("Get request parameter: tel --> " + tel);

        String errorMessage;
        String forward = Path.PAGE__ERROR_PAGE;

        if (login != null && password != null && confirm_password != null && name != null &&
                surname != null && patronymic != null && email != null && tel != null) {

            //check if exist login
            UserDao userDao = new UserDao(connectionBuilder);
            if (userDao.existsByLogin(login)) {
                errorMessage = getLocaleMessage(request.getSession(), "message_info_login_exists");
                request.getServletContext().setAttribute("message", errorMessage + " : " + login);
                logger.trace("Set servlet context attribute: message --> " + errorMessage);

                forward = Path.COMMAND__VIEW_REGISTRATION_PAGE;
                return forward;
            }

            ClientDao clientDao = new ClientDao(connectionBuilder);

            //check if exist email
            if (clientDao.existsEmail(email)) {
                errorMessage = getLocaleMessage(request.getSession(), "message_info_email_exists");
                request.getServletContext().setAttribute("message", errorMessage + " : " + email);
                logger.trace("Set servlet context attribute: message --> " + errorMessage);

                forward = Path.COMMAND__VIEW_REGISTRATION_PAGE;
                return forward;
            }

            //check if exist phone
            if (clientDao.existsPhone(tel)) {
                errorMessage = getLocaleMessage(request.getSession(), "message_info_phone_exists");
                request.getServletContext().setAttribute("message", errorMessage + " : " + tel);
                logger.trace("Set servlet context attribute: message --> " + errorMessage);

                forward = Path.COMMAND__VIEW_REGISTRATION_PAGE;
                return forward;
            }

            //check password
            if (!ValidateInput.isValid(password, PASSWORD_REGEX) && !password.equals(confirm_password)) {
                errorMessage = getLocaleMessage(request.getSession(), "client_page.jsp.message.different_pass");
                request.getServletContext().setAttribute("message", errorMessage);
                logger.trace("Set servlet context attribute: message --> " + errorMessage);

                forward = Path.COMMAND__VIEW_REGISTRATION_PAGE;
                return forward;
            }

            //check name
            if (!ValidateInput.isValid(name, NAME_REGEX)) {
                errorMessage = getLocaleMessage(request.getSession(), "client_page.jsp.message.valid_name");
                request.getServletContext().setAttribute("message", errorMessage);
                logger.trace("Set servlet context attribute: message --> " + errorMessage);

                forward = Path.COMMAND__VIEW_REGISTRATION_PAGE;
                return forward;
            }

            //check surname
            if (!ValidateInput.isValid(surname, NAME_REGEX)) {
                errorMessage = getLocaleMessage(request.getSession(), "client_page.jsp.message.valid_surname");
                request.getServletContext().setAttribute("message", errorMessage);
                logger.trace("Set servlet context attribute: message --> " + errorMessage);

                forward = Path.COMMAND__VIEW_REGISTRATION_PAGE;
                return forward;
            }

            //check patronymic
            if (!ValidateInput.isValid(patronymic, NAME_REGEX)) {
                errorMessage = getLocaleMessage(request.getSession(), "client_page.jsp.message.valid_patronymic");
                request.getServletContext().setAttribute("message", errorMessage);
                logger.trace("Set servlet context attribute: message --> " + errorMessage);

                forward = Path.COMMAND__VIEW_REGISTRATION_PAGE;
                return forward;
            }

            //check email
            if (!ValidateInput.isValid(email, EMAIL_REGEX)) {
                errorMessage = getLocaleMessage(request.getSession(), "client_page.jsp.message.valid_email");
                request.getServletContext().setAttribute("message", errorMessage);
                logger.trace("Set servlet context attribute: message --> " + errorMessage);

                forward = Path.COMMAND__VIEW_REGISTRATION_PAGE;
                return forward;
            }

            //check tel
            if (!ValidateInput.isValid(tel, TEL_REGEX)) {
                errorMessage = getLocaleMessage(request.getSession(), "client_page.jsp.message.valid_phone");
                request.getServletContext().setAttribute("message", errorMessage);
                logger.trace("Set servlet context attribute: message --> " + errorMessage);

                forward = Path.COMMAND__VIEW_REGISTRATION_PAGE;
                return forward;
            }

            User user = new User(login, PasswordEncoder.getHash(password), Role.CLIENT.ordinal());

            if (!userDao.insert(user)) {
                errorMessage = getLocaleMessage(request.getSession(), "error_message_unknown");
                request.getServletContext().setAttribute("message", errorMessage);
                logger.trace("Set servlet context attribute: message --> " + errorMessage);

                forward = Path.COMMAND__VIEW_REGISTRATION_PAGE;
                return forward;
            }

            Client client = Client.createClient(user.getId(), name, surname);
            client.setPatronymic(patronymic);
            client.setEmail(email);
            client.setPhone(tel);
            if (!clientDao.insert(client)) {
                errorMessage = getLocaleMessage(request.getSession(), "error_message_unknown");
                request.getServletContext().setAttribute("message", errorMessage);
                logger.trace("Set servlet context attribute: message --> " + errorMessage);

                return forward;
            }
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            logger.trace("Set the session attribute: user --> " + user);

            Role role = Role.getRole(user);
            session.setAttribute("role", role);
            logger.trace("Set the session attribute: role --> " + role);

            String message = getLocaleMessage(request.getSession(), "successful_register_message");

            request.getServletContext().setAttribute("message", message);
            logger.trace("Set servlet context attribute: message --> " + message);

            forward = Path.COMMAND_CLIENT_ORDERS;
        } else {
            errorMessage = getLocaleMessage(request.getSession(), "error_message_unknown");
            request.getServletContext().setAttribute("message", errorMessage);
            logger.trace("Set servlet context attribute: message --> " + errorMessage);

            forward = Path.COMMAND__VIEW_REGISTRATION_PAGE;
        }
        logger.debug("Command finished");
        return forward;
    }
}
