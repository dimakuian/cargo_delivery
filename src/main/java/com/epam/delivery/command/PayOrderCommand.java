package com.epam.delivery.command;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.ClientDao;
import com.epam.delivery.db.doa.impl.OrderDao;
import com.epam.delivery.db.doa.impl.ShippingStatusDao;
import com.epam.delivery.entities.Client;
import com.epam.delivery.entities.Order;
import com.epam.delivery.entities.ShippingStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PayOrderCommand implements Command {

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
        HttpSession session = request.getSession();
        Client client = (Client) session.getAttribute("client");
        long orderID = Long.parseLong(request.getParameter("order"));
        ConnectionBuilder connectionBuilder = new ConnectionPool();
        OrderDao orderDao = new OrderDao(connectionBuilder);
        Order order = orderDao.findById(orderID).orElse(null);
        if (order != null && client != null) {
            double currentBalance = client.getBalance();
            if (currentBalance > order.getFare()) {
                ClientDao clientDao = new ClientDao(connectionBuilder);
                client.setBalance(currentBalance - order.getFare());
                if (!clientDao.update(client)) return forward; //replace test variant

                ShippingStatusDao shippingStatusDao = new ShippingStatusDao(connectionBuilder);
                ShippingStatus paid = shippingStatusDao.findById(2L).orElse(null);
                order.setStatusID(paid.getId());
                if (!orderDao.update(order)) return forward; //replace test variant

                request.getServletContext().setAttribute("message", "successful");
            } else {
                request.getServletContext().setAttribute("message", "you don't have enough money");
            }
            forward = Path.COMMAND__USER_CABINET;
        } else {
            request.getServletContext().setAttribute("errorMessage", "problem with user");
        }
        System.out.println("Command finished");
        return forward;
    }
}
