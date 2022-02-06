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
        Double length = Double.parseDouble(req.getParameter("length"));
        Double height = Double.parseDouble(req.getParameter("height"));
        Double width = Double.parseDouble(req.getParameter("width"));
        Double weight = Double.parseDouble(req.getParameter("weight"));
        Double volume = Double.parseDouble(req.getParameter("vol"));
        System.out.println(volume);
        String str = String.format("Shipping address: %s, Delivery address: %s, length: %s, height: %s "
                + "width: %s, weight: %s", shippingAddress, deliveryAddress, length, height, width, weight);
        getServletContext().setAttribute("str", str);
        System.out.println(str);
        resp.sendRedirect("/index.jsp");
    }
}
