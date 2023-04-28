package Service;

import Dao.AccountDao;
import Dao.QueryRunner;
import Entity.Account;
import QueryBuilder.AccountQuery;

import java.sql.SQLException;

public class AccountService {

    private static AccountService accountService = null;
    private final QueryRunner queryRunner = QueryRunner.getInstance();
    private final AccountDao accountDao = AccountDao.getInstance();

    public static AccountService getInstance(){
        if(accountService == null)
            accountService = new AccountService();
        return accountService;
    }

    private AccountService(){}

    public void register(Account account) throws SQLException {
        queryRunner.run(AccountQuery.register(account));
    }

    public boolean login(String username,String password) throws SQLException {
        boolean authenticate = accountDao.checkPassword(username,password);
        if(authenticate)
            queryRunner.run(AccountQuery.loginLog(username));
        return authenticate;
    }

}
