package Dao;

import Entity.AccountType;

import java.sql.*;

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

    public AccountType getAccountType(String username) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT account_type FROM account WHERE username='"+username+"'");
        resultSet.next();
        String accountType = resultSet.getString(1);
        return AccountType.getAccountType(accountType);
    }

    public double getBalance(String username) throws SQLException {
        CallableStatement statement = connection.prepareCall("CALL GetBalance(?,?);");
        statement.setString(1,username);
        statement.registerOutParameter(2,Types.NUMERIC);
        statement.execute();
        return statement.getDouble(2);
    }

}
