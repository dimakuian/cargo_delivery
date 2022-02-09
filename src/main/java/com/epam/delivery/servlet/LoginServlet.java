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
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Start LoginServlet #doPost");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        HttpSession session = req.getSession(true);
        UserDao userDao = new UserDao(ConnectionPool.getConnection());
        User user = userDao.getByLogin(login).orElse(null);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                session.setAttribute("userId", user.getId());
                session.setAttribute("role", Role.getRole(user).getName());
                String role = Role.getRole(user).getName();
                if (Role.getRole(user).getName() != null) {
                    resp.sendRedirect("/index.jsp");
                }
            } else {
                getServletContext().setAttribute("message", "Incorrect password");
                resp.sendRedirect("/index.jsp");
            }
        } else {
            getServletContext().setAttribute("message", "Can't find this user: " + login);
            resp.sendRedirect("/index.jsp");
        }
        System.out.println("End LoginServlet #doPost");
    }
}
