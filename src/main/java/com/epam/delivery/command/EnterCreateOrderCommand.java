package com.epam.delivery.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EnterCreateOrderCommand implements Command{
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
        String forward = "/WEB-INF/jsp/user/createOrder.jsp";

        System.out.println("Command finished");
        return forward;
    }
}
