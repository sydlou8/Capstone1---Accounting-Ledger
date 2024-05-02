package com.pluralsight.Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.pluralsight.UI.UIColors.*;

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
        String output = String.format(
                PURPLE +"\t%-10s" + RESET +
                BLUE + "\t%-10s" + RESET +
                YELLOW + "\t%-30s" + RESET +
                CYAN + "\t%-20s\t" + RESET,
                currentDate,currentTime, description, vendor);
        String colorOutput = amount > 0 ? String.format(GREEN + "$\t%8.2f" + RESET, amount) : String.format(RED + "$\t%8.2f" + RESET, amount);
        System.out.println(output+colorOutput);
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
