package com.epam.delivery;

import com.epam.delivery.doa.ConnectionPool;
import com.epam.delivery.doa.SimpleConnection;
import com.epam.delivery.doa.impl.UserDao;
import com.epam.delivery.entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@WebServlet("/hello")
public class FirstServlet extends HttpServlet {
    Connection connection = SimpleConnection.getConnection();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        if (connection!=null){
            UserDao userDao = new UserDao(ConnectionPool.getConnection());
            User user = userDao.getByLogin(login).orElse(null);
            if (user != null) {
                req.setAttribute("res", user.toString());
            } else req.setAttribute("res", "don't find user " + login);
            req.getRequestDispatcher("/first.jsp").forward(req, resp);

        }
    }


}
