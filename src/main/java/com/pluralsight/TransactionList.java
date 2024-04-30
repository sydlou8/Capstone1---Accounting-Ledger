package com.pluralsight;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class TransactionList {
    public static ArrayList<Transaction> transactions = new ArrayList<>();

    // Fill Transactions if Transaction.cvs exists and has data
    // If doesn't exist make the directory
    public static void listFiller() {
        final String LOG_DIRECTORY= "logs";
        File directory = new File(LOG_DIRECTORY);
        if(!directory.exists()) {
            directory.mkdir();
            return;
        }
        File file = new File(LOG_DIRECTORY + "/transaction.csv");
        try {
            FileInputStream fileStream = new FileInputStream(file);
            Scanner fileScanner = new Scanner(fileStream);
            while (fileScanner.hasNext()){
                String[] fileInfo = fileScanner.nextLine().split("\\|");
                if(fileInfo[0].isEmpty()) break;
                Transaction transaction = new Transaction(fileInfo[0], fileInfo[1], fileInfo[2], fileInfo[3], Double.parseDouble(fileInfo[4]));
                transactions.add(transaction);
            }
        } catch (IOException e) {
            System.out.println("ERROR: " + e);
        }
    }

    // Log Transactions into transactions log
    public static void logger(Transaction transaction) {
        File file = new File("logs/transaction.csv");
        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            writer.printf("%s|%s|%s|%s|%4.2f\n",
                    transaction.getCurrentDate(),
                    transaction.getCurrentTime(),
                    transaction.getDescription(),
                    transaction.getVendor(),
                    transaction.getAmount());
        } catch (IOException e) {
            System.out.println("ERROR: " + e);
        }
    }

    // CRUD METHODS
    public static void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
    public static Transaction getTransaction(int index) {
        return transactions.get(index);
    }
    public static void removeTransaction(int index) {
        transactions.remove(index);
    }
    public static void filterTransactions(String start , String end, String description, String vendor, String amount) {
    // Collect filtered transactions based on user input
        ArrayList<Transaction> filteredTransactions = new ArrayList<>();

        transactions.stream()
                .filter(transaction -> (start.isEmpty() || transaction.getDate().isAfter(LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).minusDays(1))))
                .filter(transaction -> (end.isEmpty() || transaction.getDate().isBefore(LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).plusDays(1))))
                .filter(transaction -> (description.isEmpty() || transaction.getDescription().equalsIgnoreCase(description)))
                .filter(transaction -> (vendor.isEmpty() || transaction.getVendor().equalsIgnoreCase(vendor)))
                .filter(transaction -> (amount.isEmpty() || transaction.getAmount() == Double.parseDouble(amount)))
                .forEach(filteredTransactions::add);

        if (filteredTransactions.isEmpty()) {
            System.out.println("No Available transactions");
        } else {
            System.out.println("Filtered Transactions:");
            filteredTransactions.stream().sorted().forEach(Transaction::display);
        }
    }
    // DISPLAY METHODS -- change to a single method
    public static void displayAll () {
        transactions.forEach(Transaction::display);
    }
    public static void displayDeposits() {
        transactions.stream()
                .filter(transaction -> transaction.getAmount() > 0)
                .forEach(Transaction::display);
    }
    public static void displayPayments() {
        transactions.stream()
                .filter(transaction -> transaction.getAmount() < 0)
                .forEach(Transaction::display);
    }
}
