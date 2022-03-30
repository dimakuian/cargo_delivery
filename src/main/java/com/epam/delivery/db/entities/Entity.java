package com.epam.delivery.db.entities;

import java.io.Serializable;

/**
 * Root of all entities which have identifier field.
 */
public abstract class Entity implements Serializable {

    private static final long serialVersionUID = 464786738590157269L;

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
