package com.epam.delivery.entities;

/**
 * ShippingStatusDescription entity.
 */
import java.util.Map;
import java.util.Objects;

public class ShippingStatusDescription extends Entity {
    private static final long serialVersionUID = -8529106795406463735L;

    private long statusID;
    private Map<String, String> description;

    private ShippingStatusDescription(long statusID, Map<String, String> description) {
        this.statusID = statusID;
        this.description = description;
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

    public static ShippingStatusDescription create(long statusID, Map<String, String> description) {
        return new ShippingStatusDescription(statusID, description);
    }

    @Override
    public String toString() {
        return "ShippingStatusDescription{" +
                "statusID=" + statusID +
                ", description=" + description +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShippingStatusDescription that = (ShippingStatusDescription) o;
        return statusID == that.statusID && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusID, description);
    }
}
