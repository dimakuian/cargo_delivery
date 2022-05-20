package com.epam.delivery.web.command;

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

import static com.epam.delivery.Path.COMMAND__VIEW_CLIENT_PAGE;
import static com.epam.delivery.Path.PAGE__ERROR_PAGE;
import static com.epam.delivery.service.MessageBuilder.getLocaleMessage;


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
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return Address to go once the command is executed.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        logger.debug("start command");

        String forward = PAGE__ERROR_PAGE;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_USER);
        logger.trace("Get session attribute: user --> " + user);
        String message;

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

            if (client.getName().equals(name) && client.getPatronymic().equals(patronymic)
                    && client.getEmail().equals(email) && client.getPhone().equals(phone)) {
                message = getLocaleMessage(session, "info_message_nothing_to_change");
                request.getServletContext().setAttribute("message", message);
                logger.trace("Set servlet context attribute: message --> " + message);
                return COMMAND__VIEW_CLIENT_PAGE;
            }

            if (!client.getName().equals(name)) client.setName(name);
            if (patronymic != null && !client.getPatronymic().equals(patronymic)) client.setPatronymic(patronymic);
            if (!client.getEmail().equals(email)) client.setEmail(email);
            if (!client.getPhone().equals(phone)) client.setPhone(phone);

            if (clientDao.update(client)) {
                session.setAttribute(ATTRIBUTE_CLIENT, client);
                logger.trace("Set session attribute: client --> " + client);

                message = getLocaleMessage(session, "successful_change_user_parameter");
                request.getServletContext().setAttribute("message", message);
                logger.trace("Set servlet context attribute: message --> " + "successful");
            } else {
                message = getLocaleMessage(session, "error_message_unknown");
                request.getServletContext().setAttribute("message", message);
                logger.trace("Set servlet context attribute: message --> " + "unknown error");
            }
            forward = COMMAND__VIEW_CLIENT_PAGE;

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
