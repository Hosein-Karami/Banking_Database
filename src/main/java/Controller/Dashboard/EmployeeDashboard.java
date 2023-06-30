package Controller.Dashboard;

import Checker.NumberChecker;
import Service.EventService;

import java.util.Scanner;

public class EmployeeDashboard extends GeneralDashboard {

    private final EventService eventService = EventService.getInstance();
    private final Scanner scanner = new Scanner(System.in);

    public EmployeeDashboard(String username) {
        super(username);
    }

    @Override
    public void run(){
        int choice;
        while (true){
            System.out.println("\n1)Update databases\n2)Interest payment\n3)Logout\n");
            choice = NumberChecker.getProperNumber(1,3);
            if(choice == 1)
                updateDatabase();
            else if(choice == 2)
                interestPayment();
            else
                break;
        }
    }

    private void updateDatabase(){
        try {
            eventService.runEvents();
        } catch (Exception e) {
            System.out.println("ERROR : " + e.getMessage());
        }
    }

    private void interestPayment(){
        try{
            System.out.print("Target account number : ");
            long accountNumber = scanner.nextLong();
            eventService.saveInterestEvent(accountNumber);
            System.out.println("Done\n");
        }catch (Exception e){
            System.out.println("ERROR : " + e.getMessage());
        }
    }

}
