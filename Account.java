public abstract class Account {
    // Protected attributes to store balance and account number
    protected double balance;
    protected String accountNumber;

    // Constructor to initialize account number and balance
    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
    
    // Method to inquire the balance of the account
    public double inquireBalance() {
        return balance;
    }
    
    // Method to deposit a specified amount into the account
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposit of $" + amount + " successful.");
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    // Method to withdraw a specified amount from the account
    public void withdraw(double amount) {
        if (balance >= amount && amount > 0) {
            balance -= amount;
            System.out.println("Withdrawal of $" + amount + " successful.");
        } else {
            System.out.println("Insufficient funds or invalid amount.");
        }
    }

    public void transferFunds(Account toAccount, double amount) {
        if (balance >= amount && amount > 0) {
            withdraw(amount); // Withdraw from this account
            toAccount.deposit(amount); // Deposit into the destination account
            System.out.println("Transfer of $" + amount + " to account " + toAccount.accountNumber + " successful.");
        } else {
            System.out.println("Transfer failed. Insufficient funds or invalid amount.");
        }
    }
    
    // Abstract method to get the type of account (to be implemented by subclasses)
    public abstract String getAccountType();

    // Getter for account number
    public String getAccountNumber() {
        return accountNumber;
    }
}

