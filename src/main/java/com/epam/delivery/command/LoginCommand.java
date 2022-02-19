package com.epam.delivery.command;

import com.epam.delivery.doa.ConnectionPool;
import com.epam.delivery.doa.impl.UserDao;
import com.epam.delivery.entities.Role;
import com.epam.delivery.entities.User;
import com.epam.delivery.service.PasswordEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCommand extends Command {
    private static final long serialVersionUID = 8442743658968109708L;

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

        HttpSession session = request.getSession(true);
        // obtain login and password from the request
        String login = request.getParameter("login");
        System.out.println("Request parameter: logging --> " + login); //replace to logger

        String password = request.getParameter("password");
        // error handler
        String errorMessage;
        String forward = "error_page.jsp";

        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            errorMessage = "Login/password cannot be empty";
            request.getServletContext().setAttribute("errorMessage", errorMessage);
            System.out.println("errorMessage --> " + errorMessage); //replace to logger
            return forward;
        }
        User user = new UserDao(ConnectionPool.getConnection()).getByLogin(login).orElse(null);

        if (user == null || !PasswordEncoder.getHash(password).equals(user.getPassword())) {
            errorMessage = "Cannot find user with such login/password";
            request.getServletContext().setAttribute("errorMessage", errorMessage);
            System.out.println("errorMessage --> " + errorMessage); //replace to logger
            return forward;

        } else {
            System.out.println("Found in DB: user --> " + user); //replace to logger

            Role role = Role.getRole(user);
            System.out.println("userRole --> " + role); //replace to logger

            if (role == Role.ADMIN)
                forward = "/index.jsp";//replace to command

            if (role == Role.CLIENT)
                forward = "/controller?command=userCabinet";

            session.setAttribute("user", user);
            System.out.println("Set the session attribute: user --> " + user); //replace to logger

            session.setAttribute("role", role);
            System.out.println("Set the session attribute: userRole --> " + role);//replace to logger

            System.out.println("User " + user + " logged as " + role.toString().toLowerCase()); //replace to logger
        }
        System.out.println("Command finished");
        return forward;
    }
}
