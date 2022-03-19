package com.epam.delivery.command;

import com.epam.delivery.Path;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.ClientDao;
import com.epam.delivery.entities.Client;
import com.epam.delivery.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class EditUserCommand implements Command {

    /**
     * Execution method for command.
     *
     * @param request
     * @param response
     * @return Address to go once the command is executed.
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String forward = Path.PAGE__EDIT_ORDER;
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String patronymic = request.getParameter("patronymic");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Client client = (Client) session.getAttribute("client");

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
                request.getServletContext().setAttribute("message", "successful");
            }
            forward = Path.COMMAND__USER_CABINET;
        } else {
            String errorMessage = "can't find this user";
            request.getServletContext().setAttribute("message", errorMessage);
        }
        System.out.println("Command finished");
        return forward;
    }
}
