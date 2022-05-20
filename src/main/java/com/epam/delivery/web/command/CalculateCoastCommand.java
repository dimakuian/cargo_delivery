package com.epam.delivery.web.command;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.LocalityDao;
import com.epam.delivery.db.entities.Locality;
import com.epam.delivery.service.MessageBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.undo.CannotRedoException;
import java.io.IOException;

public class CalculateCoastCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    private static final String PARAM_SHIPPING_ADDRESS = "shipping_address";
    private static final String PARAM_DELIVERY_ADDRESS = "delivery_address";
    private static final String PARAM_LENGTH = "length";
    private static final String PARAM_HEIGHT = "height";
    private static final String PARAM_WIDTH = "width";
    private static final String PARAM_WEIGHT = "weight";


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
        String errorMessage;
        try {
            long shipAddressID = Long.parseLong(request.getParameter(PARAM_SHIPPING_ADDRESS));
            logger.trace("Request parameter: shipping_address --> " + shipAddressID);

            long delAddressId = Long.parseLong(request.getParameter(PARAM_DELIVERY_ADDRESS));
            logger.trace("Request parameter: delivery_address --> " + delAddressId);

            LocalityDao localityDao = new LocalityDao(new ConnectionPool());
            Locality shippingAddress = localityDao.findById(shipAddressID).orElseThrow(CannotRedoException::new);
            logger.trace("Found in DB: locality --> " + shippingAddress);

            Locality deliveryAddress = localityDao.findById(delAddressId).orElseThrow(CannotRedoException::new);
            logger.trace("Found in DB: locality --> " + deliveryAddress);

            Double distance = localityDao.calcDistanceBetweenTwoLocality(shippingAddress, deliveryAddress)
                    .orElseThrow(CannotRedoException::new);
            Double length = Double.parseDouble(request.getParameter(PARAM_LENGTH));
            logger.trace("Request parameter: length --> " + length);

            Double height = Double.parseDouble(request.getParameter(PARAM_HEIGHT));
            logger.trace("Request parameter: height --> " + height);

            Double width = Double.parseDouble(request.getParameter(PARAM_WIDTH));
            logger.trace("Request parameter: width --> " + width);

            double weight = Double.parseDouble(request.getParameter(PARAM_WEIGHT));
            logger.trace("Request parameter: weight --> " + weight);

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

            forward = Path.PAGE__COUNT_COAST;
        } catch (NumberFormatException numberFormatException) {
            errorMessage = MessageBuilder.getLocaleMessage(session, "error_message_number");
            request.setAttribute("message", numberFormatException);
            logger.error("errorMessage --> " + errorMessage);
            logger.error("Exception --> " + numberFormatException);
        } catch (Exception cannotRedoException) {
            errorMessage = MessageBuilder.getLocaleMessage(session, "error_message_unknown");
            request.setAttribute("message", errorMessage);
            logger.error("errorMessage --> " + errorMessage);
            logger.error("Exception --> " + cannotRedoException);
        }

        logger.debug("Command finished");
        return forward;
    }
}