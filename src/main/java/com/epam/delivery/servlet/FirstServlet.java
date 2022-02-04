package com.epam.delivery.servlet;

import com.epam.delivery.doa.ConnectionPool;
import com.epam.delivery.doa.impl.UserDao;
import com.epam.delivery.entities.Role;
import com.epam.delivery.entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/hello")
public class FirstServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");

        UserDao userDao = null;
        userDao = new UserDao(ConnectionPool.getConnection());
        User user = userDao.getByLogin(login).orElse(null);
        if (user != null) {
            req.getServletContext().setAttribute("user", user.getLogin());
            req.getServletContext().setAttribute("id", user.getId());
            req.getServletContext().setAttribute("role", Role.getRole(user).getName());
            if (Role.getRole(user).getName().equals("admin")) {
                resp.sendRedirect("/admin.jsp");
            }
            if (Role.getRole(user).getName().equals("client")) {
                resp.sendRedirect("/client.jsp");
            }
        } else {
            req.setAttribute("res", login);
            resp.sendRedirect("/index.jsp");
        }

    }
}
