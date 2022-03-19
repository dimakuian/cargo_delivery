package com.epam.delivery.command;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.ClientDao;
import com.epam.delivery.db.doa.impl.OrderDao;
import com.epam.delivery.entities.Client;
import com.epam.delivery.entities.Order;
import com.epam.delivery.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        String forward = Path.PAGE__ERROR_PAGE;
        String errorMessage;
        if (user != null) {
            ConnectionBuilder connectionBuilder = new ConnectionPool();
            ClientDao clientDao = new ClientDao(connectionBuilder);
            Client client = clientDao.getByUserId(user.getId()).orElse(null);


            if (client != null) {
                OrderDao orderDao = new OrderDao(connectionBuilder);
                List<Order> orders = new ArrayList<>();
                orderDao.findAllByUserID(user.getId()).forEach(orders::add);
                session.setAttribute("client", client);

                session.setAttribute("clientOrders", orders);
                forward = Path.PAGE__USER_CABINET;
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
