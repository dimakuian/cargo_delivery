package com.epam.delivery.web.command.clientCommand;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.ClientDao;
import com.epam.delivery.db.doa.impl.LocalityDao;
import com.epam.delivery.db.doa.impl.OrderDao;
import com.epam.delivery.db.doa.impl.ShippingStatusDao;
import com.epam.delivery.db.entities.Client;
import com.epam.delivery.db.entities.Locality;
import com.epam.delivery.db.entities.Order;
import com.epam.delivery.db.entities.User;
import com.epam.delivery.db.entities.bean.StatusDescriptionBean;
import com.epam.delivery.web.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;

import static com.epam.delivery.service.MessageBuilder.getLocaleMessage;

public class CreateOrderCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String PARAM_SHIPPING_ADDRESS = "shipping_address";
    private static final String PARAM_DELIVERY_ADDRESS = "delivery_address";
    private static final String PARAM_LENGTH = "length";
    private static final String PARAM_HEIGHT = "height";
    private static final String PARAM_WIDTH = "width";
    private static final String PARAM_WEIGHT = "weight";
    private static final String PARAM_CONSIGNEE = "consignee";
    private static final String PARAM_DESCRIPTION = "description";
    private static final String PARAM_USER = "user";

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

        String forward = Path.PAGE__ERROR_PAGE;
        String message;
        HttpSession session = request.getSession();

        try {

            Long shipAddressID = Long.parseLong(request.getParameter(PARAM_SHIPPING_ADDRESS));
            logger.trace("Request parameter: shipping_address --> " + shipAddressID);

            Long delAddressId = Long.parseLong(request.getParameter(PARAM_DELIVERY_ADDRESS));
            logger.trace("Request parameter: delivery_address --> " + delAddressId);

            ConnectionBuilder connectionBuilder = new ConnectionPool();
            LocalityDao localityDao = new LocalityDao(connectionBuilder);
            Locality shippingAddress = localityDao.findById(shipAddressID).orElse(null);
            logger.trace("Found in DB: locality --> " + shippingAddress);

            Locality deliveryAddress = localityDao.findById(delAddressId).orElse(null);
            logger.trace("Found in DB: locality --> " + deliveryAddress);

            Double distance = localityDao.calcDistanceBetweenTwoLocality(shippingAddress, deliveryAddress).orElse(null);

            double length = Double.parseDouble(request.getParameter(PARAM_LENGTH));
            logger.trace("Request parameter: length --> " + length);

            double height = Double.parseDouble(request.getParameter(PARAM_HEIGHT));
            logger.trace("Request parameter: height --> " + height);

            double width = Double.parseDouble(request.getParameter(PARAM_WIDTH));
            logger.trace("Request parameter: width --> " + width);

            double weight = Double.parseDouble(request.getParameter(PARAM_WEIGHT));
            logger.trace("Request parameter: weight --> " + weight);

            String consignee = request.getParameter(PARAM_CONSIGNEE);
            logger.trace("Request parameter: consignee --> " + consignee);

            String description = request.getParameter(PARAM_DESCRIPTION);
            logger.trace("Request parameter: description --> " + description);

            User user = (User) session.getAttribute(PARAM_USER);
            logger.trace("Get session attribute: user --> " + user);

            ClientDao clientDao = new ClientDao(connectionBuilder);
            Client client = clientDao.getByUserId(user.getId()).orElse(null);
            logger.trace("Found in DB: client --> " + client);

            double volume = length * height * width;
            BigDecimal bigDecimal = new BigDecimal(volume).setScale(2, RoundingMode.HALF_UP);
            volume = bigDecimal.doubleValue();
            double volumeWeight = volume / 4000;
            double usedWeight = Double.max(weight, volumeWeight);
            double total;
            if (distance < 500) {
                total = (30 + usedWeight * 3);
            } else {
                total = distance / 500 * (30 + usedWeight * 3);
            }

            Order order = Order.createOrder();
            Order.Builder builder = order.new Builder(order);
            builder.withShippingAddress(shippingAddress.getId())
                    .withDeliveryAddress(deliveryAddress.getId())
                    .withCreationTimestamp(new Timestamp(System.currentTimeMillis()))
                    .withClient(client.getId())
                    .withConsignee(consignee)
                    .withDescription(description)
                    .withDistance(distance)
                    .withLength(length)
                    .withHeight(height)
                    .withWidth(width)
                    .withWeight(weight)
                    .withVolume(volume)
                    .withFare(total);

            ShippingStatusDao statusDao = new ShippingStatusDao(connectionBuilder);
            StatusDescriptionBean shippingStatus = statusDao.findTranslateByStatusId(1L).orElse(null);//crate method in dao
            logger.trace("Found in DB: status --> " + shippingStatus);

            builder.withShippingStatus(shippingStatus.getStatusID());

            order = builder.build();
            OrderDao orderDao = new OrderDao(connectionBuilder);

            if (orderDao.insert(order)) {
                forward = Path.COMMAND_CLIENT_ORDERS;
                message = getLocaleMessage(session, "successful_create_order_message");
            } else {
                forward = Path.PAGE__ERROR_PAGE;
                message = getLocaleMessage(session, "error_message_unknown");
            }
            request.getServletContext().setAttribute("message", message); //edit later
            logger.trace("Set servlet context attribute: message --> " + message);

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
