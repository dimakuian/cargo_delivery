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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/countCost.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Start #doPost CountFare");
        String shippingAddress = req.getParameter("shipping_address");
        String deliveryAddress = req.getParameter("delivery_address");
        Double length = Double.parseDouble(req.getParameter("length"));
        Double height = Double.parseDouble(req.getParameter("height"));
        Double width = Double.parseDouble(req.getParameter("width"));
        Double weight = Double.parseDouble(req.getParameter("weight"));
        Double volume = Double.parseDouble(req.getParameter("volume"));
        Double volumeWeight = length * height * width / 4000;
        double usedWeight = Double.max(weight, volumeWeight);
        Double total = 25 + (usedWeight * 2);
        System.out.println(total);
        String str = String.format("Shipping address: %s, Delivery address: %s, length: %s, height: %s "
                + "width: %s, weight: %s", shippingAddress, deliveryAddress, length, height, width, weight);
        getServletContext().setAttribute("total", total);
        resp.sendRedirect("/countCost.jsp");
    }
}
