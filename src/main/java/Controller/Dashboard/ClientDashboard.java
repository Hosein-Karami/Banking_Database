package Controller.Dashboard;

import Checker.NumberChecker;
import Service.AccountService;

public class ClientDashboard extends GeneralDashboard {

    private AccountService accountService = AccountService.getInstance();

    public ClientDashboard(String username) {
        super(username);
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
            System.out.println("Balance : " + accountService.getBalance(username));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
