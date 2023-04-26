package Service;

import Entity.Account;
import QueryBuilder.AccountQuery;

public class AccountService {

    private static AccountService accountService = null;
    private final FileService fileService = FileService.getInstance();

    public static AccountService getInstance(){
        if(accountService == null)
            accountService = new AccountService();
        return accountService;
    }

    private AccountService(){}

    public void register(Account account){
        fileService.saveQuery(AccountQuery.register(account));
    }

}
