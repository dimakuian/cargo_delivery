package com.epam.delivery.web.command;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.UserDao;
import com.epam.delivery.db.entities.Role;
import com.epam.delivery.db.entities.User;
import com.epam.delivery.service.PasswordEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LoginCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

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

        HttpSession session = request.getSession(true);
        // obtain login and password from the request
        String login = request.getParameter("login");
        logger.trace("Request parameter: login --> " + login);

        String password = request.getParameter("password");
        // error handler
        String errorMessage;
        String forward = Path.PAGE__ERROR_PAGE;

        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            errorMessage = "Login/password cannot be empty";
            request.getServletContext().setAttribute("errorMessage", errorMessage);
            logger.error("errorMessage --> " + errorMessage);
            return forward;
        }
        User user = new UserDao(new ConnectionPool()).getByLogin(login).orElse(null);
        if (user == null || !PasswordEncoder.getHash(password).equals(user.getPassword())) {
            errorMessage = "Cannot find user with such login/password";
            request.getServletContext().setAttribute("errorMessage", errorMessage);
            logger.error("errorMessage --> " + errorMessage);
            return forward;

        } else {
            logger.trace("Found in DB: user --> " + user);

            Role role = Role.getRole(user);
            logger.trace("userRole --> " + role);

            if (role == Role.ADMIN)
                forward = Path.COMMAND__ADMIN_ORDERS;

            if (role == Role.CLIENT)
                forward = Path.COMMAND_CLIENT_ORDERS;

            session.setAttribute("user", user);
            logger.trace("Set the session attribute: user --> " + user);

            session.setAttribute("role", role);
            logger.trace("Set the session attribute: userRole --> " + role);

            logger.info("User " + user + " logged as " + role.toString().toLowerCase());
        }
        logger.debug("Command finished");
        return forward;
    }
}
