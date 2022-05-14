package com.epam.delivery.web.command;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.ClientDao;
import com.epam.delivery.db.entities.Client;
import com.epam.delivery.db.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class EditClientCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String PARAM_NAME = "name";
    private static final String PARAM_SURNAME = "surname";
    private static final String PARAM_PATRONYMIC = "patronymic";
    private static final String PARAM_EMAIL = "email";
    private static final String PARAM_PHONE = "phone";
    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_CLIENT = "client";

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

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_USER);
        logger.trace("Get session attribute: user --> " + user);

        try {
            String name = request.getParameter(PARAM_NAME);
            logger.trace("Request parameter: name --> " + name);

            String surname = request.getParameter(PARAM_SURNAME);
            logger.trace("Request parameter: surname --> " + surname);

            String patronymic = request.getParameter(PARAM_PATRONYMIC);
            logger.trace("Request parameter: patronymic --> " + patronymic);

            String email = request.getParameter(PARAM_EMAIL);
            logger.trace("Request parameter: email --> " + email);

            String phone = request.getParameter(PARAM_PHONE);
            logger.trace("Request parameter: phone --> " + phone);

            Client client = (Client) session.getAttribute(ATTRIBUTE_CLIENT);
            logger.trace("Get session attribute: client --> " + client);

            ConnectionBuilder builder = new ConnectionPool();
            ClientDao clientDao = new ClientDao(builder);
            if (!client.getName().equals(name)) client.setName(name);
            if (patronymic != null && !client.getPatronymic().equals(patronymic)) client.setPatronymic(patronymic);
            if (!client.getEmail().equals(email)) client.setEmail(email);
            if (!client.getPhone().equals(phone)) client.setPhone(phone);

            if (clientDao.update(client)) {
                session.setAttribute(ATTRIBUTE_CLIENT, client);
                logger.trace("Set session attribute: client --> " + client);

                String message = "successful"; //replace
                request.getServletContext().setAttribute("message", message);
                logger.trace("Set servlet context attribute: message --> " + "successful");
            } else {
                String message = "unknown error"; //replace
                request.getServletContext().setAttribute("message", message);
                logger.trace("Set servlet context attribute: message --> " + "unknown error");
            }
            forward = Path.PAGE__CLIENT_PAGE;

        } catch (Exception exception) {
            logger.error(exception.getMessage());
        }

        logger.debug("Command finished");
        return forward;
    }
}
