package com.epam.delivery.web.command.clientCommand;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.ClientDao;
import com.epam.delivery.db.entities.Client;
import com.epam.delivery.service.MessageBuilder;
import com.epam.delivery.web.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.delivery.service.MessageBuilder.getLocaleMessage;

public class RechargeCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String PARAM_CLIENT = "client";
    private static final String PARAM_SUM = "sum";

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
        String forward = Path.PAGE__ERROR_PAGE;
        String message;
        HttpSession session = request.getSession();

        try {
            Client client = (Client) session.getAttribute(PARAM_CLIENT);
            logger.trace("Get session attribute: client --> " + client);

            double plusBalance = Double.parseDouble(request.getParameter(PARAM_SUM));
            logger.trace("Get request parameter: sum --> " + plusBalance);

            ClientDao clientDao = new ClientDao(new ConnectionPool());

            double newBalance = plusBalance + client.getBalance();
            client.setBalance(newBalance);

            if (clientDao.update(client)) {
                forward = Path.COMMAND__VIEW_CLIENT_PAGE;
                message = MessageBuilder.getLocaleMessage(session, "registration_new_admin.jsp.message.successful");
                request.getServletContext().setAttribute("message", message);
                logger.trace("Set servlet context attribute: message --> " + message);
            } else {
                message = getLocaleMessage(session, "error_message_unknown");
                request.getServletContext().setAttribute("errorMessage", message);
                logger.trace("Set servlet context attribute: errorMessage --> " + message);
            }
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
