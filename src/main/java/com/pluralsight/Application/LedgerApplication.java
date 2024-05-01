package com.pluralsight.Application;

import com.pluralsight.Services.LogHandler;
import com.pluralsight.Models.TransactionList;
import com.pluralsight.UI.UserInterface;

import java.util.Scanner;

public class LedgerApplication {

    public static void run() {
        while(true) {
            Scanner scanner = new Scanner(System.in);
            TransactionList transactionList = new TransactionList();
            LogHandler lgoHandler = new LogHandler(transactionList);

            // listFiller() --> fills transactions ArrayList if transaction.csv is already filled also creates a directory if does not exist.
            lgoHandler.listFiller();

            UserInterface ui = new UserInterface(scanner, transactionList, lgoHandler);
            int choice = ui.getHomeScreen();
            scanner.close();

            System.exit(choice);
        }
    }
}
