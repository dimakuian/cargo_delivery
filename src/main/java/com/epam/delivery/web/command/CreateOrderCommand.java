package com.epam.delivery.web.command;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.ClientDao;
import com.epam.delivery.db.doa.impl.LocalityDao;
import com.epam.delivery.db.doa.impl.OrderDao;
import com.epam.delivery.db.doa.impl.ShippingStatusDao;
import com.epam.delivery.db.entities.*;
import com.epam.delivery.db.entities.bean.StatusDescriptionBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;

public class CreateOrderCommand implements Command {

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

        String forward = Path.PAGE__ERROR_PAGE;
        Long shipAddressID = Long.parseLong(request.getParameter("shipping_address"));
        logger.trace("Request parameter: shipping_address --> " + shipAddressID);

        Long delAddressId = Long.parseLong(request.getParameter("delivery_address"));
        logger.trace("Request parameter: delivery_address --> " + delAddressId);

        ConnectionBuilder connectionBuilder = new ConnectionPool();
        LocalityDao localityDao = new LocalityDao(connectionBuilder);
        Locality shippingAddress = localityDao.findById(shipAddressID).orElse(null);
        logger.trace("Found in DB: locality --> " + shippingAddress);

        Locality deliveryAddress = localityDao.findById(delAddressId).orElse(null);
        logger.trace("Found in DB: locality --> " + deliveryAddress);

        Double distance = localityDao.calcDistanceBetweenTwoLocality(shippingAddress, deliveryAddress).orElse(null);

        double length = Double.parseDouble(request.getParameter("length"));
        logger.trace("Request parameter: length --> " + length);

        double height = Double.parseDouble(request.getParameter("height"));
        logger.trace("Request parameter: height --> " + height);

        double width = Double.parseDouble(request.getParameter("width"));
        logger.trace("Request parameter: width --> " + width);

        double weight = Double.parseDouble(request.getParameter("weight"));
        logger.trace("Request parameter: weight --> " + weight);

        String consignee = request.getParameter("consignee");
        logger.trace("Request parameter: consignee --> " + consignee);

        String description = request.getParameter("description");
        logger.trace("Request parameter: description --> " + description);

        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");
        logger.trace("Get session attribute: user --> " + user);

        if (user == null) return forward;

        ClientDao clientDao = new ClientDao(connectionBuilder);
        Client client = clientDao.getByUserId(user.getId()).orElse(null);  //!!!!! problem
        logger.trace("Found in DB: client --> " + client);

        if (distance != null && consignee != null && description != null && client != null) {

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
            String message;
            if (orderDao.insert(order)) {
                forward = Path.COMMAND_CLIENT_CABINET;
                message = "successful";
            } else {
                forward = Path.PAGE__ERROR_PAGE;
                message = "some problem";
            }
            request.getServletContext().setAttribute("message", message); //edit later
            logger.trace("Set servlet context attribute: message --> " + message);
        }
        logger.debug("Command finished");
        return forward;

    }
}
