package atmInterface;

import java.util.ArrayList;
import java.util.Scanner;

public class Atm{

    private Bank bank;
    private Scanner scanner;

    
    private ArrayList<Transaction> transactions;

    public Atm(Bank bank) {

        this.bank = bank;
        scanner = new Scanner(System.in);
        transactions = new ArrayList<Transaction>();
    }

    // Start ATM
    public void start() {

        System.out.println("=================================");
        System.out.println("        WELCOME TO ATM");
        System.out.println("=================================");

        Account currentAccount = authenticate();

        if (currentAccount == null) {

            System.out.println("Access Denied.");
            System.out.println("Thank you for using ATM.");
            return;
        }

        System.out.println("\nLogin Successful!");
        System.out.println("Welcome, " + currentAccount.getUserId());

        showMenu(currentAccount);
    }

    // Authentication
    private Account authenticate() {

        int attempts = 0;

        while (attempts < 3) {

            System.out.print("\nEnter User ID: ");
            String userId = scanner.nextLine();

            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine();

            Account account = bank.login(userId, pin);

            if (account != null) {
                return account;
            }

            attempts++;

            System.out.println("Incorrect User ID or PIN.");

            if (attempts < 3) {
                System.out.println("Attempts remaining: " + (3 - attempts));
            }
        }

        System.out.println("\nYou have entered incorrect details 3 times.");
        System.out.println("Access denied.");

        return null;
    }

    // Main menu
    private void showMenu(Account currentAccount) {

        while (true) {

            System.out.println("\n=================================");
            System.out.println("           ATM MENU");
            System.out.println("=================================");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.println("=================================");

            System.out.print("Enter your choice: ");

            int choice;

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {

                case 1:
                    transactionHistory();
                    break;

                case 2:
                    withdraw(currentAccount);
                    break;

                case 3:
                    deposit(currentAccount);
                    break;

                case 4:
                    transfer(currentAccount);
                    break;

                case 5:
                    System.out.println("\nThank you for using ATM.");
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Transaction History
    private void transactionHistory() {

        System.out.println("\n=================================");
        System.out.println("       TRANSACTION HISTORY");
        System.out.println("=================================");

        if (transactions.isEmpty()) {

            System.out.println("No transactions in this session.");

        } else {

            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }

        System.out.println("=================================");
    }

    // Withdraw
    private void withdraw(Account account) {

        System.out.print("\nEnter withdrawal amount: ₹");

        double amount;

        try {
            amount = Double.parseDouble(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid amount.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Amount must be greater than 0.");
            return;
        }

        // Balance check
        if (amount > account.getBalance()) {

            System.out.println("Insufficient Funds.");
            System.out.println("Available Balance: ₹" + account.getBalance());
            return;
        }

        account.withdraw(amount);

        transactions.add(
                new Transaction(
                        "WITHDRAW",
                        amount,
                        "Cash withdrawn"
                )
        );

        System.out.println("Withdrawal successful.");
        System.out.println("Withdrawn Amount: ₹" + amount);
        System.out.println("Remaining Balance: ₹" + account.getBalance());
    }

    // Deposit
    private void deposit(Account account) {

        System.out.print("\nEnter deposit amount: ₹");

        double amount;

        try {
            amount = Double.parseDouble(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid amount.");
            return;
        }

        if (amount <= 0) {

            System.out.println("Amount must be greater than 0.");
            return;
        }

        account.deposit(amount);

        transactions.add(
                new Transaction(
                        "DEPOSIT",
                        amount,
                        "Cash deposited"
                )
        );

        System.out.println("Deposit successful.");
        System.out.println("Deposited Amount: ₹" + amount);
        System.out.println("Current Balance: ₹" + account.getBalance());
    }

    // Transfer
    private void transfer(Account sender) {

        System.out.print("\nEnter recipient Account/User ID: ");
        String recipientId = scanner.nextLine();

        Account receiver = bank.findAccount(recipientId);

        if (receiver == null) {

            System.out.println("Recipient account not found.");
            return;
        }

        // Don't allow transfer to same account
        if (sender.getUserId().equals(receiver.getUserId())) {

            System.out.println("You cannot transfer money to your own account.");
            return;
        }

        System.out.print("Enter transfer amount: ₹");

        double amount;

        try {
            amount = Double.parseDouble(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid amount.");
            return;
        }

        if (amount <= 0) {

            System.out.println("Amount must be greater than 0.");
            return;
        }

        // Balance validation
        if (amount > sender.getBalance()) {

            System.out.println("Insufficient Funds.");
            System.out.println("Available Balance: ₹" + sender.getBalance());
            return;
        }

        // Remove money from sender
        sender.withdraw(amount);

        // Add money to receiver
        receiver.deposit(amount);

        // Store transaction
        transactions.add(
                new Transaction(
                        "TRANSFER",
                        amount,
                        "Transferred to " + receiver.getUserId()
                )
        );

        System.out.println("\nTransfer successful.");
        System.out.println("Transferred Amount: ₹" + amount);
        System.out.println("Recipient: " + receiver.getUserId());
        System.out.println("Remaining Balance: ₹" + sender.getBalance());
    }
}