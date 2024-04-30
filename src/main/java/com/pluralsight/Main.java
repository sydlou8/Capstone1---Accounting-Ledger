package com.pluralsight;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private static ArrayList<Transaction> transactions = new ArrayList<>();
    private static Scanner userInput = new Scanner(System.in);

    public static void main(String[] args) {
        // listFiller() --> fills transactions ArrayList if transaction.csv is already filled also creates a directory if does not exist.
        listFiller();
        getHomeScreen();
    }

    // SCREENS
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
                    getLedger();
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
    private static void getLedger() {
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
                    break;
                case "D":
                    System.out.println("-".repeat(60));
                    displayDeposits();
                    break;
                case "P":
                    System.out.println("-".repeat(60));
                    displayPayments();
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
        int selection;
        while(true) {
            LocalDateTime today = LocalDateTime.now();
            System.out.println("=".repeat(60));
            System.out.println(" ".repeat(23) + "REPORTS SCREEN" + " ".repeat(25));
            System.out.println("=".repeat(60));
            System.out.println("Please make a selection: ");

            System.out.println("\t[1] - Month To Date");
            System.out.println("\t[2] - Last Month");
            System.out.println("\t[3] - Year To Date");
            System.out.println("\t[4] - Last Year");
            System.out.println("\t[5] - Search By Vendor");
            System.out.println("\t[6] - Custom Search");
            System.out.println("\t[0] - Back");
            System.out.print("Your Selection: ");
            try {
                selection = Integer.parseInt(userInput.nextLine().strip());
                switch(selection) {
                    case 1:
                        System.out.println("-".repeat(60));
                        System.out.printf("MTD: $%5.2f\n", monthToDate(today));
                        break;
                    case 2:
                        System.out.println("-".repeat(60));
                        System.out.printf("Last Month: $%5.2f\n", lastMonth(today));
                        break;
                    case 3:
                        System.out.println("-".repeat(60));
                        System.out.printf("YTD: $%5.2f\n", yearToDate(today));
                        break;
                    case 4:
                        System.out.println("-".repeat(60));
                        System.out.printf("YTD: $%5.2f\n", lastYear(today));
                        break;
                    case 5:
                        System.out.println("-".repeat(60));
                        searchVendor();
                        break;
                    case 6:
                        System.out.println("-".repeat(60));
                        customSearch();
                        break;
                    case 0:
                        getLedger();
                        break;
                    default:
                        System.out.print("Please enter a proper selection: ");
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: " + e);
            }
        }
    }

    // LOG HANDLING
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

    // DISPLAY METHODS
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

    // SEARCH METHODS
    private static void searchVendor() {
        String input;
        System.out.print("Please Enter Vendor: ");
        input = userInput.nextLine().strip();
        System.out.println("-".repeat(60));
        transactions.stream()
                .filter(transaction -> transaction.getVendor().equalsIgnoreCase(input))
                .forEach(Transaction::display);
    }
    private static void customSearch() {
        final String SOD = " 00:00:00";
        final String EOD = " 23:59:59";
        System.out.println("Please enter information for search or hit [ENTER] to skip: ");

        System.out.print("Please enter start date (format: yyyy-MM-dd): ");
        String start = userInput.nextLine().strip();
        System.out.print("Please enter end date (format: yyyy-MM-dd): ");
        String end = userInput.nextLine().strip();
        System.out.print("Please enter description of transaction: ");
        String description = userInput.nextLine().strip();
        System.out.print("Please enter transaction vendor: ");
        String vendor = userInput.nextLine().strip();
        System.out.print("Please enter amount debited/deposited: ");
        String amount = userInput.nextLine().strip();

        // Collect filtered transactions based on user input
        ArrayList<Transaction> filteredTransactions = new ArrayList<>();

        transactions.stream()
                .filter(transaction -> (start.isEmpty() || transaction.getDate().isAfter(LocalDateTime.parse(start + SOD, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).minusDays(1))))
                .filter(transaction -> (end.isEmpty() || transaction.getDate().isBefore(LocalDateTime.parse(end + EOD, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).plusDays(1))))
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
    // REPORT METHODS
    private static double monthToDate(LocalDateTime today) {
        return transactions.stream()
                .filter(transaction -> transaction.getDate().getYear() == today.getYear())
                .filter(transaction -> transaction.getDate().getMonthValue() == today.getMonthValue())
                .filter(transaction -> transaction.getDate().getDayOfMonth() <= today.getDayOfMonth())
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
    private static double lastMonth(LocalDateTime today) {
        return transactions.stream()
                .filter(transaction -> transaction.getDate().getYear() == today.getYear())
                .filter(transaction -> transaction.getDate().getMonthValue() == today.getMonthValue()-1)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
    private static double yearToDate(LocalDateTime today) {
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
    private static double lastYear(LocalDateTime today) {
        return transactions.stream()
                .filter(transaction -> transaction.getDate().getYear() == today.getYear()-1)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
}