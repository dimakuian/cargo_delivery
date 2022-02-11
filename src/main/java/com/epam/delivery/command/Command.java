package com.epam.delivery.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * Main interface for the Command pattern implementation.
 */
public abstract class Command implements Serializable {
    private static final long serialVersionUID = -1720199921879984976L;

    /**
     * Execution method for command.
     *
     * @return Address to go once the command is executed.
     */
    public abstract String execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException;


    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
