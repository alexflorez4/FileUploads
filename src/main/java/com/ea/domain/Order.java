package main.java.com.ea.domain;

public class Order
{
    private int orderNumber;
    private String trackingId;
    private String status;

    public Order(int orderNumber, String trackingId) {
        this.orderNumber = orderNumber;
        this.trackingId = trackingId;
    }

    public Order(int orderNumber, String trackingId, String status) {
        this.orderNumber = orderNumber;
        this.trackingId = trackingId;
        this.status = status;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
