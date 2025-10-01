import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileUtil {

    // Method to read from a CSV file and return a BinarySearchTree of Customer objects
    public static BinarySearchTree readCustomersFromCSV(String filePath) {
        BinarySearchTree bst = new BinarySearchTree();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Skip header
            br.readLine();
            while ((line = br.readLine()) != null) {
                // Manually parse the line considering quoted strings
                String[] values = parseCSVLine(line);

                // Validate the number of columns
                if (values.length < 13) {
                    System.err.println("Invalid line format: " + line);
                    continue; // Skip to the next line
                }

                try {
                    // Assuming CSV format: ID, First Name, Last Name, Date of Birth, Address, Phone, Checking Account Number, Checking Balance, Savings Account Number, Savings Balance, Credit Account Number, Credit Max, Credit Balance
                    String id = values[0];
                    String firstName = values[1];
                    String lastName = values[2];
                    Date dob = new SimpleDateFormat("dd-MMM-yy").parse(values[3]); // Updated format
                    String address = values[4];
                    String phoneNumber = values[5].trim(); // Trim whitespace
                    String checkingAccountNumber = values[6];
                    double checkingBalance = Double.parseDouble(values[7]);
                    String savingsAccountNumber = values[8];
                    double savingsBalance = values[9].isEmpty() ? 0.0 : Double.parseDouble(values[9]);
                    String creditAccountNumber = values[10];
                    double creditMax = values[11].isEmpty() ? 0.0 : Double.parseDouble(values[11]);
                    double creditBalance = values[12].isEmpty() ? 0.0 : Double.parseDouble(values[12]);

                    // Create accounts
                    Checking checkingAccount = new Checking(checkingAccountNumber, checkingBalance);
                    Saving savingAccount = new Saving(savingsAccountNumber, savingsBalance);
                    Credit creditAccount = new Credit(creditAccountNumber, creditMax);

                    // Create customer and add accounts
                    Customer customer = new Customer(id, firstName, lastName, dob, address, phoneNumber);
                    customer.addAccount(checkingAccount);
                    customer.addAccount(savingAccount);
                    customer.addAccount(creditAccount);

                    // Add customer to the BST
                    bst.insert(customer.getId(), customer); // Use customer ID as the key
                } catch (IllegalArgumentException | ParseException e) {
                    System.err.println("Error processing line: " + line + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("File read error: " + e.getMessage());
        }
        return bst;
    }

    // Simple method to parse CSV lines manually
    private static String[] parseCSVLine(String line) {
        ArrayList<String> values = new ArrayList<>();
        StringBuilder currentValue = new StringBuilder();
        boolean inQuotes = false;

        for (char c : line.toCharArray()) {
            if (c == '\"') {
                inQuotes = !inQuotes; // Toggle the inQuotes flag
            } else if (c == ',' && !inQuotes) {
                values.add(currentValue.toString().trim());
                currentValue.setLength(0); // Reset for the next value
            } else {
                currentValue.append(c); // Add character to the current value
            }
        }
        // Add the last value
        values.add(currentValue.toString().trim());

        return values.toArray(new String[0]);
    }

}





