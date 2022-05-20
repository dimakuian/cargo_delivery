package com.epam.delivery.web.command.common;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.UserDao;
import com.epam.delivery.db.entities.User;
import com.epam.delivery.service.MessageBuilder;
import com.epam.delivery.service.PasswordEncoder;
import com.epam.delivery.web.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ChangeUserPasswordCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String ATTRIBUTE_USER = "user";
    private static final String PARAM_PASS = "pass";
    private static final String PARAM_NEW_PASS = "newPass";
    private static final String PARAM_NEW_PASS_CONF = "newPassConf";
    private static final String PARAM_PAGE = "page";

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

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_USER);
        logger.trace("Get session attribute: " + ATTRIBUTE_USER + "-- >" + user);
        String message;

        String forward = Path.PAGE__ERROR_PAGE;

        try {

            String currPass = request.getParameter(PARAM_PASS);
            logger.trace("Request parameter: pass --> " + currPass);

            String newPass = request.getParameter(PARAM_NEW_PASS);
            logger.trace("Request parameter: newPass --> " + newPass);

            String newPassConf = request.getParameter(PARAM_NEW_PASS_CONF);
            logger.trace("Request parameter: newPassConf --> " + newPassConf);

            String page = request.getParameter(PARAM_PAGE);

            if (currPass != null && !user.getPassword().equals(PasswordEncoder.getHash(currPass))) {
                message = MessageBuilder.getLocaleMessage(session, "incorrect_password_message");
            } else if (!newPass.equals(newPassConf)) {
                message = MessageBuilder.getLocaleMessage(session, "error_message_different_pass");
            } else {
                ConnectionBuilder builder = new ConnectionPool();
                UserDao userDao = new UserDao(builder);
                user.setPassword(PasswordEncoder.getHash(newPass));
                userDao.update(user);
                message = MessageBuilder.getLocaleMessage(session, "successful_update_pass_message");
            }

            request.getServletContext().setAttribute("message", message);
            logger.trace("Set servlet context attribute: message --> " + message);
            forward = page;
        } catch (NumberFormatException numberFormatException) {
            message = MessageBuilder.getLocaleMessage(session, "error_message_number");
            request.setAttribute("message", numberFormatException);
            logger.error("errorMessage --> " + message);
            logger.error("Exception --> " + numberFormatException);
        } catch (Exception cannotRedoException) {
            message = MessageBuilder.getLocaleMessage(session, "error_message_unknown");
            request.setAttribute("message", message);
            logger.error("errorMessage --> " + message);
            logger.error("Exception --> " + cannotRedoException);
        }
        logger.debug("Command finished");
        return forward;
    }
}
