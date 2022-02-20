package com.epam.delivery.command;

import com.epam.delivery.doa.ConnectionPool;
import com.epam.delivery.doa.impl.ClientDao;
import com.epam.delivery.doa.impl.LocalityDao;
import com.epam.delivery.doa.impl.OrderDao;
import com.epam.delivery.doa.impl.ShippingStatusDescriptionDao;
import com.epam.delivery.entities.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
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
        Connection connection = ConnectionPool.getConnection();

        String forward = "/error_page.jsp";
        Integer shipAddressID = Integer.parseInt(request.getParameter("shipping_address"));
        Integer delAddressId = Integer.parseInt(request.getParameter("delivery_address"));

        LocalityDao localityDao = new LocalityDao(connection);
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

        ClientDao clientDao = new ClientDao(connection);
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
            builder.withShippingAddress(shippingAddress)
                    .withDeliveryAddress(deliveryAddress)
                    .withCreationTimestamp(new Timestamp(System.currentTimeMillis()))
                    .withClient(client)
                    .withConsignee(consignee)
                    .withDescription(description)
                    .withDistance(distance)
                    .withLength(length)
                    .withHeight(height)
                    .withWidth(width)
                    .withWeight(weight)
                    .withVolume(volume)
                    .withFare(total);

            ShippingStatusDescriptionDao shippingStatusDao = new ShippingStatusDescriptionDao(connection);
            ShippingStatusDescription shippingStatus = shippingStatusDao.findById(1).orElse(null); //crate method in dao

            builder.withShippingStatus(shippingStatus);

            order = builder.build();

            OrderDao orderDao = new OrderDao(connection);
            if (orderDao.insert(order)) {
                forward = "controller?command=userCabinet";
                request.getServletContext().setAttribute("message", "successful"); //edit later
            } else {
                forward = "error_page.jsp";
                request.getServletContext().setAttribute("message", "some problem"); //edit later
            }
        }
        return forward;

    }
}
