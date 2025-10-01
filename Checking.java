public class Checking extends Account {

    // constructor to initialize a checking account with the specified account number and initial balance
    public Checking(String accountNumber, double balance) {
        super(accountNumber, balance);
    }

    // implementation of the abstract method to specify the account type
    @Override
    public String getAccountType() {
        return "Checking";
    }

}
