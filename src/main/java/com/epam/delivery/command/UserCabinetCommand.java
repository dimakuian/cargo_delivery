package com.epam.delivery.command;

import com.epam.delivery.doa.ConnectionPool;
import com.epam.delivery.doa.impl.ClientDao;
import com.epam.delivery.doa.impl.OrderDao;
import com.epam.delivery.entities.Client;
import com.epam.delivery.entities.Order;
import com.epam.delivery.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class UserCabinetCommand implements Command {

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
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        String forward = "error_page.jsp";
        String errorMessage;
        if (user != null) {
            Connection connection = ConnectionPool.getConnection();
            ClientDao clientDao = new ClientDao(connection);
            Client client = clientDao.getByUserId(user.getId()).orElse(null);


            if (client != null) {
                OrderDao orderDao = new OrderDao(connection);
                List<Order> orders = new ArrayList<>();
                orderDao.findAllByUserID(user.getId()).forEach(orders::add);
                session.setAttribute("client", client);

                session.setAttribute("clientOrders", orders);
                forward = "/WEB-INF/jsp/user/userCabinet.jsp";
            } else {
                errorMessage = "can't find this client";
                request.getServletContext().setAttribute("message", errorMessage);
            }
        } else {
            errorMessage = "can't find this user";
            request.getServletContext().setAttribute("message", errorMessage);
        }
        System.out.println("Command finished");
        return forward;
    }
}
