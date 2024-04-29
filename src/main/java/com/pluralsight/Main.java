package com.pluralsight;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Transaction> transactions = new ArrayList<>();
    private static Scanner userInput = new Scanner(System.in);

    public static void main(String[] args) {
        // listFiller() --> fills transactions ArrayList if transaction.csv is already filled also creates a directory if does not exist.
        listFiller();
        getHomeScreen();
    }
    private static void getHomeScreen (){
        String selection;
        System.out.println("=".repeat(60));
        System.out.println(" ".repeat(25) + "HOME SCREEN" + " ".repeat(25));
        System.out.println("=".repeat(60));
        System.out.println("Please make a selection: ");

        System.out.println("\t[D] - Add Deposit");
        System.out.println("\t[P] - Make Payment");
        System.out.println("\t[L] - Show Ledger");
        System.out.println("\t[X] - Exit");
        System.out.print("Your Selection: ");
        //System.out.println("=".repeat(60));
        while(true) {
            selection = userInput.nextLine().strip().toUpperCase();
            switch (selection) {
                case "D":
                    System.out.println("=".repeat(60));
                    setTransactionInfo("Deposit");
                    System.out.println("=".repeat(60));
                    break;
                case "P":
                    System.out.println("=".repeat(60));
                    setTransactionInfo("Debit");
                    System.out.println("=".repeat(60));
                    break;
                case "L":
                    System.out.println("=".repeat(60));
                    getLedgerScreen();
                    System.out.println("=".repeat(60));
                    break;
                case "X":
                    System.out.println("-".repeat(60));
                    System.out.println("Thank you. Goodbye.");
                    System.out.println("-".repeat(60));
                    System.exit(0);
                default:
                    System.out.print("Please enter a proper selection: ");
            }
        }
    }
    private static void setTransactionInfo(String transactionType) {
        System.out.printf(" ".repeat(25)+"%s screen\n".toUpperCase(), transactionType);
        System.out.println("=".repeat(60));
        System.out.printf("Please enter %s information:\n", transactionType);
        System.out.println("-".repeat(60));
        LocalDateTime current = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        System.out.printf("Enter %s DESCRIPTION: ", transactionType);
        String description = userInput.nextLine().strip();
        System.out.printf("Enter %s VENDOR: ", transactionType);
        String vendor = userInput.nextLine().strip();
        System.out.printf("Enter %s AMOUNT: ", transactionType);
        double amount = userInput.nextDouble();
        //userInput.nextLine();
        //= userInput.nextLine().strip();
        Transaction transaction = new Transaction(
                dateFormatter.format(current),
                timeFormatter.format(current),
                description,
                vendor,
                amount);
        transactions.add(transaction);
        logger(transaction);
        System.out.println("-".repeat(60));
        System.out.println("Input another transaction: ");

        System.out.println("\t[Y] - Yes - Back to Home Screen");
        System.out.println("\t[N] - No - Exit Program");
        System.out.print("Your Selection: ");
        while(true) {
            userInput.nextLine();
            String selection = userInput.nextLine().strip().toUpperCase();
            switch (selection) {
                case "Y":
                    getHomeScreen();
                    break;
                case "N":
                    System.out.println("-".repeat(60));
                    System.out.println("Thank you. Goodbye.");
                    System.out.println("-".repeat(60));
                    System.exit(0);
                default:
                    System.out.print("Please enter a proper selection: ");
            }
        }
    }
    private static void listFiller() {
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
    private static void logger(Transaction transaction) {
       /* final String LOG_DIRECTORY= "logs";
        File directory = new File(LOG_DIRECTORY);
        if(!directory.exists()) directory.mkdir();*/
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
    private static void getLedgerScreen() {
        String selection;
        while(true) {
            System.out.println("=".repeat(60));
            System.out.println(" ".repeat(23) + "LEDGER SCREEN" + " ".repeat(25));
            System.out.println("=".repeat(60));
            System.out.println("Please make a selection: ");

            System.out.println("\t[A] - Show All Transactions");
            System.out.println("\t[D] - Show All Deposits");
            System.out.println("\t[P] - Show All Payments");
            System.out.println("\t[R] - Reports");
            System.out.println("\t[H] - Home");
            System.out.print("Your Selection: ");

            selection = userInput.nextLine().strip().toUpperCase();
            switch (selection){
                case "A":
                    System.out.println("-".repeat(60));
                    displayAll();
                    System.out.println("-".repeat(60));
                    break;
                case "D":
                    System.out.println("-".repeat(60));
                    displayDeposits();
                    System.out.println("-".repeat(60));
                    break;
                case "P":
                    System.out.println("-".repeat(60));
                    displayPayments();
                    System.out.println("-".repeat(60));
                    break;
                case "R":
                    getReports();
                    break;
                case "H":
                    getHomeScreen();
                    break;
                default:
                    break;
            }
        }
    }
    private static void getReports() {

    }
    private static void displayAll () {
        transactions.forEach(Transaction::display);

    }
    private static void displayDeposits() {
        transactions.stream()
                .filter(transaction -> transaction.getAmount() > 0)
                .forEach(Transaction::display);
    }
    private static void displayPayments() {
        transactions.stream()
                .filter(transaction -> transaction.getAmount() < 0)
                .forEach(Transaction::display);
    }
    private static void searchVendor() {

    }
}