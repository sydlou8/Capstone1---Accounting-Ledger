package com.pluralsight.UI;

import com.pluralsight.Services.Reports;
import com.pluralsight.Models.Transaction;
import com.pluralsight.Models.TransactionList;
import com.pluralsight.Services.LogHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static com.pluralsight.UI.UIColors.*;

public class UserInterface {
    private final Scanner userInput ;
    private final TransactionList transactions;
    private final LogHandler logHandler;

    public UserInterface(Scanner userInput, TransactionList transactions, LogHandler logHandler) {
        this.userInput = userInput;
        this.transactions = transactions;
        this.logHandler = logHandler;
    }
    public int getHomeScreen (){
        String selection;
        while(true) {
            System.out.println(CYAN + "=".repeat(100));
            System.out.println(" ".repeat(45) + "HOME SCREEN");
            System.out.println("=".repeat(100) + RESET);
            System.out.println("Please make a selection: ");

            System.out.println("\t[D] - Add Deposit");
            System.out.println("\t[P] - Make Payment");
            System.out.println("\t[L] - Show Ledger");
            System.out.println("\t[X] - Exit");
            System.out.print("Your Selection: ");
            selection = userInput.nextLine().strip().toUpperCase();
            switch (selection) {
                case "D":
                    System.out.println(GREEN + "=".repeat(100));
                    setTransactionInfo("Deposit");
                    break;
                case "P":
                    System.out.println(RED + "=".repeat(100));
                    setTransactionInfo("Debit");
                    break;
                case "L":
                    getLedger();
                    break;
                case "X":
                    System.out.println("-".repeat(100));
                    System.out.println("Thank you. Goodbye.");
                    System.out.println("-".repeat(100));
                    return 0;
                default:
                    System.out.println("Please enter a proper selection: ");
            }

        }
    }
    private void setTransactionInfo(String transactionType) {
        System.out.printf(" ".repeat(45)+"%s screen\n".toUpperCase(), transactionType);
        System.out.println("=".repeat(100) + RESET);
        System.out.printf("Please enter %s information:\n", transactionType);
        System.out.println("-".repeat(100));
        LocalDateTime current = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        System.out.printf("Enter %s DESCRIPTION: ", transactionType);
        String description = userInput.nextLine().strip();
        System.out.printf("Enter %s VENDOR: ", transactionType);
        String vendor = userInput.nextLine().strip();
        String transactionAmount = transactionType.equalsIgnoreCase("Debit") ?
                String.format("Enter %s AMOUNT: $\t-", transactionType) :
                String.format("Enter %s AMOUNT: $\t ", transactionType);
        System.out.print(transactionAmount);
        double amount = transactionType.equalsIgnoreCase("Debit") ?
                userInput.nextDouble() * -1 : userInput.nextDouble();

        Transaction transaction = new Transaction(
                dateFormatter.format(current),
                timeFormatter.format(current),
                description,
                vendor,
                amount);
        transactions.addTransaction(transaction);
        logHandler.logger(transaction);
        System.out.println("-".repeat(100));
        System.out.println("Input another transaction: ");

        System.out.println("\t[Y] - Yes - Back to Home Screen");
        System.out.println("\t[N] - No - Exit Program");
        System.out.print("Your Selection: ");
        while(true) {
            userInput.nextLine();
            String selection = userInput.nextLine().strip().toUpperCase();
            switch (selection) {
                case "Y":
                    return;
                case "N":
                    System.out.println("-".repeat(100));
                    System.out.println("Thank you. Goodbye.");
                    System.out.println("-".repeat(100));
                    System.exit(0);
                default:
                    System.out.print("Please enter a proper selection: ");
            }
        }
    }
    private void getLedger() {
        String selection;
        while(true) {
            System.out.println(PURPLE + "=".repeat(100));
            System.out.println(" ".repeat(44) + "LEDGER SCREEN" );
            System.out.println("=".repeat(100) + RESET);
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
                    System.out.println("-".repeat(100));
                    transactions.displayAll();
                    break;
                case "D":
                    System.out.println("-".repeat(100));
                    transactions.displayDeposits();
                    break;
                case "P":
                    System.out.println("-".repeat(100));
                    transactions.displayPayments();
                    break;
                case "R":
                    getReports();
                    break;
                case "H":
                    return;
                default:
                    System.out.println("Please enter a proper response: ");
            }
        }
    }
    private void getReports() {
        int selection;
        while(true) {
            LocalDateTime today = LocalDateTime.now();
            System.out.println(YELLOW + "=".repeat(100));
            System.out.println(" ".repeat(44) + "REPORTS SCREEN" );
            System.out.println("=".repeat(100) + RESET);
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
                Reports reports = new Reports(transactions);
                selection = Integer.parseInt(userInput.nextLine().strip());
                switch(selection) {
                    case 1:
                        System.out.println("-".repeat(100));
                        double mtd = reports.monthToDate(today);
                        if (mtd > 0) {
                            System.out.printf(GREEN + "MTD: $\t %5.2f\n" + RESET, mtd);
                        } else {
                            System.out.printf(RED + "MTD: $\t %5.2f\n" + RESET, mtd);
                        }
                        break;
                    case 2:
                        System.out.println("-".repeat(100));
                        double prevMonth = reports.lastMonth(today);
                        if (prevMonth > 0) {
                            System.out.printf(GREEN + "Last Month: $\t %5.2f\n" + RESET, prevMonth);
                        } else {
                            System.out.printf(RED + "Last Month: $\t %5.2f\n" + RESET, prevMonth);
                        }
                        break;
                    case 3:
                        System.out.println("-".repeat(100));
                        double ytd = reports.yearToDate(today);
                        if (ytd > 0) {
                            System.out.printf(GREEN + "YTD: $\t %5.2f\n" + RESET, ytd);
                        } else {
                            System.out.printf(RED + "YTD: $\t %5.2f\n" + RESET, ytd);
                        }
                        break;
                    case 4:
                        System.out.println("-".repeat(100));
                        double prevYear = reports.lastYear(today);
                        if (prevYear > 0) {
                            System.out.printf(GREEN + "Last Year: $\t %5.2f\n" + RESET, prevYear);
                        } else {
                            System.out.printf(RED + "Last Year: $\t %5.2f\n" + RESET, prevYear);
                        }
                        break;
                    case 5:
                        System.out.println("-".repeat(100));
                        System.out.print("Please Enter Vendor: ");
                        String input = userInput.nextLine().strip();
                        System.out.println("-".repeat(100));
                        reports.searchVendor(input);
                        break;
                    case 6:
                        System.out.println("-".repeat(100));
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
                        reports.customSearch(start, end, description, vendor, amount);
                        break;
                    case 0:
                        return;
                    default:
                        System.out.print("Please enter a proper selection: ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a proper response: ");
            }
        }
    }
}
