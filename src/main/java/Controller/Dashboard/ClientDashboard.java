package Controller.Dashboard;

import Checker.AccountChecker;
import Checker.NumberChecker;
import Service.AccountService;
import Service.EventService;

import java.sql.SQLException;
import java.util.Scanner;

public class ClientDashboard extends GeneralDashboard {

    private final AccountService accountService = AccountService.getInstance();
    private final EventService eventService = EventService.getInstance();
    private final Scanner scanner = new Scanner(System.in);
    private final AccountChecker accountChecker = AccountChecker.getInstance();

    private long accountNumber;

    public ClientDashboard(String username) {
        super(username);
        try {
            accountNumber = accountService.getAccountNumber(username);
        } catch (SQLException e) {
            System.out.println("ERROR : " + e.getMessage());
        }
    }

    public void run(){
        int choice;
        while (true){
            System.out.println("\n1)Check balance\n2)Deposit\n3)Withdraw\n4)Transfer\n5)Logout\n");
            choice = NumberChecker.getProperNumber(1,5);
            if(choice == 1)
                checkBalance();
            else if(choice == 2)
                deposit();
            else if(choice == 3)
                withdraw();
            else if(choice == 4)
                transfer();
            else
                break;
        }
    }

    private void checkBalance(){
        try{
            System.out.println("Balance : " + accountService.getBalance(accountNumber));
        }catch (Exception e){
            System.out.println("ERROR : " + e.getMessage());
        }
    }

    private void deposit(){
        System.out.print("Enter amount : ");
        double amount = scanner.nextDouble();
        try {
            if (amount < 0)
                System.out.println("Invalid amount");
            else {
                eventService.saveDepositEvent(accountNumber, amount);
                System.out.println("Done\n");
            }
        }catch (Exception e){
            System.out.println("ERROR : " + e.getMessage());
        }
    }

    private void withdraw(){
        System.out.print("Enter amount : ");
        double amount = scanner.nextDouble();
        try {
            if (amount < 0)
                System.out.println("Invalid amount");
            else {
                eventService.saveWithdrawEvent(accountNumber, amount);
                System.out.println("Done\n");
            }
        }catch (Exception e){
            System.out.println("ERROR : " + e.getMessage());
        }
    }

    private void transfer(){
        System.out.print("Enter target account number : ");
        long targetAccountNumber = accountChecker.getProperLongInformation(16);
        try {
            System.out.print("Enter amount : ");
            double amount = scanner.nextDouble();
            if (amount < 0)
                System.out.println("Invalid amount");
            else {
                eventService.saveTransferEvent(accountNumber,targetAccountNumber,amount);
                System.out.println("Done\n");
            }
        }catch (Exception e){
            System.out.println("ERROR : " + e.getMessage());
        }
    }

}
