package atmInterface;

public class Account {

    private String userId;
    private String pin;
    private double balance;

    public Account(String userId, String pin, double balance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
    }

    // Getters
    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    // Deposit money
    public void deposit(double amount) {
        balance = balance + amount;
    }

    // Withdraw money
    public boolean withdraw(double amount) {

        if (amount <= balance) {
            balance = balance - amount;
            return true;
        }

        return false;
    }
}