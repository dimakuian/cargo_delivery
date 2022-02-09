package com.epam.delivery.servlet.filter;

import com.epam.delivery.doa.ConnectionPool;
import com.epam.delivery.doa.impl.LocalityDao;
import com.epam.delivery.doa.impl.TariffDao;
import com.epam.delivery.entities.Locality;
import com.epam.delivery.entities.Tariff;

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
        TariffDao tariffDao = new TariffDao(con);
        Iterable<Tariff> tariffs = tariffDao.findAll();
        sce.getServletContext().setAttribute("tariff",tariffs);
        sce.getServletContext().setAttribute("localities", loc);
        sce.getServletContext().setAttribute("name", "name");
    }
}
