package com.epam.delivery.web.command.outOfControl;

import com.epam.delivery.web.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.delivery.Path.PAGE__HOME_PAGE;

public class SetLocaleCommand implements Command {

    private static final Logger logger = LogManager.getLogger();
    public static final String PARAM_PAGE = "page";
    public static final String PARAM_LANG = "lang";

    /**
     * Execution method for command.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return Address to go once the command is executed.
     */

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("start command");

        String redirection = request.getParameter(PARAM_PAGE);
        logger.trace("Get request parameter: page --> " + redirection);

        if (redirection == null) redirection = PAGE__HOME_PAGE;

        // login logic here
        String lang = request.getParameter(PARAM_LANG);
        logger.trace("Get request parameter: logging --> " + redirection);
        HttpSession session = request.getSession(true);
        session.removeAttribute("locale");
        if (lang != null) {
            switch (lang) {
                case "ua":
                    session.setAttribute("locale", "ua");
                    logger.trace("Set the session attribute: locale --> " + "ua");
                    break;
                case "en":
                    session.setAttribute("locale", "en");
                    logger.trace("Set the session attribute: locale --> " + "en");
                    break;
                default:
                    session.setAttribute("locale", "");
                    logger.trace("Set the session attribute: locale --> " + "");
                    break;
            }
        }

        logger.debug("Command finished");
        return redirection;
    }
}
