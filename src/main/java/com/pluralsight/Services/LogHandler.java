package com.pluralsight.Services;

import com.pluralsight.Models.Transaction;
import com.pluralsight.Models.TransactionList;

import java.io.*;
import java.util.Scanner;

public class LogHandler {
    private final TransactionList transactions;
    public LogHandler(TransactionList transactions) {
        this.transactions = transactions;
    }
    public void listFiller() {
        final String LOG_DIRECTORY= "logs2";
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
                transactions.addTransaction(transaction);
            }
        } catch (IOException e) {
            System.out.println("ERROR: " + e);
        }
    }
    // Log Transactions into transactions log
    public void logger(Transaction transaction) {
        File file = new File("logs2/transaction.csv");
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
}
