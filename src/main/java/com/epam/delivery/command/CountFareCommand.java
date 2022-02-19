package com.epam.delivery.command;

import com.epam.delivery.doa.ConnectionPool;
import com.epam.delivery.doa.impl.LocalityDao;
import com.epam.delivery.entities.Locality;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CountFareCommand extends Command {
    private static final long serialVersionUID = 438065897383166022L;

    /**
     * Execution method for command.
     *
     * @param request
     * @param response
     * @return Address to go once the command is executed.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("start command");  //replace to logger
        String forward = "/error_page.jsp";
        Integer shipAddressID = Integer.parseInt(request.getParameter("shipping_address"));
        Integer delAddressId = Integer.parseInt(request.getParameter("delivery_address"));

        LocalityDao localityDao = new LocalityDao(ConnectionPool.getConnection());
        Locality shippingAddress = localityDao.findById(shipAddressID).orElse(null);
        Locality deliveryAddress = localityDao.findById(delAddressId).orElse(null);
        Double distance = localityDao.calcDistanceBetweenTwoLocality(shippingAddress, deliveryAddress).orElse(null);

        Double length = Double.parseDouble(request.getParameter("length"));
        Double height = Double.parseDouble(request.getParameter("height"));
        Double width = Double.parseDouble(request.getParameter("width"));
        double weight = Double.parseDouble(request.getParameter("weight"));

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
            forward = "/countCost.jsp";
        }
        System.out.println("Command finished");
        return forward;
    }
}