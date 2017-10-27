package main.java.com.ea.domain;

public class Item
{
    private String sku;
    private int quantity;
    private double price;
    private String notes;

    public Item(String sku, String notes)
    {
        this.sku = sku;
        this.notes = notes;
    }

    public Item(String sku, int quantity, double price) {
        this.sku = sku;
        this.quantity = quantity;
        this.price = price;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Item{" + "sku='" + sku + '\'' +", price=" + price +", quantity=" + quantity +'}';
    }
}
