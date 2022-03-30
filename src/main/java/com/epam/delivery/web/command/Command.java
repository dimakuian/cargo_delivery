package com.epam.delivery.web.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main interface for the Command pattern implementation.
 */
public interface Command {

    /**
     * Execution method for command.
     *
     * @return Address to go once the command is executed.
     */
    String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException;

    default String print() {
        return this.getClass().getSimpleName();
    }

}
