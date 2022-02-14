package com.epam.delivery.doa.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public abstract class AbstractDao<T, ID> {

    protected Connection connection;

    public AbstractDao(Connection connection) {
        this.connection = connection;
    }

    public abstract boolean insert(T entity);

    public abstract boolean update(T entity);

    public abstract Optional<T> findById(ID id);

    public abstract boolean existsById(ID id);

    public abstract Iterable<T> findAll();

    public abstract boolean deleteById(ID id);

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
