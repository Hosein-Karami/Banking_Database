package Controller.Dashboard;

import Checker.NumberChecker;
import Service.AccountService;

import java.sql.SQLException;

public class ClientDashboard extends GeneralDashboard {

    private AccountService accountService = AccountService.getInstance();

    private long accountNumber;

    public ClientDashboard(String username) {
        super(username);
        try {
            accountNumber = accountService.getAccountNumber(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        int choice;
        while (true){
            System.out.println("\n1)Check balance\n2)Deposit\n3)Withdraw\n4)Transfer\n5)Interest payments\n6)Logout\n");
            choice = NumberChecker.getProperNumber(1,6);
            if(choice == 1)
                checkBalance();
            else
                break;
        }
    }

    private void checkBalance(){
        try{
            System.out.println("Balance : " + accountService.getBalance(accountNumber));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
