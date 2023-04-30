package Controller.Dashboard;

import Checker.NumberChecker;
import QueryBuilder.AccountQuery;
import Service.AccountService;
import Service.EventService;

import java.sql.SQLException;
import java.util.Scanner;

public class ClientDashboard extends GeneralDashboard {

    private AccountService accountService = AccountService.getInstance();
    private EventService eventService = EventService.getInstance();
    private final Scanner scanner = new Scanner(System.in);

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
            else if(choice == 2)
                deposit();
            else if(choice == 3)
                withdraw();
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

    private void deposit(){
        System.out.print("Enter amount : ");
        double amount = scanner.nextDouble();
        if(amount < 0)
            System.out.println("Invalid amount");
        else{
            eventService.saveEvent(AccountQuery.deposit(accountNumber,amount));
            System.out.println("Done\n");
        }
    }

    private void withdraw(){
        System.out.print("Enter amount : ");
        double amount = scanner.nextDouble();
        if(amount < 0)
            System.out.println("Invalid amount");
        else{
            eventService.saveEvent(AccountQuery.withdraw(accountNumber,amount));
            System.out.println("Done\n");
        }
    }

}
