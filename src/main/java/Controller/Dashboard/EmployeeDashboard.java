package Controller.Dashboard;

import Checker.NumberChecker;
import Service.EventService;

import java.io.FileNotFoundException;

public class EmployeeDashboard extends GeneralDashboard {

    private EventService eventService = EventService.getInstance();

    public EmployeeDashboard(String username) {
        super(username);
    }

    @Override
    public void run(){
        int choice;
        while (true){
            System.out.println("\n1)Update databases\n2)Logout\n");
            choice = NumberChecker.getProperNumber(1,2);
            if(choice == 1)
                updateDatabase();
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

}
