package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AccountLedger {
    private static final String tFile = "src/main/resources/transactions.csv";

    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);

        while (true) {

            System.out.println("""
                                        
                    Home Page
                                        
                    1. Make a deposit
                    2. Make a payment
                    3. Ledger
                    4. Exit
                                        
                    """);

            System.out.print("Please enter a number to get started: ");
            int choice = userInput.nextInt();

            if (choice == 1) {
                System.out.print("Please enter your name: ");
                userInput.nextLine(); // Consume a line
                String name = userInput.nextLine();

                System.out.print("Please add deposit amount: ");
                double deposit = userInput.nextDouble();

                // Format deposit
                String depositFormat = String.format("%.2f", 1 * deposit);

                try {
                    StringBuilder existingFile = new StringBuilder();
                    BufferedReader reader = new BufferedReader(new FileReader(tFile));

                    String input;
                    while ((input = reader.readLine()) != null) {
                        existingFile.append(input).append("\n");
                    }
                    // Stop reading to file
                    reader.close();

                    // Append deposit to file
                    String userDeposit = savedRecord(name, depositFormat);

                    existingFile.append(userDeposit).append("\n");

                    // Write updated data back to file
                    BufferedWriter writer = new BufferedWriter(new FileWriter(tFile));
                    writer.write(existingFile.toString());
                    // Stop writing to file
                    writer.close();

                    System.out.printf("Deposit of " + depositFormat + " for " + name + " was made successfully!\n");

                } catch (IOException e) {
                    System.err.println("Error: " + e.getMessage());
                }
            } else if (choice == 2) {
                System.out.print("Please enter your name: ");
                userInput.nextLine(); // Consume a line
                String name = userInput.nextLine();

                System.out.print("Please enter payment amount: ");
                double payment = userInput.nextDouble();
                // Format payment
                String paymentFormat = String.format("%.2f", -1 * payment);

                try {
                    StringBuilder existingFile = new StringBuilder();
                    BufferedReader reader = new BufferedReader(new FileReader(tFile));

                    String input;
                    while ((input = reader.readLine()) != null) {
                        existingFile.append(input).append("\n");
                    }
                    // Stop reading to file
                    reader.close();

                    // Append deposit to file
                    String userPayment = savedRecord(name, paymentFormat);

                    existingFile.append(userPayment).append("\n");

                    // Write updated data back to file
                    BufferedWriter writer = new BufferedWriter(new FileWriter(tFile));
                    writer.write(existingFile.toString());
                    // Stop writing to file
                    writer.close();

                    System.out.println("Payment of " + paymentFormat + " for " + name + " was made successfully!\n");

                } catch (IOException e) {
                    System.err.println("Error: " + e.getMessage());
                }

            } else if (choice == 3) {

                System.out.print("""
                                                
                        Welcome to your Ledger! 
                                                
                        1. Show All Transactions
                        2. Show Deposits
                        3. Show Payments
                        4. Reports
                                                
                        """);
                System.out.print("Please select from one of the following: ");
                int option = userInput.nextInt();

                if (option == 1) {
                    doLedger(userInput);

                } else if (option == 2) {
                    doAllDepo(userInput);

                } else if (option == 3) {
                    doAllPay(userInput);

                } else if (option == 4) {
                    doRepo(userInput);

                } else {
                    System.out.println("\nInvalid input, please try again \n");
                }

            } else if (choice == 4) {
                System.exit(0);

            } else {
                System.out.println("\nInvalid input, please try again \n");
            }
        }
    }

    private static void doLedger(Scanner userInput) {
        // Create an instance of the File class
        File fileUploader = new File();
        //use the uploadFile method
        List<Ledger> uploadedLedgers = fileUploader.uploadFile();
        Collections.sort(uploadedLedgers, new Comparator<Ledger>() {
            @Override
            public int compare(Ledger l1, Ledger l2) {
                // Compares date from newest to oldest
                return l2.getDate().compareTo(l1.getDate());
            }
        });
        //Print the sorted list
        for (Ledger ledger : uploadedLedgers) {
            System.out.println(ledger.getAll() + "\n");
        }
    }

    private static void doAllDepo(Scanner userInput) {
        String fileName = tFile;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                // check to see if a line contains the word 'deposit'
                if (line.contains("Deposit")) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void doAllPay(Scanner userInput) {
        String fileName = tFile;

        try (BufferedReader br = new BufferedReader(new FileReader(tFile))) {
            String line;

            while ((line = br.readLine()) != null) {
                // check to see if a line contains the word 'paid'
                if (line.contains("Paid")) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        }

    private static void doRepo(Scanner userInput) {
        System.out.println(""" 
                
                Reports
                
                1. Month to Date
                2. Previous Month
                3. Year to Date
                4. Previous Year 
                5. Search by Vendor
                
                """);
        System.out.print("Please choose one of the following options: ");
        int option = userInput.nextInt();

        if (option == 1) {
            Reports reports = new Reports();
            reports.filterMTD(tFile);

        } else if (option == 2) {
            Reports reports = new Reports();
            reports.filterprevMonth(tFile);

        }else if (option == 3) {
            Reports reports = new Reports();
            reports.filterYTD(tFile);

        }else if (option == 4) {
            Reports reports = new Reports();
            reports.filterprevYear(tFile);

        }else if (option == 5) {
            System.out.print("Please enter the vendor name: ");
            String vendor = userInput.nextLine();
            userInput.nextLine(); // Consume newline character

            Reports reports = new Reports();
            reports.byVendor(tFile, vendor);
        }

    }
    private static String savedRecord(String name, String depositAmount) {
        // get date and time
        LocalDateTime today = LocalDateTime.now();

        // Specify format of Date and Time
        DateTimeFormatter fmt =
                DateTimeFormatter.ofPattern("yyyy-MM-dd | HH:mm:ss");
        String formattedDate = today.format(fmt);
        return formattedDate + " | Deposit | " + name + " | " + depositAmount;
    }
}