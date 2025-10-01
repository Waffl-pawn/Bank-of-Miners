import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionLogger {

    private static final String LOG_FILE = "transactions.log";

    // Method to append logs to a file
    public static void log(String message) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) { // 'true' for append mode
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timestamp = formatter.format(new Date());
            writer.write("[" + timestamp + "] " + message + "\n");
        } catch (IOException e) {
            System.out.println("Error writing to log file: " + e.getMessage());
        }
    }
}
