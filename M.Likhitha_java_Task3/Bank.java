package atmInterface;
import java.util.ArrayList;

public class Bank {

    private ArrayList<Account> accounts;

    public Bank() {

        accounts = new ArrayList<Account>();

        // Sample accounts
        accounts.add(new Account("user1", "1234", 10000));
        accounts.add(new Account("user2", "5678", 8000));
        accounts.add(new Account("user3", "1111", 5000));
    }

    // Find account using User ID
    public Account findAccount(String userId) {

        for (Account account : accounts) {

            if (account.getUserId().equals(userId)) {
                return account;
            }
        }

        return null;
    }

    // Validate User ID and PIN
    public Account login(String userId, String pin) {

        Account account = findAccount(userId);

        if (account != null && account.getPin().equals(pin)) {
            return account;
        }

        return null;
    }
}