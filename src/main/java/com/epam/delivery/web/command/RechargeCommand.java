package com.epam.delivery.web.command;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.ClientDao;
import com.epam.delivery.db.entities.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RechargeCommand implements Command {

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
        String forward = Path.PAGE__ERROR_PAGE;

        HttpSession session = request.getSession();
        Client client = (Client) session.getAttribute("client");
        logger.trace("Get session attribute: client --> " + client);

        double plusBalance = Double.parseDouble(request.getParameter("sum"));
        logger.trace("Get request parameter: sum --> " + plusBalance);

        String message;
        if (client != null) {
            ClientDao clientDao = new ClientDao(new ConnectionPool());

            double newBalance = plusBalance + client.getBalance();
            client.setBalance(newBalance);


            if (clientDao.update(client)) {
                forward = Path.PAGE__CLIENT_EDIT_PAGE;
                message = "successful";
                request.getServletContext().setAttribute("message", message);
                logger.trace("Set servlet context attribute: message --> " + message);
            } else {
                message = "problem with account balance";
                request.getServletContext().setAttribute("errorMessage", message);
                logger.trace("Set servlet context attribute: errorMessage --> " + message);
            }
        } else {
            message = "problem with user";
            request.getServletContext().setAttribute("errorMessage", message);
            logger.trace("Set servlet context attribute: errorMessage --> " + message);
        }
        logger.debug("Command finished");
        return forward;
    }
}
