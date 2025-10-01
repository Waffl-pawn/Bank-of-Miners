import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RunBank {
    private static BinarySearchTree customerTree = new BinarySearchTree();
    private static Scanner scanner = new Scanner(System.in);
    private static String loggedInCustomerId;
    private static BankManager bankManager = new BankManager(customerTree); // Initialize BankManager with customerTree

    private static void openAccount() {
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine();

        // Check if the customer already exists
        Customer existingCustomer = customerTree.findCustomer(customerId);
        if (existingCustomer == null) {
            // Customer doesn't exist, prompt to create a new customer
            System.out.print("Enter First Name: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter Last Name: ");
            String lastName = scanner.nextLine();
            System.out.print("Enter Date of Birth (yyyy-MM-dd): ");
            String dobInput = scanner.nextLine();
            Date dob = null;

            // Validate date input
            try {
                dob = new SimpleDateFormat("yyyy-MM-dd").parse(dobInput);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
                return; // Exit if the date is invalid
            }

            System.out.print("Enter Address: ");
            String address = scanner.nextLine();
            System.out.print("Enter Phone Number: ");
            String phoneNumber = scanner.nextLine();

            // Create a new customer and add to the BST
            existingCustomer = new Customer(customerId, firstName, lastName, dob, address, phoneNumber);
            customerTree.insert(customerId, existingCustomer); // Insert new customer into BST
            System.out.println("Customer account created successfully.");
        }

        // Prompt for account type using a menu format
        System.out.println("Choose an account type:");
        System.out.println("1. Checking");
        System.out.println("2. Saving");
        System.out.println("3. Credit");
        System.out.print("Enter the option number: ");

        int accountTypeOption = -1;
        while (true) {
            try {
                accountTypeOption = Integer.parseInt(scanner.nextLine());
                if (accountTypeOption < 1 || accountTypeOption > 3) {
                    throw new NumberFormatException(); // force re-prompt if outside valid range
                }
                break; // exit loop if valid
            } catch (NumberFormatException e) {
                System.out.print("Invalid option. Please enter 1, 2, or 3: ");
            }
        }

        // Map option to account type
        String accountType;
        switch (accountTypeOption) {
            case 1:
                accountType = "checking";
                break;
            case 2:
                accountType = "saving";
                break;
            case 3:
                accountType = "credit";
                break;
            default:
                System.out.println("Invalid option selected.");
                return;
        }

        System.out.print("Enter Initial Deposit Amount: ");
        double initialDeposit;

        // Validate initial deposit input
        while (true) {
            try {
                initialDeposit = Double.parseDouble(scanner.nextLine());
                if (initialDeposit < 0) {
                    throw new NumberFormatException(); // force re-prompt if negative
                }
                break; // exit loop if valid
            } catch (NumberFormatException e) {
                System.out.print("Invalid amount. Please enter a non-negative number: ");
            }
        }

        System.out.println();

        // Call the BankManager's openAccount method to open a new account
        bankManager.openAccount(customerId, accountType, initialDeposit);
    }

    private static boolean login() {
        System.out.println("Welcome! ");
        System.out.print("Enter Customer ID or Account Number: ");
        String input = scanner.nextLine();
        System.out.println();

        // First, try to find a customer by the input (either ID or account number)
        Customer customer = customerTree.findCustomer(input);

        // If the customer is found
        if (customer != null) {
            // Successful login
            loggedInCustomerId = customer.getId(); // Save the logged-in customer ID
            System.out.println("Login successful. Hello, " + customer.getFirstName() + "!");
            System.out.println();
            return true;
        } else {
            // If customer is not found, check if it's an account number
            for (Customer cust : customerTree.getAllCustomers()) {
                if (cust.findAccount(input) != null) {
                    customer = cust;
                    break;
                }
            }

            // If the account was found but not the customer
            if (customer != null) {
                System.out
                        .println("Customer associated with this account number found. Please enter your ID to proceed");
                System.out.print("Enter Customer ID: ");
                String idInput = scanner.nextLine();
                System.out.println();
                // Check if the entered ID matches the found customer
                if (customer.getId().equals(idInput)) {
                    loggedInCustomerId = customer.getId(); // Save the logged-in customer ID
                    System.out.println("Login successful. Hello, " + customer.getFirstName() + "!");
                    System.out.println();

                    // Log the transfer
                    TransactionLogger.log(customer.getFirstName() + " " + customer.getFirstName() + " , ID = " + loggedInCustomerId + ", Has logged in.");
                    return true;
                } else {
                    System.out.println("Incorrect Customer ID. Login failed.");
                    return false;
                }
            } else {
                // If neither customer nor account found
                System.out.println("Customer not found. Would you like to open an account? (Y/N)");
                String response = getYesNoInput();
                if (response.equalsIgnoreCase("Y")) {
                    openAccount(); // Call the method to open a new account
                }
                return false;
            }
        }
    }

    private static String getYesNoInput() {
        String input;
        while (true) {
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N")) {
                return input;
            } else {
                System.out.print("Invalid input. Please enter 'Y' for yes or 'N' for no: ");
            }
        }
    }

    private static void loadCustomersFromCSV(String filePath) {
        try {
            customerTree = FileUtil.readCustomersFromCSV(filePath);
        } catch (Exception e) {
            System.err.println("Error loading customer data: " + e.getMessage());
            System.exit(1); // Exit the application if loading fails
        }
    }

    private static boolean isBankManager() {
        System.out.println("Are you a bank manager? (Y/N): ");
        String response = getYesNoInput();
        return true;
    }

    public static void main(String[] args) {
        loadCustomersFromCSV("BankUsers.csv"); // Specify the path to your CSV file
        bankManager = new BankManager(customerTree); // Initialize the bank manager with the customer tree

        // Upper-level menu to ask if the user is a bank manager
        System.out.println("Bank of Miners");
        System.out.println("Please log in to continue.");
        boolean bankManagerLogin = isBankManager();
        System.out.println();

        /*
         * if (bankManagerLogin) {
         * System.out.println("Welcome");
         * } else {
         * System.out.println("Welcome");
         * }
         */

        // User login
        while (!login()) {
            System.out.println("Please try logging in again.");
            System.out.println();
        }

        // Menu loop
        boolean exit = false;
        while (!exit) {
            System.out.println("Welcome to the Bank of Miners!");
            System.out.println("1. Inquire Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. Send Money");
            System.out.println("6. Open Account");
            System.out.println("7. Exit");
            System.out.print("Please select an option or type 'exit' to quit: ");

            String input = scanner.nextLine();

            // Exit condition
            if (input.equalsIgnoreCase("exit")) {
                exit = true;
                System.out.println("Exiting system.........  \nHave a great day!");
                scanner.close();
                continue;
            }

            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid option, please try again!");
                continue;
            }

            switch (choice) {
                // Add logic for each option here
                case 1:
                    // Find the customer based on the logged-in ID
                    Customer customer = customerTree.findCustomer(loggedInCustomerId);

                    if (customer != null) {
                        // Get all accounts for the logged-in customer
                        ArrayList<Account> accounts = customer.getAccounts();

                        // Check if the customer has any accounts
                        if (accounts.isEmpty()) {
                            System.out.println(
                                    customer.getFirstName() + " " + customer.getLastName() + " has no accounts.");
                        } else {
                            // Print the balance for each account
                            System.out.println("Account Balances for " + customer.getFirstName() + " "
                                    + customer.getLastName() + ":");
                            
                            // Log balance inquiry
                            TransactionLogger.log("Customer ID " + loggedInCustomerId + " inquired balance.");

                            for (Account account : accounts) {
                                System.out.println("Account Type: " + account.getAccountType() + ", Account Number: "
                                        + account.getAccountNumber() + ", Balance: $" + account.inquireBalance());
                            }
                        }
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;
                case 2:
                    customer = customerTree.findCustomer(loggedInCustomerId);

                    if (customer != null) {
                        // Prompt the user to choose the account number for deposit
                        System.out.print("Enter the Account Number to deposit money into: ");
                        String accountNumber = scanner.nextLine();
                        Account account = customer.findAccount(accountNumber);

                        if (account != null) {
                            System.out.print("Enter the amount to deposit: ");
                            double amount = Double.parseDouble(scanner.nextLine());
                            account.deposit(amount); // Deposit method from Account class

                            // Log the deposit
                            TransactionLogger.log("Customer ID " + loggedInCustomerId + " deposited $" + amount + " into account " + accountNumber + ".");
                        } else {
                            System.out.println("Account not found.");
                        }
                    }
                    break;
                case 3:
                    customer = customerTree.findCustomer(loggedInCustomerId);

                    if (customer != null) {
                        // Prompt the user to choose the account number for withdrawal
                        System.out.print("Enter the Account Number to withdraw money from: ");
                        String accountNumber = scanner.nextLine();
                        Account account = customer.findAccount(accountNumber);

                        if (account != null) {
                            System.out.print("Enter the amount to withdraw: ");
                            double amount = Double.parseDouble(scanner.nextLine());
                            account.withdraw(amount); // Withdraw method from Account class

                            // Log the withdrawal
                            TransactionLogger.log("Customer ID " + loggedInCustomerId + " withdrew $" + amount + " from account " + accountNumber + ".");
                        } else {
                            System.out.println("Account not found.");
                        }
                    }
                    break;
                case 4:
                    customer = customerTree.findCustomer(loggedInCustomerId);

                    if (customer != null) {
                        // Prompt the user to choose the account number to transfer money from
                        System.out.print("Enter the Account Number to transfer money from: ");
                        String fromAccountNumber = scanner.nextLine();
                        Account fromAccount = customer.findAccount(fromAccountNumber);

                        if (fromAccount != null) {
                            // Prompt the user to choose the account number to transfer money to
                            System.out.print("Enter the Account Number to transfer money to: ");
                            String toAccountNumber = scanner.nextLine();
                            Account toAccount = customer.findAccount(toAccountNumber);

                            if (toAccount != null) {
                                System.out.print("Enter the amount to transfer: ");
                                double amount = Double.parseDouble(scanner.nextLine());
                                fromAccount.transferFunds(toAccount, amount); // Transfer funds between accounts

                                // Log the transfer
                                TransactionLogger.log("Customer ID " + loggedInCustomerId + " transferred $" + amount + " from account " + fromAccountNumber + " to account " + toAccountNumber + ".");
                            } else {
                                System.out.println("Destination account not found.");
                            }
                        } else {
                            System.out.println("Source account not found.");
                        }
                    }
                    break;
                case 5:
                    customer = customerTree.findCustomer(loggedInCustomerId);

                    if (customer != null) {
                        // Prompt the user to choose the account number to send money from
                        System.out.print("Enter the Account Number to send money from: ");
                        String fromAccountNumber = scanner.nextLine();
                        Account fromAccount = customer.findAccount(fromAccountNumber);

                        if (fromAccount != null) {
                            // Prompt the user for the recipient customer ID
                            System.out.print("Enter the Customer ID to send money to: ");
                            String recipientCustomerId = scanner.nextLine();
                            Customer recipientCustomer = customerTree.findCustomer(recipientCustomerId);

                            if (recipientCustomer != null) {
                                // Prompt the user to choose the recipient's account number
                                System.out.print("Enter the Account Number to send money to: ");
                                String toAccountNumber = scanner.nextLine();
                                Account toAccount = recipientCustomer.findAccount(toAccountNumber);

                                if (toAccount != null) {
                                    System.out.print("Enter the amount to send: ");
                                    double amount = Double.parseDouble(scanner.nextLine());
                                    fromAccount.transferFunds(toAccount, amount); // Send money to another customer
                                } else {
                                    System.out.println("Recipient's account not found.");
                                }
                            } else {
                                System.out.println("Recipient customer not found.");
                            }
                        } else {
                            System.out.println("Source account not found.");
                        }
                    }
                    break;
                case 6:
                    openAccount(); // Open account logic
                    break;
                case 7:
                    exit = true;
                    System.out.println("Exiting system.........  \nHave a great day!");
                    scanner.close();
                    continue;
                default:
                    System.out.println("Invalid option. Please try again.");
            }

            // Prompt user to continue or exit only if the exit option was not chosen
            if (!exit) {
                System.out.print("Would you like to perform another action? (Y/N): ");
                String response = getYesNoInput(); // This handles input validation
                if (response.equalsIgnoreCase("N")) {
                    System.out.println("Exiting system.........  \nHave a great day!");
                    scanner.close();
                    exit = true; // Set exit to true to exit the system
                } 
            }
        }
    }
}
