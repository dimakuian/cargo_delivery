package com.epam.delivery.web.filter;

import com.epam.delivery.db.ConnectionBuilder;
import com.epam.delivery.db.ConnectionPool;
import com.epam.delivery.db.doa.impl.LocalityDao;
import com.epam.delivery.db.doa.impl.ShippingStatusDao;
import com.epam.delivery.db.entities.bean.LocalityBean;
import com.epam.delivery.db.entities.bean.StatusDescriptionBean;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Comparator;
import java.util.List;


@WebListener
public class Listener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionBuilder builder = new ConnectionPool();
        LocalityDao localityDao = new LocalityDao(builder);
        List<LocalityBean> localities = localityDao.getAllTranslate();
        ServletContext context = sce.getServletContext();
        context.setAttribute("localities", localities);
        ShippingStatusDao descriptionDAO = new ShippingStatusDao(builder);
        List<StatusDescriptionBean> descriptions = descriptionDAO.getAllTranslate();
        descriptions.sort(Comparator.comparingLong(StatusDescriptionBean::getStatusID));
        context.setAttribute("status_description", descriptions);
    }
}
