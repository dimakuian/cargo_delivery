package com.epam.delivery.web.command;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.UserDao;
import com.epam.delivery.db.entities.User;
import com.epam.delivery.service.PasswordEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ChangeUserPasswordCommand implements Command {

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

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        logger.trace("Get session attribute: user --> " + user);
        String message;

        String forward = Path.PAGE__ERROR_PAGE;
        String currPass = request.getParameter("pass");
        String newPass = request.getParameter("newPass");
        String newPassConf = request.getParameter("newPassConf");
        if (user != null) {
            if (currPass != null && !user.getPassword().equals(PasswordEncoder.getHash(currPass))) {
                message = "you enter incorrect password";//replace
            } else if (!newPass.equals(newPassConf)) {
                message = "new password not similar";//replace
            } else {
                ConnectionBuilder builder = new ConnectionPool();
                UserDao userDao = new UserDao(builder);
                user.setPassword(PasswordEncoder.getHash(newPass));
                if (!userDao.update(user)) {
                    message = "can't update password";//replace
                    request.getServletContext().setAttribute("errorMessage", message);
                    logger.trace("Set servlet context attribute: errorMessage --> " + message);
                    return forward;
                }
                message = "successful";
            }
            request.getServletContext().setAttribute("message", message);
            logger.trace("Set servlet context attribute: message --> " + message);
            forward = Path.PAGE__CLIENT_EDIT_PAGE;

        } else {
            message = "problem with user";
            request.getServletContext().setAttribute("errorMessage", message);
            logger.trace("Set servlet context attribute: errorMessage --> " + message);
        }
        logger.debug("Command finished");
        return forward;
    }
}
