package com.epam.delivery.controller;

import com.epam.delivery.command.Command;
import com.epam.delivery.command.CommandContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class Controller extends HttpServlet {
    private static final long serialVersionUID = 5248789422654095305L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    /**
     * Main method of this controller.
     */
    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //add logger

        // extract command name from the request
        String commandName = req.getParameter("command");
        System.out.println("Request parameter: command --> " + commandName); //replace to logger

        // obtain command object by its name
        Command command = CommandContainer.get(commandName);
        System.out.println("Obtained command --> " + command); //replace to logger

        // execute command and get page address
        String page = command.execute(req, resp);
        System.out.println("Forward address --> " + page); //replace to logger

        String method = req.getMethod();

        if (method.equals("GET")) {
            req.getRequestDispatcher(page).forward(req, resp);
        } else if (method.equals("POST")) {
            resp.sendRedirect(page);
        }
        System.out.println("Controller finished, now go to page address --> " + page);//replace to logger
    }
}
