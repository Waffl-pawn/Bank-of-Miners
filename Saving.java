public class Saving extends Account {

    // constructor to initialize a saving account with the specified account number and initial balance
    public Saving(String accountNumber, double balance) {
        super(accountNumber, balance);
    }

     // implementation of the abstract method to specify the account type
     @Override
     public String getAccountType() {
         return "Saving";
     }
}
