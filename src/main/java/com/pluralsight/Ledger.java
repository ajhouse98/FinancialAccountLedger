package com.pluralsight;

// Create a Class called Ledger to set and get the values
// and set the format
public class Ledger {

private String date, time, description, vendor;
private double amount;

public Ledger(String date, String time, String description, String vendor, double amount) {
    this.date = date;
    this.time = time;
    this.description = description;
    this.vendor = vendor;
    this.amount = amount;
}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getAll() {
    return String.format("%s | %s | %s | %s | %.2f", date, time, description, vendor, amount);
    }

}
