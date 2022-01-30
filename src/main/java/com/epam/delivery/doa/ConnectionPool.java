package com.epam.delivery.doa;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static DataSource ds;

    private ConnectionPool() {
    }

    private static synchronized DataSource getDataSource() {
        if (ds == null) initDataSource();
        return ds;
    }

    private static void initDataSource() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            ds = (DataSource) envContext.lookup("jdbc/delivery");
        } catch (NamingException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

    }

    public static Connection getConnection() {
        Connection con = null;
        try {
            con = getDataSource().getConnection();
        } catch (SQLException e) {
//            logger.fatal("Can't get sql connection from DataSource", e.getMessage());
            Thread.currentThread().interrupt();
        }
        return con;
    }

}
