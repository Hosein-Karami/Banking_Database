package Controller;

import Checker.NumberChecker;

public class MainMenu {

    AccountController accountController = AccountController.getInstance();

    public void start(){
        while (true) {
            System.out.println("1)Login\n2)Sign up\n3)Exit");
            int choice = NumberChecker.getProperNumber(1, 3);
            if (choice == 1)
                accountController.login();
            else if (choice == 2)
                accountController.register();
            else {
                System.out.println("Have nice time,Goodbye :)");
                break;
            }
        }
    }

}
