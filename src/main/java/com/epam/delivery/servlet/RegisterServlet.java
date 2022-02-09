package com.epam.delivery.servlet;

import com.epam.delivery.doa.ConnectionPool;
import com.epam.delivery.doa.impl.ClientDao;
import com.epam.delivery.doa.impl.UserDao;
import com.epam.delivery.entities.Client;
import com.epam.delivery.entities.Role;
import com.epam.delivery.entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/register_new_user")
public class RegisterServlet extends HttpServlet {
    public static final String EMAIL_REGEX = "^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
        System.out.println("RegisterServlet doGET()...");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Pattern pattern;
        Matcher matcher;
        Connection con = ConnectionPool.getConnection();
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String confirm_password = req.getParameter("confirm_password");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String patronymic = req.getParameter("patronymic");
        String email = req.getParameter("email");
        String tel = req.getParameter("tel");
        if (login != null && password != null && confirm_password != null && name != null &&
                surname != null && patronymic != null && email != null && tel != null) {
            UserDao userDao = new UserDao(con);

            //check login
            for (User user : userDao.findAll()) {
                if (user.getLogin().equals(login)) {
                    req.setAttribute("message", String.format("This login is already taken: %s", login));
                    System.out.printf("This login is already taken: %s%n", login);
                    resp.sendRedirect("/register.jsp");
                    return;
                }
            }
            //check password
            pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,12}$");
            matcher = pattern.matcher(password);
            if (!matcher.find() && !password.equals(confirm_password)) {
                req.setAttribute("message", "Passwords Don't Match");
                resp.sendRedirect("/register.jsp");
                return;
            }

            //check name
            pattern = Pattern.compile("^[a-zA-Zа-яА-Я]+$");
            matcher = pattern.matcher(name);

            if (!matcher.find()) {
                req.setAttribute("message", "Name isn't valid;");
                resp.sendRedirect("/register.jsp");
                return;
            }

            //check surname
            pattern = Pattern.compile("^[a-zA-Zа-яА-Я]+$");
            matcher = pattern.matcher(surname);
            if (!matcher.find()) {
                req.setAttribute("message", "Surname isn't valid;");
                resp.sendRedirect("/register.jsp");
                return;
            }

            //check patronymic
            pattern = Pattern.compile("^[a-zA-Zа-яА-Я]+$");
            matcher = pattern.matcher(patronymic);
            if (!matcher.find()) {
                req.setAttribute("message", "Patronymic isn't valid;");
                resp.sendRedirect("/register.jsp");
                return;
            }

            //check email
            pattern = Pattern.compile(EMAIL_REGEX);
            matcher = pattern.matcher(email);
            if (!matcher.find()) {
                req.setAttribute("message", "Email isn't valid;");
                resp.sendRedirect("/register.jsp");
                return;
            }

            //check tel
            pattern = Pattern.compile("^(\\+{1}(380){1}[0-9]{9}){1}$");
            matcher = pattern.matcher(tel);
            if (!matcher.find()) {
                req.setAttribute("message", "Phone number isn't valid;");
                resp.sendRedirect("/register.jsp");
                return;
            }

            User user = User.createUser(login, password, Role.CLIENT.ordinal());
            if (!userDao.insert(user)) {
                req.setAttribute("message", "problem while insert user");
                resp.sendRedirect("/register.jsp");
                return;
            }
            ClientDao clientDao = new ClientDao(con);
            Client client = Client.createPerson(user, name, surname);
            client.setPatronymic(patronymic);
            client.setEmail(email);
            client.setPhone(tel);
            if (clientDao.insert(client)) {
                req.setAttribute("message", "problem while insert client");
                resp.sendRedirect("/register.jsp");
                return;
            }
            req.setAttribute("message", "successful");
            resp.sendRedirect("/index.jsp");
        } else {
            req.setAttribute("message", "problem with input type");
            resp.sendRedirect("/register.jsp");
        }
    }
}
