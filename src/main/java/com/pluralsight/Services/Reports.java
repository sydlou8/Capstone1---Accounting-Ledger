package com.pluralsight.Services;

import com.pluralsight.Models.Transaction;
import com.pluralsight.Models.TransactionList;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Reports {
    private final TransactionList transactions;

    public Reports(TransactionList transactions) {
        this.transactions = transactions;
    }

    // LAST MINUTE UPDATES: I wanted to show all the transactions involved with MTD/YTD/lastMonth/lastYear
    // I was inspired by peers.
    // original code is commented out.

    public double monthToDate(LocalDateTime today) {
        ArrayList<Transaction> filtered = new ArrayList<>();
        transactions.stream()
                .filter(transaction -> transaction.getDateTime().getYear() == today.getYear())
                .filter(transaction -> transaction.getDateTime().getMonthValue() == today.getMonthValue())
                .filter(transaction -> transaction.getDateTime().getDayOfMonth() <= today.getDayOfMonth())
                .forEach(transaction -> {
                    filtered.add(transaction);
                    transaction.display();
                });
        System.out.println();
        return filtered.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
        /*return transactions.stream()
                .filter(transaction -> transaction.getDateTime().getYear() == today.getYear())
                .filter(transaction -> transaction.getDateTime().getMonthValue() == today.getMonthValue())
                .filter(transaction -> transaction.getDateTime().getDayOfMonth() <= today.getDayOfMonth())
                .mapToDouble(Transaction::getAmount)
                .sum();*/
    }
    public double lastMonth(LocalDateTime today) {
        ArrayList<Transaction> filtered = new ArrayList<>();
        transactions.stream()
                .filter(transaction -> transaction.getDateTime().getYear() == today.getYear())
                .filter(transaction -> transaction.getDateTime().getMonthValue() == today.getMonthValue()-1)
                .forEach(transaction -> {
                    filtered.add(transaction);
                    transaction.display();
                });
        System.out.println();
        return filtered.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
        /*return transactions.stream()
                .filter(transaction -> transaction.getDateTime().getYear() == today.getYear())
                .filter(transaction -> transaction.getDateTime().getMonthValue() == today.getMonthValue()-1)
                .mapToDouble(Transaction::getAmount)
                .sum();*/
    }
    public double yearToDate(LocalDateTime today) {
        // MTD + Beginning of Year to Before Current Month
        ArrayList<Transaction> filtered = new ArrayList<>();
        // Beginning of Year Before current Month
        transactions.stream()
                .filter(transaction -> transaction.getDateTime().getYear() == today.getYear())
                .filter(transaction -> transaction.getDateTime().getMonthValue() < today.getMonthValue())
                .forEach(transaction -> {
                    filtered.add(transaction);
                    transaction.display();
                });
        // MTD
        transactions.stream()
                .filter(transaction -> transaction.getDateTime().getYear() == today.getYear())
                .filter(transaction -> transaction.getDateTime().getMonthValue() == today.getMonthValue())
                .filter(transaction -> transaction.getDateTime().getDayOfMonth() <= today.getDayOfMonth())
                .forEach(transaction -> {
                    filtered.add(transaction);
                    transaction.display();
                });
        System.out.println();
        return filtered.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
        /*return (transactions.stream()
                .filter(transaction -> transaction.getDateTime().getYear() == today.getYear())
                .filter(transaction -> transaction.getDateTime().getMonthValue() == today.getMonthValue())
                .filter(transaction -> transaction.getDateTime().getDayOfMonth() <= today.getDayOfMonth())
                .mapToDouble(Transaction::getAmount)
                .sum()) + (transactions.stream()
                .filter(transaction -> transaction.getDateTime().getYear() == today.getYear())
                .filter(transaction -> transaction.getDateTime().getMonthValue() < today.getMonthValue())
                .mapToDouble(Transaction::getAmount)
                .sum()) ;*/
    }
    public double lastYear(LocalDateTime today) {
        ArrayList<Transaction> filtered = new ArrayList<>();
        // Beginning of Year Before current Month
        transactions.stream()
                .filter(transaction -> transaction.getDateTime().getYear() == today.getYear()-1)
                .forEach(transaction -> {
                    filtered.add(transaction);
                    transaction.display();
                });
        return filtered.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
        /*return transactions.stream()
                .filter(transaction -> transaction.getDateTime().getYear() == today.getYear()-1)
                .mapToDouble(Transaction::getAmount)
                .sum();*/
    }
    // Search Reports
    public void searchVendor(String input) {
        transactions.stream()
                .filter(transaction -> transaction.getVendor().equalsIgnoreCase(input))
                .forEach(Transaction::display);
    }
    public void customSearch(String start, String end, String description, String vendor, String amount) {
        final String SOD = " 00:00:00";
        final String EOD = " 23:59:59";
        System.out.println("-".repeat(100));
        if(start.isEmpty() && end.isEmpty()){
            transactions.filterTransactions(start, end, description,vendor,amount);
        } else if (start.isEmpty()) {
            transactions.filterTransactions(start, end+EOD, description,vendor,amount);
        } else if (end.isEmpty()) {
            transactions.filterTransactions(start+SOD, end, description,vendor,amount);
        } else {
            transactions.filterTransactions(start+SOD, end+EOD, description,vendor,amount);
        }
    }
}
