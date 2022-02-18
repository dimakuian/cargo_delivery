package com.epam.delivery.command;

import com.epam.delivery.doa.ConnectionPool;
import com.epam.delivery.doa.impl.ClientDao;
import com.epam.delivery.doa.impl.OrderDao;
import com.epam.delivery.doa.impl.PaymentStatusDao;
import com.epam.delivery.entities.Client;
import com.epam.delivery.entities.Order;
import com.epam.delivery.entities.PaymentStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

public class PayOrderCommand extends Command {
    private static final long serialVersionUID = 2980508940786252119L;

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
        HttpSession session = request.getSession();
        Client client = (Client) session.getAttribute("client");
        int orderID = Integer.parseInt(request.getParameter("order"));
        Connection connection = ConnectionPool.getConnection();
        OrderDao orderDao = new OrderDao(connection);
        Order order = orderDao.findById(orderID).orElse(null);
        if (order != null && client != null) {
            double currentBalance = client.getBalance();
            if (currentBalance > order.getFare()) {
                ClientDao clientDao = new ClientDao(connection);
                client.setBalance(currentBalance - order.getFare());
                if (!clientDao.update(client)) return forward; //replace test variant

                PaymentStatusDao paymentStatusDao = new PaymentStatusDao(connection);
                PaymentStatus paid = paymentStatusDao.findById(1).orElse(null);
                order.setPaymentStatus(paid);
                if (!orderDao.update(order)) return forward; //replace test variant

                request.getServletContext().setAttribute("message", "successful");
            } else {
                request.getServletContext().setAttribute("message", "you don't have enough money");
            }
            forward = "/controller?command=userCabinet";
        } else {
            request.getServletContext().setAttribute("errorMessage", "problem with user");
        }
        System.out.println("Command finished");
        return forward;
    }
}
