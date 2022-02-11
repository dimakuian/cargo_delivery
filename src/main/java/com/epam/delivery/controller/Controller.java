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
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String process = process(req, resp);
        req.getRequestDispatcher(process).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String process = process(req, resp);
        resp.sendRedirect(process);
    }

    /**
     * Main method of this controller.
     */
    private String process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //add logger

        // extract command name from the request
        String commandName = req.getParameter("command");
        System.out.println("Request parameter: command --> " + commandName); //replace to logger

        // obtain command object by its name
        Command command = CommandContainer.get(commandName);
        System.out.println("Obtained command --> " + command); //replace to logger

        // execute command and get forward address
        String forward = command.execute(req, resp);
        System.out.println("Forward address --> " + forward); //replace to logger

        System.out.println("Controller finished, now go to forward address --> " + forward);//replace to logger
//        if (forward != null) {
//            RequestDispatcher dispatcher = req.getRequestDispatcher(forward);
//            dispatcher.forward(req, resp);
//        }
        return forward;
    }
}
