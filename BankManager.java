import java.util.Scanner; // Import Scanner for user input

public class BankManager {
    private BinarySearchTree customerTree;
    private Scanner scanner; // Create a Scanner instance for user input

    public BankManager(BinarySearchTree customerTree) {
        this.customerTree = customerTree;
        this.scanner = new Scanner(System.in); // Initialize Scanner
    }

    // Method to open a new account for a customer
    public void openAccount(String customerId, String accountType, double initialDeposit) {
        Customer customer = customerTree.findCustomer(customerId);
        if (customer == null) {
            System.out.println("Customer not found. Please create a customer account first.");
            return;
        }
    
        // Check for existing account of the same type
        if (customer.findAccountOfType(accountType) != null) {
            System.out.println("Customer already has an account of type " + accountType + ". No new account opened.");
            return;
        }
    
        String accountNumber = generateUniqueAccountNumber();
        Account newAccount;
    
        // Determine account type and create the corresponding account
        switch (accountType.toLowerCase()) {
            case "checking":
                newAccount = new Checking(accountNumber, initialDeposit);
                break;
            case "saving":
                newAccount = new Saving(accountNumber, initialDeposit);
                break;
            case "credit":
                // Prompt for the credit limit
                System.out.print("Set the credit limit (negative value): ");
                double creditLimit;
                while (true) {
                    try {
                        creditLimit = Double.parseDouble(scanner.nextLine());
                        if (creditLimit >= 0) {
                            System.out.println("Credit limit must be a negative value. Please try again.");
                            continue;
                        }
                        break; // exit loop if valid
                    } catch (NumberFormatException e) {
                        System.out.print("Invalid input. Please enter a negative number: ");
                    }
                }
                newAccount = new Credit(accountNumber, creditLimit);
                break;
            default:
                System.out.println("Invalid account type. Please try again.");
                return;
        }
    
        customer.addAccount(newAccount);
        System.out.println("Successfully opened a new " + accountType + " account with account number: " + accountNumber);
        System.out.println();
    }
    

    // Generate a unique account number
    private String generateUniqueAccountNumber() {
        // Implement logic to generate a unique account number
        // Example: Generate a random 4-digit number
        int number = (int) (Math.random() * 9000) + 1000; // ensures a 4-digit number
        return String.valueOf(number);
    }
}

