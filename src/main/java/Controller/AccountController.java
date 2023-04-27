package Controller;

import Checker.AccountChecker;
import Service.AccountService;

import java.sql.SQLException;
import java.util.Scanner;

public class AccountController {

    private final AccountChecker accountChecker = AccountChecker.getInstance();
    private final AccountService accountService = AccountService.getInstance();
    private static AccountController accountController = null;

    private final Scanner scanner = new Scanner(System.in);

    public static AccountController getInstance(){
        if(accountController == null)
            accountController = new AccountController();
        return accountController;
    }

    private AccountController(){}

    public void register(){
        try {
            accountService.register(accountChecker.getProperAccount());
            System.out.println("You signed up successfully\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void login(){
        System.out.print("Username : ");
        String username = scanner.nextLine();
        System.out.print("Password : ");
        String password = scanner.nextLine();
        try {
            boolean authenticate = accountService.login(username,password);
            if(authenticate)
                //TODO design dashboard
                System.out.println("salam");
            else
                System.out.println("Username or password is false\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
