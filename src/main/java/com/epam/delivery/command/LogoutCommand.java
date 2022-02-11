package com.epam.delivery.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutCommand extends Command {
    private static final long serialVersionUID = 7651273082286167624L;

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
        HttpSession session = request.getSession(false);
//        User user = (User) session.getAttribute("user");
//        String userRole = (String) session.getAttribute("role");
        if (session != null) session.invalidate();
        String forward = "/index.jsp";
        System.out.println("Command finished"); //replace to logger
        return forward;
    }
}
