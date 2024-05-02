package com.pluralsight.Models;

import java.time.LocalDateTime;

public class Reports {
    private final TransactionList transactions;

    public Reports(TransactionList transactions) {
        this.transactions = transactions;
    }
    public double monthToDate(LocalDateTime today) {
        return transactions.stream()
                .filter(transaction -> transaction.getDateTime().getYear() == today.getYear())
                .filter(transaction -> transaction.getDateTime().getMonthValue() == today.getMonthValue())
                .filter(transaction -> transaction.getDateTime().getDayOfMonth() <= today.getDayOfMonth())
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
    public double lastMonth(LocalDateTime today) {
        return transactions.stream()
                .filter(transaction -> transaction.getDateTime().getYear() == today.getYear())
                .filter(transaction -> transaction.getDateTime().getMonthValue() == today.getMonthValue()-1)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
    public double yearToDate(LocalDateTime today) {
        // MTD + Beginning of Year to Before Current Month
        return (transactions.stream()
                .filter(transaction -> transaction.getDateTime().getYear() == today.getYear())
                .filter(transaction -> transaction.getDateTime().getMonthValue() == today.getMonthValue())
                .filter(transaction -> transaction.getDateTime().getDayOfMonth() <= today.getDayOfMonth())
                .mapToDouble(Transaction::getAmount)
                .sum()) + (transactions.stream()
                .filter(transaction -> transaction.getDateTime().getYear() == today.getYear())
                .filter(transaction -> transaction.getDateTime().getMonthValue() < today.getMonthValue())
                .mapToDouble(Transaction::getAmount)
                .sum()) ;
    }
    public double lastYear(LocalDateTime today) {
        return transactions.stream()
                .filter(transaction -> transaction.getDateTime().getYear() == today.getYear()-1)
                .mapToDouble(Transaction::getAmount)
                .sum();
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
