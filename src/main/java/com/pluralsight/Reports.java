package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Reports {
    private static final String tFile = "src/main/resources/transactions.csv";

    public static void main(String[] args) {
        Reports reports = new Reports();
        reports.filterMTD(tFile);
    }

    public void filterMTD(String tFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(tFile))) {
            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;

                if (lineNumber == 1) {
                    continue; // Skip the first line
                }

                String[] sections = line.split("\\|");
                String dateStr = sections[0].trim();
                LocalDate transactionDate = LocalDate.parse(dateStr.split(" ")[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
                String currentMonthYear = currentDate.format(formatter);

                if (transactionDate.format(formatter).equals(currentMonthYear)) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void filterprevMonth(String tFile) {
        boolean foundTransaction = false; // Flag to track if any transaction is found for the previous month
        try (BufferedReader br = new BufferedReader(new FileReader(tFile))) {
            String line;
            int lineNumber = 0;

            LocalDate currentDate = LocalDate.now().minusMonths(1); // Get the date for the previous month
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
            String prevMonthYear = currentDate.format(formatter);

            while ((line = br.readLine()) != null) {
                lineNumber++;

                if (lineNumber == 1) {
                    continue; // Skip the first line
                }

                String[] parts = line.split("\\|");
                String dateStr = parts[0].trim();
                LocalDate transactionDate = LocalDate.parse(dateStr.split(" ")[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                if (transactionDate.format(formatter).equals(prevMonthYear)) {
                    System.out.println(line);
                    foundTransaction = true; // Set flag to true if any transaction is found
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print "Nothing to Report!" if no transaction is found for the previous month
        if (!foundTransaction) {
            System.out.println("Nothing to Report!");
        }
    }

    public void filterYTD(String tFile) {
        boolean foundTransaction = false;
        try (BufferedReader br = new BufferedReader(new FileReader(tFile))) {
            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;

                if (lineNumber == 1) {
                    continue; // Skip the first line
                }

                String[] sections = line.split("\\|");
                String dateStr = sections[0].trim();
                LocalDate transactionDate = LocalDate.parse(dateStr.split(" ")[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
                String currentYear = currentDate.format(formatter);

                if (transactionDate.format(formatter).equals(currentYear)) {
                    System.out.println(line);
                    foundTransaction = true; // Change to true if any transaction is found
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print "Nothing to Report!" if no transaction is found for the previous year
        if (!foundTransaction) {
            System.out.println("Nothing to Report!");
        }
    }

    public void filterprevYear(String tFile) {
        boolean foundTransaction = false; // track if any transaction is found for the previous year
        try (BufferedReader br = new BufferedReader(new FileReader(tFile))) {
            String line;
            int lineNumber = 0;

            LocalDate currentDate = LocalDate.now().minusYears(1); // Get the date for the previous year
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
            String prevYear = currentDate.format(formatter);

            while ((line = br.readLine()) != null) {
                lineNumber++;

                if (lineNumber == 1) {
                    continue; // Skip the first line
                }

                String[] parts = line.split("\\|");
                String dateStr = parts[0].trim();
                LocalDate transactionDate = LocalDate.parse(dateStr.split(" ")[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                if (transactionDate.format(formatter).equals(prevYear)) {
                    System.out.println(line);
                    foundTransaction = true; // Set flag to true if any transaction is found
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print "Nothing to Report!" if no transaction is found for the previous month
        if (!foundTransaction) {
            System.out.println("Nothing to Report!");
        }
    }

    public void byVendor(String tFile, String vendor) {
        boolean foundTransaction = false;
        try (BufferedReader br = new BufferedReader(new FileReader(tFile))) {
            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;

                if (lineNumber == 1) {
                    continue; // Skip the first line
                }

                String[] sections = line.split("\\|");
                
                String vendorStr = sections[3].trim(); // Vendor is at index 3
                if (!vendorStr.equalsIgnoreCase(vendor)) {
                    continue; // Skip this transaction if vendor does not match
                }

                // Output the transaction line
                System.out.println(line);
                foundTransaction = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print "No transactions found" if no transaction is found for the specified vendor
        if (!foundTransaction) {
            System.out.println("\nNo transactions found!");
        }
    }
}
