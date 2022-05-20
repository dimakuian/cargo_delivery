package com.epam.delivery.web.command.clientCommand;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.OrderDao;
import com.epam.delivery.db.entities.Client;
import com.epam.delivery.db.entities.bean.OrderBean;
import com.epam.delivery.web.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.delivery.service.MessageBuilder.getLocaleMessage;

public class ClientViewOrderCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String PARAM_CLIENT = "client";
    private static final String PARAM_ORDER_ID = "orderID";

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

            long orderID = Long.parseLong(request.getParameter(PARAM_ORDER_ID));
            logger.trace("Request parameter: orderID --> " + orderID);

            ConnectionBuilder builder = new ConnectionPool();
            OrderDao orderDao = new OrderDao(builder);
            OrderBean order = orderDao.findOrderBeanById(orderID).orElse(null);
            logger.trace("Found in DB: order --> " + order);

            ServletContext context = request.getServletContext();

            context.setAttribute("orderBean", order);
            forward = Path.PAGE__CLIENT_VIEW_ORDER;

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
