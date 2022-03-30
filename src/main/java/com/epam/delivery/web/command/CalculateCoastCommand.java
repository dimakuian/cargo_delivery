package com.epam.delivery.web.command;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.LocalityDao;
import com.epam.delivery.db.entities.Locality;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CalculateCoastCommand implements Command {

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
        long shipAddressID = Long.parseLong(request.getParameter("shipping_address"));
        logger.trace("Request parameter: shipping_address --> " + shipAddressID);

        long delAddressId = Long.parseLong(request.getParameter("delivery_address"));
        logger.trace("Request parameter: delivery_address --> " + delAddressId);

        LocalityDao localityDao = new LocalityDao(new ConnectionPool());
        Locality shippingAddress = localityDao.findById(shipAddressID).orElse(null);
        logger.trace("Found in DB: locality --> " + shippingAddress);

        Locality deliveryAddress = localityDao.findById(delAddressId).orElse(null);
        logger.trace("Found in DB: locality --> " + deliveryAddress);

        Double distance = localityDao.calcDistanceBetweenTwoLocality(shippingAddress, deliveryAddress).orElse(null);
        Double length = Double.parseDouble(request.getParameter("length"));
        logger.trace("Request parameter: length --> " + length);

        Double height = Double.parseDouble(request.getParameter("height"));
        logger.trace("Request parameter: height --> " + height);

        Double width = Double.parseDouble(request.getParameter("width"));
        logger.trace("Request parameter: width --> " + width);

        double weight = Double.parseDouble(request.getParameter("weight"));
        logger.trace("Request parameter: weight --> " + weight);

        if (distance != null) {
            double volumeWeight = length * height * width / 4000;
            double usedWeight = Double.max(weight, volumeWeight);
            double total;
            if (distance < 500) {
                total = (30 + usedWeight * 3);
            } else {
                total = distance / 500 * (30 + usedWeight * 3);
            }
            request.getServletContext().setAttribute("total", Math.round(total));
            logger.trace("Servlet context attribute: total --> " + total);

            forward = Path.COMMAND__VIEW_CALCULATE_COAST;
        }
        logger.debug("Command finished");
        return forward;
    }
}