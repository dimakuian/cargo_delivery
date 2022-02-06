package com.epam.delivery.servlet;

import com.epam.delivery.entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Start LogoutServlet #doGet");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String userRole = (String) session.getAttribute("role");
        System.out.println(user.toString());
        System.out.println(userRole);
        if (user != null && userRole != null) {
            session.removeAttribute("user");
            session.removeAttribute("role");
            resp.sendRedirect("/index.jsp");
        }
        System.out.println("End LogoutServlet #doGet");
    }
}
