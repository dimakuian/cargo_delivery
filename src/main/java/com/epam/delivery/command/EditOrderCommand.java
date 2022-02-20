package com.epam.delivery.command;

import com.epam.delivery.doa.ConnectionPool;
import com.epam.delivery.doa.impl.OrderDao;
import com.epam.delivery.entities.Order;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

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
        String forward = "error_page.jsp";
        int orderID = Integer.parseInt(request.getParameter("order"));
        Connection connection = ConnectionPool.getConnection();
        OrderDao orderDao = new OrderDao(connection);
        Order order = orderDao.findById(orderID).orElse(null);
        if (order != null) {
            request.getServletContext().setAttribute("showOrder", order);
            forward = "/editOrder.jsp";
        } else {
            request.getServletContext().setAttribute("errorMessage", "problem with order");
        }
        System.out.println("Command finished");
        return forward;
    }
}
