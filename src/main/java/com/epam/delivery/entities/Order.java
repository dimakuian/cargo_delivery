package com.epam.delivery.entities;

import java.sql.Timestamp;

/**
 * Order entity.
 */
public class Order extends Entity {
    private static final long serialVersionUID = 1255434001425151410L;

    private int id;
    private long shippingAddressID;
    private long deliveryAddressID;
    private Timestamp creationTime;
    private long clientID;
    private String consignee;
    private String description;
    private double distance;
    private double length;
    private double height;
    private double width;
    private double weight;
    private double volume;
    private double fare;
    private long statusID;
    private Timestamp deliveryDate;

    private Order() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getShippingAddressID() {
        return shippingAddressID;
    }

    public void setShippingAddressID(long shippingAddressID) {
        this.shippingAddressID = shippingAddressID;
    }

    public long getDeliveryAddressID() {
        return deliveryAddressID;
    }

    public void setDeliveryAddressID(long deliveryAddressID) {
        this.deliveryAddressID = deliveryAddressID;
    }

    public long getClientID() {
        return clientID;
    }

    public void setClientID(long clientID) {
        this.clientID = clientID;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public long getStatusID() {
        return statusID;
    }

    public void setStatusID(long statusID) {
        this.statusID = statusID;
    }

    public Timestamp getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Timestamp deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public static Order createOrder() {
        return new Order();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", shippingAddressID=" + shippingAddressID +
                ", deliveryAddressID=" + deliveryAddressID +
                ", creationTime=" + creationTime +
                ", clientID=" + clientID +
                ", consignee='" + consignee + '\'' +
                ", description='" + description + '\'' +
                ", distance=" + distance +
                ", length=" + length +
                ", height=" + height +
                ", width=" + width +
                ", weight=" + weight +
                ", volume=" + volume +
                ", fare=" + fare +
                ", statusID=" + statusID +
                ", deliveryDate=" + deliveryDate +
                '}';
    }

    public class Builder {

        private Order order;

        public Builder(Order order) {
            this.order = order;
        }

        public Builder withID(Integer id) {
            order.id = id;
            return this;
        }

        public Builder withShippingAddress(long val) {
            order.shippingAddressID = val;
            return this;
        }

        public Builder withDeliveryAddress(long val) {
            order.deliveryAddressID = val;
            return this;
        }

        public Builder withCreationTimestamp(Timestamp val) {
            order.creationTime = val;
            return this;
        }

        public Builder withDescription(String val) {
            order.description = val;
            return this;
        }

        public Builder withClient(long val) {
            order.clientID = val;
            return this;
        }

        public Builder withConsignee(String val) {
            order.consignee = val;
            return this;
        }

        public Builder withDistance(double val) {
            order.distance = val;
            return this;
        }

        public Builder withLength(double val) {
            order.length = val;
            return this;
        }

        public Builder withHeight(double val) {
            order.height = val;
            return this;
        }

        public Builder withWidth(double val) {
            order.width = val;
            return this;
        }

        public Builder withWeight(double val) {
            order.weight = val;
            return this;
        }

        public Builder withVolume(double val) {
            order.volume = val;
            return this;
        }

        public Builder withFare(double val) {
            order.fare = val;
            return this;
        }

        public Builder withShippingStatus(long val) {
            order.statusID = val;
            return this;
        }

        public Builder withDeliveryDate(Timestamp val) {
            order.deliveryDate = val;
            return this;
        }

        public Order build() {
            return order;
        }
    }
}
