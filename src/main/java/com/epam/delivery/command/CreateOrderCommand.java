package com.epam.delivery.command;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.ClientDao;
import com.epam.delivery.db.doa.impl.LocalityDao;
import com.epam.delivery.db.doa.impl.OrderDao;
import com.epam.delivery.db.doa.impl.ShippingStatusDao;
import com.epam.delivery.entities.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;

public class CreateOrderCommand implements Command {

    /**
     * Execution method for command.
     *
     * @param request
     * @param response
     * @return Address to go once the command is executed.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("start command");  //replace to logger

        String forward = Path.PAGE__ERROR_PAGE;
        Long shipAddressID = Long.parseLong(request.getParameter("shipping_address"));
        Long delAddressId = Long.parseLong(request.getParameter("delivery_address"));

        ConnectionBuilder connectionBuilder = new ConnectionPool();
        LocalityDao localityDao = new LocalityDao(connectionBuilder);
        Locality shippingAddress = localityDao.findById(shipAddressID).orElse(null);
        Locality deliveryAddress = localityDao.findById(delAddressId).orElse(null);
        assert shippingAddress != null;
        assert deliveryAddress != null;
        Double distance = localityDao.calcDistanceBetweenTwoLocality(shippingAddress, deliveryAddress).orElse(null);


        double length = Double.parseDouble(request.getParameter("length"));
        double height = Double.parseDouble(request.getParameter("height"));
        double width = Double.parseDouble(request.getParameter("width"));
        double weight = Double.parseDouble(request.getParameter("weight"));
        String consignee = request.getParameter("consignee");
        String description = request.getParameter("description");
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");
        if (user == null) return forward;

        ClientDao clientDao = new ClientDao(connectionBuilder);
        Client client = clientDao.findById(user.getId()).orElse(null);

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
            ShippingStatusDescription shippingStatus = statusDao.findTranslateByStatusId(1L).orElse(null); //crate method in dao

            builder.withShippingStatus(shippingStatus.getStatusID());

            order = builder.build();

            OrderDao orderDao = new OrderDao(connectionBuilder);
            if (orderDao.insert(order)) {
                forward=Path.COMMAND__USER_CABINET;
                request.getServletContext().setAttribute("message", "successful"); //edit later
            } else {
                forward = Path.PAGE__ERROR_PAGE;
                request.getServletContext().setAttribute("message", "some problem"); //edit later
            }
        }
        return forward;

    }
}
