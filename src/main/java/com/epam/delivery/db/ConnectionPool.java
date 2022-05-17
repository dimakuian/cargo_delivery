package com.epam.delivery.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool implements ConnectionBuilder {

    private static final Logger logger = LogManager.getLogger();
    private static DataSource ds;

    public ConnectionPool() {
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
        } catch (NamingException exception) {
            logger.error("Cannot obtain a connection from the pool", exception);
        }

    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = getDataSource().getConnection();
        } catch (SQLException e) {
            logger.fatal("Can't get sql connection from DataSource" + e.getMessage());
        }
        return connection;
    }
}
