package main.java.com.ea.domain;

import java.util.Date;

public class TransDetail
{
    private int llc;
    private String transDate;
    private String supplier;
    private int entity;
    private int cc;
    private double total;
    private String notes;

    public TransDetail(int llc, String transDate, String supplier, int entity, int cc, double total, String notes)
    {
        this.llc = llc;
        this.transDate = transDate;
        this.supplier = supplier;
        this.entity = entity;
        this.cc = cc;
        this.total = total;
        this.notes = notes;
    }

    public int getLlc() {
        return llc;
    }

    public void setLlc(int llc) {
        this.llc = llc;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public int getEntity() {
        return entity;
    }

    public void setEntity(int entity) {
        this.entity = entity;
    }

    public int getCc() {
        return cc;
    }

    public void setCc(int cc) {
        this.cc = cc;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
