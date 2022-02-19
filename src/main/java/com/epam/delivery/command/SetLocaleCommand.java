package com.epam.delivery.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SetLocaleCommand extends Command{
    private static final long serialVersionUID = -8860652860963643059L;

    /**
     * Execution method for command.
     *
     * @param request
     * @param response
     * @return Address to go once the command is executed.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String redirection;
        redirection = request.getParameter("page");
        if (redirection == null) redirection = "index.jsp";

        // login logic here
        String lang = request.getParameter("lang");
        HttpSession session = request.getSession(true);
        //session.removeAttribute("locale");
        if (lang!=null) {
            switch (lang) {
                case "ua" : session.setAttribute("locale", "ua"); break;
                case "en" : session.setAttribute("locale", "en"); break;
                default : session.setAttribute("locale", ""); break;
            }
        }
        return redirection;
    }
}
