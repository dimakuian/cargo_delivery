package com.epam.delivery.servlet;

import com.epam.delivery.entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Start LogoutServlet #doGet");
        HttpSession session = req.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        String userRole = (String) session.getAttribute("role");
        System.out.println(userRole);
        if (userId != null && userRole != null) {
            Enumeration<String> attributes = session.getAttributeNames();
            while (attributes.hasMoreElements()){
                System.out.println(attributes.nextElement());
                session.removeAttribute(attributes.nextElement());
            }
            resp.sendRedirect("/index.jsp");
        }
        System.out.println("End LogoutServlet #doGet");
    }
}
