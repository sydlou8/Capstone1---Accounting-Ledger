package com.pluralsight;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static com.pluralsight.TransactionList.filterTransactions;
import static com.pluralsight.TransactionList.transactions;

public class Reports {
    public static double monthToDate(LocalDateTime today) {
        return transactions.stream()
                .filter(transaction -> transaction.getDate().getYear() == today.getYear())
                .filter(transaction -> transaction.getDate().getMonthValue() == today.getMonthValue())
                .filter(transaction -> transaction.getDate().getDayOfMonth() <= today.getDayOfMonth())
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
    public static double lastMonth(LocalDateTime today) {
        return transactions.stream()
                .filter(transaction -> transaction.getDate().getYear() == today.getYear())
                .filter(transaction -> transaction.getDate().getMonthValue() == today.getMonthValue()-1)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
    public static double yearToDate(LocalDateTime today) {
        // MTD + Beginning of Year to Before Current Month
        return (transactions.stream()
                .filter(transaction -> transaction.getDate().getYear() == today.getYear())
                .filter(transaction -> transaction.getDate().getMonthValue() == today.getMonthValue())
                .filter(transaction -> transaction.getDate().getDayOfMonth() <= today.getDayOfMonth())
                .mapToDouble(Transaction::getAmount)
                .sum()) + (transactions.stream()
                .filter(transaction -> transaction.getDate().getYear() == today.getYear())
                .filter(transaction -> transaction.getDate().getMonthValue() < today.getMonthValue())
                .mapToDouble(Transaction::getAmount)
                .sum()) ;
    }
    public static double lastYear(LocalDateTime today) {
        return transactions.stream()
                .filter(transaction -> transaction.getDate().getYear() == today.getYear()-1)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
    // Search Reports
    public static void searchVendor(String input) {
        transactions.stream()
                .filter(transaction -> transaction.getVendor().equalsIgnoreCase(input))
                .sorted()
                .forEach(Transaction::display);
    }
    public static void customSearch(String start, String end, String description, String vendor, String amount) {
        final String SOD = " 00:00:00";
        final String EOD = " 23:59:59";
        filterTransactions(start+SOD, end+EOD, description,vendor,amount);
    }
}
