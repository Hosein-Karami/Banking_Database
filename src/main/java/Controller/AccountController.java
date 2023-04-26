package Controller;

import Checker.AccountChecker;
import Service.AccountService;

public class AccountController {

    private final AccountChecker accountChecker = AccountChecker.getInstance();
    private final AccountService accountService = AccountService.getInstance();
    private static AccountController accountController = null;

    public static AccountController getInstance(){
        if(accountController == null)
            accountController = new AccountController();
        return accountController;
    }

    private AccountController(){}

    public void register(){
        accountService.register(accountChecker.getProperAccount());
    }

}
