package com.pluralsight;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static com.pluralsight.Reports.*;

public class UserInterface {
    private static Scanner userInput = new Scanner(System.in);
    public static void getHomeScreen (){
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
    public static void setTransactionInfo(String transactionType) {
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
        TransactionList.transactions.add(transaction);
        TransactionList.logger(transaction);
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
    public static void getLedger() {
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
                    display(1);
                    break;
                case "D":
                    System.out.println("-".repeat(60));
                    display(2);
                    break;
                case "P":
                    System.out.println("-".repeat(60));
                    display(3);
                    break;
                case "R":
                    getReports();
                    break;
                case "H":
                    getHomeScreen();
                    break;
                default:
                    System.out.println("Please enter a proper response: ");
            }
        }
    }
    public static void getReports() {
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
                        System.out.print("Please Enter Vendor: ");
                        String input = userInput.nextLine().strip();
                        System.out.println("-".repeat(60));
                        searchVendor(input);
                        break;
                    case 6:
                        System.out.println("-".repeat(60));
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
                        customSearch(start, end, description, vendor, amount);
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
    // DISPLAY METHODS replace with searchTransaction class in TransactionList.java
    private static void display (int i) {
        switch (i){
            case 1:
                TransactionList.displayAll();
                break;
            case 2:
                TransactionList.displayDeposits();
                break;
            case 3:
                TransactionList.displayPayments();
                break;
        }
    }
}
