package com.epam.delivery.doa.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public abstract class AbstractDao<T, ID> {

    protected Connection connection;

    protected AbstractDao(Connection connection) {
        this.connection = connection;
    }

    abstract boolean insert(T entity);

    abstract boolean update(T entity);

    abstract Optional<T> getById(ID id);

    abstract boolean existsById(ID id);

    abstract Iterable<T> findAll();

    abstract boolean deleteById(ID id);

    protected void closeConnection() {
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
