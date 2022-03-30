package com.epam.delivery.db.entities.bean;

import java.util.Map;

public class StatusDescriptionBean {

    private long statusID;
    private Map<String, String> description;

    public StatusDescriptionBean() {
    }

    public long getStatusID() {
        return statusID;
    }

    public void setStatusID(long statusID) {
        this.statusID = statusID;
    }

    public Map<String, String> getDescription() {
        return description;
    }

    public void setDescription(Map<String, String> description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ShippingStatusDescription{" +
                "statusID=" + statusID +
                ", description=" + description +
                '}';
    }
}
