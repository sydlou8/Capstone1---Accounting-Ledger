package com.pluralsight.Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private final String currentDate;
    private final String currentTime;
    private final String description;
    private final String vendor;
    private final double amount;

    public Transaction(String currentDate, String currentTime, String description, String vendor, double amount) {
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    // Getters
    public String getCurrentDate() {
        return currentDate;
    }
    public String getCurrentTime() {
        return currentTime;
    }
    public String getDescription() {
        return description;
    }
    public String getVendor() {
        return vendor;
    }
    public double getAmount() {
        return amount;
    }
    // Setters --> UNUSED --> Deleted
    // Methods
    public void display() {
        String output = String.format("\t%-10s\t%-10s\t%-30s\t%-20s\t$\t%8.2f",
                currentDate,currentTime, description, vendor, amount);
        System.out.println(output);
    }
    public LocalDateTime getDateTime() {
        String currentDateTime = String.format("%s %s", currentDate, currentTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(currentDateTime, formatter);
    }
    public boolean isDeposit() {
        return amount > 0;
    }
    public boolean isPayment() {
        return amount < 0;
    }
}
