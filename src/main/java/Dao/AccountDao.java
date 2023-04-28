package Dao;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

public class AccountDao extends GeneralDao{

    private static AccountDao accountDao = null;

    public static AccountDao getInstance(){
        if(accountDao == null)
            accountDao = new AccountDao();
        return accountDao;
    }

    private AccountDao(){}

    public boolean checkPassword(String username, String password) throws SQLException {
        CallableStatement statement = connection.prepareCall("CALL CheckPassword(?,?,?);");
        statement.setString(1,username);
        statement.setString(2,password);
        statement.registerOutParameter(3, Types.BOOLEAN);
        statement.execute();
        return statement.getBoolean(3);
    }

}
