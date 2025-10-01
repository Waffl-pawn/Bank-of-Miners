import java.util.ArrayList;
import java.util.Date;

public class Customer extends Person {
    private ArrayList<Account> accounts;

    public Customer(String id, String firstName, String lastName, Date dob, String address, String phoneNumber) {
        super(id, firstName, lastName, dob, address, phoneNumber);
        accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        // Check for duplicate accounts
        if (findAccountOfType(account.getClass().getSimpleName()) != null) {
            System.out.println("Account of type " + account.getClass().getSimpleName() + " already exists for this customer.");
            return;
        }
        accounts.add(account);
    }

    public Account findAccountOfType(String accountType) {
        for (Account account : accounts) {
            if (account.getClass().getSimpleName().equalsIgnoreCase(accountType)) {
                return account;
            }
        }
        return null;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public Account findAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public boolean removeAccount(String accountNumber) {
        Account accountToRemove = findAccount(accountNumber);
        if (accountToRemove != null) {
            accounts.remove(accountToRemove);
            return true;
        }
        return false;
    }
}




