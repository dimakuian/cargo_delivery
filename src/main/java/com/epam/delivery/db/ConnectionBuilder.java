package com.epam.delivery.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionBuilder {

    Connection getConnection();

    // //////////////////////////////////////////////////////////
    // DB util methods
    // //////////////////////////////////////////////////////////

    /**
     * Commits and close the given connection.
     *
     * @param connection Connection to be committed and closed.
     */

    default void commitAndClose(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.commit();
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Rollbacks and close the given connection.
     *
     * @param connection Connection to be rollbacked and closed.
     */
    default void rollbackAndClose(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
