package com.epam.delivery.db.entities.bean;

import java.util.Map;

public class LocalityBean {

    private long localityID;
    private Map<String,String> description;

    public LocalityBean() {
    }

    public LocalityBean(long localityID, Map<String, String> description) {
        this.localityID = localityID;
        this.description = description;
    }

    public long getLocalityID() {
        return localityID;
    }

    public void setLocalityID(long localityID) {
        this.localityID = localityID;
    }

    public Map<String, String> getDescription() {
        return description;
    }

    public void setDescription(Map<String, String> description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "LocalityBean{" +
                "localityID=" + localityID +
                ", description=" + description +
                '}';
    }
}
