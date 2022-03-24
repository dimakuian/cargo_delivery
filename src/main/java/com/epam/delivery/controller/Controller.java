package com.epam.delivery.controller;

import com.epam.delivery.command.Command;
import com.epam.delivery.command.CommandContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {
    static final Logger logger = LogManager.getLogger();
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

        logger.debug("Controller starts");

        // extract command name from the request
        String commandName = req.getParameter("command");
        logger.trace("Request parameter: command --> " + commandName);

        // obtain command object by its name
        Command command = CommandContainer.get(commandName);
        logger.trace("Obtained command --> " + command.print());

        // execute command and get page address
        String page = command.execute(req, resp);
        logger.trace("Forward address --> " + page);

        String method = req.getMethod();

        if (method.equals("GET")) {
            req.getRequestDispatcher(page).forward(req, resp);
        } else if (method.equals("POST")) {
            resp.sendRedirect(page);
        }

        logger.debug("Controller finished, now go to forward address --> " + page);    }
}
