package com.epam.delivery.web.command;

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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegistrationCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    public static final String EMAIL_REGEX = "^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String NAME_REGEX = "^[a-zA-Zа-яА-Я]+$";
    public static final String TEL_REGEX = "^(\\+(380){1}[0-9]{9}){1}$";
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,12}$";

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

        ConnectionBuilder connectionBuilder = new ConnectionPool();
        String login = request.getParameter("login");
        logger.trace("Get request parameter: login --> " + login);

        String password = request.getParameter("password");
        String confirm_password = request.getParameter("confirm_password");
        String name = request.getParameter("name");
        logger.trace("Get request parameter: name --> " + name);

        String surname = request.getParameter("surname");
        logger.trace("Get request parameter: surname --> " + surname);

        String patronymic = request.getParameter("patronymic");
        logger.trace("Get request parameter: patronymic --> " + patronymic);

        String email = request.getParameter("email");
        logger.trace("Get request parameter: email --> " + email);

        String tel = request.getParameter("tel");
        logger.trace("Get request parameter: tel --> " + tel);

        String errorMessage;
        String forward = Path.PAGE__ERROR_PAGE;

        if (login != null && password != null && confirm_password != null && name != null &&
                surname != null && patronymic != null && email != null && tel != null) {

            //check if exist login
            UserDao userDao = new UserDao(connectionBuilder);
            if (userDao.existsByLogin(login)) {
                errorMessage = String.format("This login is already taken: %s", login);
                request.getServletContext().setAttribute("message", errorMessage);
                logger.trace("Set servlet context attribute: message --> " + errorMessage);

                forward = Path.COMMAND__VIEW_REGISTRATION_PAGE;
                return forward;
            }

            ClientDao clientDao = new ClientDao(connectionBuilder);

            //check if exist email
            if (clientDao.existsEmail(email)) {
                System.out.println("exist mail");
                errorMessage = String.format("This email is already taken: %s", email);
                request.getServletContext().setAttribute("message", errorMessage);
                logger.trace("Set servlet context attribute: message --> " + errorMessage);

                forward = Path.COMMAND__VIEW_REGISTRATION_PAGE;
                return forward;
            }

            //check if exist phone
            if (clientDao.existsPhone(tel)) {
                System.out.println("exist tel");
                errorMessage = String.format("This phone number is already taken: %s", tel);
                request.getServletContext().setAttribute("message", errorMessage);
                logger.trace("Set servlet context attribute: message --> " + errorMessage);

                forward = Path.COMMAND__VIEW_REGISTRATION_PAGE;
                return forward;
            }

            //check password
            if (!ValidateInput.isValid(password, PASSWORD_REGEX) && !password.equals(confirm_password)) {
                errorMessage = "Passwords Don't Match";
                request.getServletContext().setAttribute("message", errorMessage);
                logger.trace("Set servlet context attribute: message --> " + errorMessage);

                forward = Path.COMMAND__VIEW_REGISTRATION_PAGE;
                return forward;
            }

            //check name
            if (!ValidateInput.isValid(name, NAME_REGEX)) {
                errorMessage = "Name isn't valid";
                request.getServletContext().setAttribute("message", errorMessage);
                logger.trace("Set servlet context attribute: message --> " + errorMessage);

                forward = Path.COMMAND__VIEW_REGISTRATION_PAGE;
                return forward;
            }

            //check surname
            if (!ValidateInput.isValid(surname, NAME_REGEX)) {
                errorMessage = "Surname isn't valid";
                request.getServletContext().setAttribute("message", errorMessage);
                logger.trace("Set servlet context attribute: message --> " + errorMessage);

                forward = Path.COMMAND__VIEW_REGISTRATION_PAGE;
                return forward;
            }

            //check patronymic
            if (!ValidateInput.isValid(patronymic, NAME_REGEX)) {
                errorMessage = "Patronymic isn't valid";
                request.getServletContext().setAttribute("message", errorMessage);
                logger.trace("Set servlet context attribute: message --> " + errorMessage);

                forward = Path.COMMAND__VIEW_REGISTRATION_PAGE;
                return forward;
            }

            //check email
            if (!ValidateInput.isValid(email, EMAIL_REGEX)) {
                errorMessage = "Email isn't valid";
                request.getServletContext().setAttribute("message", errorMessage);
                logger.trace("Set servlet context attribute: message --> " + errorMessage);

                forward = Path.COMMAND__VIEW_REGISTRATION_PAGE;
                return forward;
            }

            //check tel
            if (!ValidateInput.isValid(tel, TEL_REGEX)) {
                errorMessage = "Phone number isn't valid";
                request.getServletContext().setAttribute("message", errorMessage);
                logger.trace("Set servlet context attribute: message --> " + errorMessage);

                forward = Path.COMMAND__VIEW_REGISTRATION_PAGE;
                return forward;
            }

            User user = User.createUser(login, PasswordEncoder.getHash(password), Role.CLIENT.ordinal());
            if (!userDao.insert(user)) {
                errorMessage = "problem while insert user";
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
                errorMessage = "problem while insert client";
                request.getServletContext().setAttribute("message", errorMessage);
                logger.trace("Set servlet context attribute: message --> " + errorMessage);

                return forward;
            }
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            logger.trace("Set the session attribute: user --> " + user);

            String roleName = Role.getRole(user).getName();
            session.setAttribute("role", roleName);
            logger.trace("Set the session attribute: role --> " + roleName);

            request.getServletContext().setAttribute("message", "successful");
            logger.trace("Set servlet context attribute: message --> " + "successful");

            forward = Path.COMMAND_CLIENT_CABINET;
        } else {
            errorMessage = "problem with input type";
            request.getServletContext().setAttribute("message", errorMessage);
            logger.trace("Set servlet context attribute: message --> " + errorMessage);

            forward = Path.COMMAND__VIEW_REGISTRATION_PAGE;
        }
        logger.debug("Command finished");
        return forward;
    }
}
