public class Credit extends Account {
    // the maximum credit limit for the account
    private double creditLimit;

    // constructor to initialize a credit account with the specified account number and credit limit
    public Credit(String accountNumber, double creditLimit) {
        super(accountNumber, creditLimit);
        this.creditLimit = creditLimit;
    }

    // method to charge an amount to the credit account, within the credit limit
    public void charge(double amount) {
        if (balance - amount >= -creditLimit) {
            balance -= amount;
            System.out.println("Charge of $" + amount + " successful.");
        } else {
            System.out.println("Charge denied. Exceeds credit limit.");
        }
    }

    // method to deposit a payment towards the credit account balance (reduce debt)
    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount; // Reduces the debt (balance becomes less negative)
            System.out.println("Payment of $" + amount + " towards credit balance successful.");
            System.out.println("New Balance: $" + balance);
        } else {
            throw new IllegalArgumentException("Invalid payment amount.");
        }
    }

    // method to prevent withdrawals from the credit account
    @Override
    public void withdraw(double amount) {
        System.out.println("Withdrawals are not allowed from credit accounts.");
    }

    // method to get the remaining available credit
    public double getAvailableCredit() {
        return creditLimit + balance; // balance is negative, so this calculates available credit
    }

    // implementation of the abstract method to specify the account type
    @Override
    public String getAccountType() {
        return "Credit";
    }
}