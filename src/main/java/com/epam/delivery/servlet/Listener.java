package com.epam.delivery.servlet;

import com.epam.delivery.doa.ConnectionPool;
import com.epam.delivery.doa.impl.LocalityDao;
import com.epam.delivery.entities.Locality;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.util.ArrayList;


@WebListener
public class Listener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Connection con = ConnectionPool.getConnection();
        System.out.println("connection ==> " + con);
        LocalityDao dao = new LocalityDao(con);
        Iterable<Locality> localities = dao.findAll();
        ArrayList <Locality> loc = new ArrayList<>();
        localities.forEach(loc::add);
        System.out.println(localities);
        sce.getServletContext().setAttribute("localities", loc);
        sce.getServletContext().setAttribute("name", "name");
    }
}
