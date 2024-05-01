package com.pluralsight.Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Stream;

public class TransactionList {
    private final ArrayList<Transaction> transactions;

    public TransactionList() {
        this.transactions = new ArrayList<>();
    }
    // Fill Transactions if Transaction.cvs exists and has data --> This should go in a different helper class!
    // If doesn't exist make the directory

    public Stream<Transaction> stream() {
        return transactions.stream();
    }
    // CRUD METHODS ***NOTE NOT ALL OF THESE WILL BE USED***
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
    public Transaction getTransaction(int index) {
        if (index >= 0 && index < transactions.size()) {
            return transactions.get(index);
        } else {
            throw new IndexOutOfBoundsException("Invalid transaction index");
        }
    }
    public void removeTransaction(int index) {
        if (index >= 0 && index < transactions.size()) {
            transactions.remove(index);
        } else {
            throw new IndexOutOfBoundsException("Invalid transaction index");
        }
    }
    public int getSize() {
        return transactions.size();
    }

    public void filterTransactions(String start , String end, String description, String vendor, String amount) {
    // Collect filtered transactions based on user input
        ArrayList<Transaction> filteredTransactions = new ArrayList<>();

        transactions.stream()
                .filter(transaction -> (start.isEmpty() || transaction.getDateTime().isAfter(LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).minusDays(1))))
                .filter(transaction -> (end.isEmpty() || transaction.getDateTime().isBefore(LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).plusDays(1))))
                .filter(transaction -> (description.isEmpty() || transaction.getDescription().equalsIgnoreCase(description)))
                .filter(transaction -> (vendor.isEmpty() || transaction.getVendor().equalsIgnoreCase(vendor)))
                .filter(transaction -> (amount.isEmpty() || transaction.getAmount() == Double.parseDouble(amount)))
                .forEach(filteredTransactions::add);

        if (filteredTransactions.isEmpty()) {
            System.out.println("No Available transactions");
        } else {
            System.out.println("Filtered Transactions:");
            filteredTransactions.forEach(Transaction::display);
        }
    }

    private ArrayList<Transaction> getDeposits() {
        ArrayList<Transaction> deposits = new ArrayList<>();
        transactions.stream().filter(Transaction::isDeposit).forEach(deposits::add);
        return deposits;
    }
    private ArrayList<Transaction> getDebits() {
        ArrayList<Transaction> debits = new ArrayList<>();
        transactions.stream().filter(Transaction::isPayment).forEach(debits::add);
        return debits;
    }
    // DISPLAY METHODS -- change to a single method
    public void displayAll() {
        transactions.forEach(Transaction::display);
    }
    public void displayDeposits() {
        getDeposits().forEach(Transaction::display);
    }
    public void displayPayments() {
        getDebits().forEach(Transaction::display);
    }
}