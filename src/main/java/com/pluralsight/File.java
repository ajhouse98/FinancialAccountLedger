package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class File {
    private static final String tFile = "src/main/resources/transactions.csv";

    public static List<Ledger> uploadFile() {
        List<Ledger> files = new ArrayList<>();
        try {
            // Use file/bufferReader to grab file I want to display
            FileReader fileReader = new FileReader(tFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            //use bufferedReader.readLine to skip the first line.
            bufferedReader.readLine();

            String input = "";

            while ((input = bufferedReader.readLine()) != null) {
                String[] data = input.split("\\|");

                if (data.length >= 5) {
                    String date = data[0];
                    String time = data[1];
                    String description = data[2];
                    String vendor = data[3];
                    double amount = Double.parseDouble(data[4]);

                    Ledger ledger = new Ledger(date, time, description, vendor, amount);
                    files.add(ledger); // Added ledger object to the list
                } else {
                    System.out.println("Invalid data format: " + input);
                }
            }
            bufferedReader.close(); // Close the BufferReader after usage
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number: " + e.getMessage());
        }
        return files; // Return the list of Ledger objects
    }

    public static List<Ledger> getDeposits() {
        List<Ledger> deposits = new ArrayList<>();
        List<Ledger> allTrans = uploadFile(); // Get all transactions

        // Filter for deposits
        for (Ledger transaction : allTrans) {
            if (transaction.getDescription().equalsIgnoreCase("Deposit")) {
                deposits.add(transaction);
            }
        }
        return deposits;
    }

    public static List<Ledger> getpayments() {
        List<Ledger> payments = new ArrayList<>();
        List<Ledger> allTrans = uploadFile(); // Get all transactions

        // Filter for deposits
        for (Ledger transaction : allTrans) {
            if (transaction.getDescription().equalsIgnoreCase("Payment")) {
                payments.add(transaction);
            }
        }
        return payments;
    }
}

