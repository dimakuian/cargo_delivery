package com.epam.delivery.command;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.OrderDao;
import com.epam.delivery.entities.Order;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditOrderCommand implements Command {

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
        String forward = Path.PAGE__ERROR_PAGE;
        long orderID = Long.parseLong(request.getParameter("order"));
        OrderDao orderDao = new OrderDao(new ConnectionPool());
        Order order = orderDao.findById(orderID).orElse(null);
        if (order != null) {
            request.getServletContext().setAttribute("showOrder", order);
            forward = Path.PAGE__EDIT_ORDER;
        } else {
            request.getServletContext().setAttribute("errorMessage", "problem with order");
        }
        System.out.println("Command finished");
        return forward;
    }
}
