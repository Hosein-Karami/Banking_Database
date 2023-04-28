package Controller.Dashboard;

import Checker.NumberChecker;

public class ClientDashboard extends GeneralDashboard {

    public ClientDashboard(String username) {
        super(username);
    }

    public void run(){
        int choice;
        while (true){
            System.out.println("1)Check balance\n2)Deposit\n3)Withdraw\n4)Transfer\n5)Interest payments\n6)Logout\n");
            choice = NumberChecker.getProperNumber(1,6);
        }
    }

}
