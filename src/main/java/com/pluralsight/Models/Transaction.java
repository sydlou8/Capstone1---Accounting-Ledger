package com.pluralsight.Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private String currentDate;
    private String currentTime;
    private String description;
    private String vendor;
    private double amount;

    public Transaction(String currentDate, String currentTime, String description, String vendor, double amount) {
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public String getCurrentDate() {
        return currentDate;
    }
    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }
    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
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
