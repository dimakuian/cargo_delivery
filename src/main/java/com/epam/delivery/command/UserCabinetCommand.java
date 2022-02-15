package com.epam.delivery.command;

import com.epam.delivery.doa.ConnectionPool;
import com.epam.delivery.doa.impl.ClientDao;
import com.epam.delivery.entities.Client;
import com.epam.delivery.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserCabinetCommand extends Command {
    private static final long serialVersionUID = -6065564914771102683L;

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
            ClientDao clientDao = new ClientDao(ConnectionPool.getConnection());
            Client client = clientDao.getByUserId(user.getId()).orElse(null);
            if (client != null) {
                session.setAttribute("client", client);
                forward = "/userCabinet.jsp";
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
