package com.epam.delivery.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/count_fare")
public class CountFare extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String shippingAddress = req.getParameter("shipAddres");
        String deliveryAddress = req.getParameter("delivAddres");
        Integer length = Integer.parseInt(req.getParameter("length"));
        Integer height = Integer.parseInt(req.getParameter("height"));
        Integer width = Integer.parseInt(req.getParameter("width"));
        Integer weight = Integer.parseInt(req.getParameter("weight"));
        String str = String.format("Shipping address: %s, Delivery address: %s, length: %s, height: %s"
                + "width: %s, weight: %s", shippingAddress, deliveryAddress, length, height, width, weight);
        getServletContext().setAttribute("str", str);
        System.out.println(str);
        resp.sendRedirect("/index.jsp");
    }
}
