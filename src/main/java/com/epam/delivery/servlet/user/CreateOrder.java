package com.epam.delivery.servlet.user;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/create_order")
public class CreateOrder extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Start CreateOrder doGet");
//        String shippingAddress = req.getParameter("shipping_address");
//        String deliveryAddress = req.getParameter("delivery_address");
//        Double length = Double.parseDouble(req.getParameter("length"));
//        Double height = Double.parseDouble(req.getParameter("height"));
//        Double width = Double.parseDouble(req.getParameter("width"));
//        Double weight = Double.parseDouble(req.getParameter("weight"));
//        Double volume = Double.parseDouble(req.getParameter("volume"));
        String tot = req.getParameter("total");
        System.out.println(tot);
    }
}
