package com.epam.delivery.web.command;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.ClientDao;
import com.epam.delivery.db.entities.Client;
import com.epam.delivery.db.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class EditUserCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    /**
     * Execution method for command.
     *
     * @param request
     * @param response
     * @return Address to go once the command is executed.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        logger.debug("start command");

        String forward = Path.PAGE__EDIT_ORDER;
        String name = request.getParameter("name");
        logger.trace("Request parameter: name --> " + name);

        String surname = request.getParameter("surname");
        logger.trace("Request parameter: surname --> " + surname);

        String patronymic = request.getParameter("patronymic");
        logger.trace("Request parameter: patronymic --> " + patronymic);

        String email = request.getParameter("email");
        logger.trace("Request parameter: email --> " + email);

        String phone = request.getParameter("phone");
        logger.trace("Request parameter: phone --> " + phone);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        logger.trace("Get session attribute: user --> " + user);

        Client client = (Client) session.getAttribute("client");
        logger.trace("Get session attribute: client --> " + client);

        if (name != null && surname != null && patronymic != null && email != null && phone != null &&
                user != null && client != null) {

            ClientDao clientDao = new ClientDao(new ConnectionPool());
            if (!client.getName().equals(name)) client.setName(name);
            if (!client.getSurname().equals(surname)) client.setSurname(surname);
            if (!client.getPatronymic().equals(patronymic)) client.setPatronymic(patronymic);
            if (!client.getEmail().equals(email)) client.setEmail(email);
            if (!client.getPhone().equals(phone)) client.setPhone(phone);

            if (clientDao.update(client)) {
                session.setAttribute("client", client);
                logger.trace("Set session attribute: client --> " + client);

                String message = "successful";
                request.getServletContext().setAttribute("message", message);
                logger.trace("Set servlet context attribute: message --> " + "successful");
            }
            forward = Path.COMMAND__USER_CABINET;
        } else {
            String errorMessage = "can't find this user";
            request.getServletContext().setAttribute("message", errorMessage);
            logger.trace("Set servlet context attribute: message --> " + errorMessage);
        }
        logger.debug("Command finished");
        return forward;
    }
}
