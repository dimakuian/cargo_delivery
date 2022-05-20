package com.epam.delivery.web.command.adminCommand;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.ClientDao;
import com.epam.delivery.db.doa.impl.OrderDao;
import com.epam.delivery.db.doa.impl.ShippingStatusDao;
import com.epam.delivery.db.entities.Admin;
import com.epam.delivery.db.entities.Client;
import com.epam.delivery.db.entities.Order;
import com.epam.delivery.db.entities.ShippingStatus;
import com.epam.delivery.db.entities.bean.StatusDescriptionBean;
import com.epam.delivery.service.EmailSender;
import com.epam.delivery.web.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;

import static com.epam.delivery.service.MessageBuilder.getLocaleMessage;

public class ChangeOrderStatusCommand implements Command {

    private static final String PARAM_ORDER = "order";
    private static final Logger logger = LogManager.getLogger();
    private static final String PARAM_ADMIN = "admin";
    private static final String PARAM_STATUS_ID = "status_id";
    private static final String EMAIL_SUBJECT = "Замовлення №%s | Order №%s ";
    private static final String EMAIL_TEXT = "Статус замовлення змінено на : %s \n" +
            "Order status changed to: %s \n";

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
        HttpSession session = request.getSession();
        String message;

        try {
            Admin admin = (Admin) session.getAttribute(PARAM_ADMIN);
            logger.trace("Get session attribute: admin --> " + admin);

            long orderID = Long.parseLong(request.getParameter(PARAM_ORDER));
            logger.trace("Request parameter: order --> " + orderID);


            Long status_id = Long.valueOf(request.getParameter(PARAM_STATUS_ID));
            logger.trace("Request parameter: status_id --> " + status_id);

            ConnectionBuilder builder = new ConnectionPool();
            OrderDao orderDao = new OrderDao(builder);
            Order order = orderDao.findById(orderID).orElse(null);
            logger.trace("Found in DB: order --> " + order);

            ClientDao clientDao = new ClientDao(builder);
            Client client = clientDao.findById(order.getClientID()).orElse(null);
            logger.trace("Found in DB: client --> " + client);

            ShippingStatusDao statusDao = new ShippingStatusDao(builder);

            ShippingStatus status = statusDao.findById(order.getStatusID()).orElse(null);
            ShippingStatus newStatus = statusDao.findById(status_id).orElse(null);

            if (!status.equals(newStatus)) {

                if (newStatus.getId() == 6L) {
                    Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                    order.setStatusID(newStatus.getId());
                    order.setDeliveryDate(currentTime);
                } else if (newStatus.getId() != 1L || newStatus.getId() != 7L) {
                    order.setStatusID(newStatus.getId());
                } else {
                    message = "you can't change this order status";
                    request.getServletContext().setAttribute("message", message);
                    logger.trace("Set servlet context attribute: message --> " + message);
                    return forward;
                }
                if (!orderDao.update(order)) return forward;

                StatusDescriptionBean descriptionBean = statusDao.findTranslateByStatusId(status_id).get();
                Map<String, String> description = descriptionBean.getDescription();
                String ua = description.get("ua");
                String en = description.get("en");
                message = "successful";
                String mailText = String.format(EMAIL_TEXT, ua, en);
                EmailSender.sendMail(client.getEmail(), String.format(EMAIL_SUBJECT, orderID, orderID), mailText);
            } else {
                message = getLocaleMessage(session, "info_message_nothing_to_change");
            }
            request.getServletContext().setAttribute("message", message);
            logger.trace("Set servlet context attribute: message --> " + message);

            forward = Path.COMMAND__ADMIN_ORDERS;
        } catch (NullPointerException numberFormatException) {
            message = getLocaleMessage(session, "error_message_number");
            request.setAttribute("message", numberFormatException);
            logger.error("errorMessage --> " + message);
            logger.error("Exception --> " + numberFormatException);

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
