package com.epam.delivery.db.doa.impl;

import com.epam.delivery.db.ConnectionBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public abstract class AbstractDao<T, ID> implements Serializable {

    private static final long serialVersionUID = -1589363087043147391L;

    protected static final Logger logger = LogManager.getLogger();

    protected ConnectionBuilder builder;

    public AbstractDao(ConnectionBuilder builder) {
        this.builder = builder;
    }

    public abstract boolean insert(T entity);

    public abstract boolean update(T entity);

    public abstract Optional<T> findById(ID id);

    public abstract boolean existsById(ID id);

    public abstract Iterable<T> findAll();

    public abstract boolean deleteById(ID id);

}
