package com.epam.delivery.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionBuilder {
    Connection getConnection();

     default void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed()) connection.close();
            } catch (SQLException e) {
                System.out.println("some problem with close connection ==> " + e.getMessage());
                e.printStackTrace();
            }
        }
        connection = null;
    }
}
