package com.epam.delivery.db.entities.bean;

import com.epam.delivery.db.entities.Entity;

import java.sql.Timestamp;
import java.util.Map;

/**
 * Order entity.
 */
public class OrderBean extends Entity {
    private static final long serialVersionUID = 1255434001425151410L;

    private LocalityBean shippingAddress;
    private LocalityBean deliveryAddress;
    private Timestamp creationTime;
    private long clientID;
    private String client;
    private String consignee;
    private String description;
    private double distance;
    private double length;
    private double height;
    private double width;
    private double weight;
    private double volume;
    private double fare;
    private long statusId;
    private Map<String,String> status;
    private Timestamp deliveryDate;

    public OrderBean() {
    }

    public LocalityBean getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(LocalityBean shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public LocalityBean getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(LocalityBean deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public long getClientID() {
        return clientID;
    }

    public void setClientID(long clientID) {
        this.clientID = clientID;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
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

    public long getStatusId() {
        return statusId;
    }

    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }

    public Map<String, String> getStatus() {
        return status;
    }

    public void setStatus(Map<String, String> status) {
        this.status = status;
    }

    public Timestamp getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Timestamp deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "shippingAddress=" + shippingAddress +
                ", deliveryAddress=" + deliveryAddress +
                ", creationTime=" + creationTime +
                ", clientID=" + clientID +
                ", client='" + client + '\'' +
                ", consignee='" + consignee + '\'' +
                ", description='" + description + '\'' +
                ", distance=" + distance +
                ", length=" + length +
                ", height=" + height +
                ", width=" + width +
                ", weight=" + weight +
                ", volume=" + volume +
                ", fare=" + fare +
                ", status=" + status +
                ", deliveryDate=" + deliveryDate +
                "} " + super.toString();
    }
}
