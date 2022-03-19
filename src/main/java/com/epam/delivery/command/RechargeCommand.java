package com.epam.delivery.command;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.ClientDao;
import com.epam.delivery.entities.Client;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RechargeCommand implements Command {

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
        double plusBalance = Double.parseDouble(request.getParameter("balance"));
        if (client != null) {
            ClientDao clientDao = new ClientDao(new ConnectionPool());

            double newBalance = plusBalance + client.getBalance();
            client.setBalance(newBalance);

            if (clientDao.update(client)) {
                forward = Path.COMMAND__USER_CABINET;
                request.getServletContext().setAttribute("message", "successful");
            } else {
                request.getServletContext().setAttribute("errorMessage", "problem with account balance");
            }
        } else {
            request.getServletContext().setAttribute("errorMessage", "problem with user");
        }
        return forward;
    }
}
