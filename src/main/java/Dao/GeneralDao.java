package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GeneralDao {

    protected Connection connection;

    public GeneralDao() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Project","root","root");
       // makeTables();
        makeProcedures();
    }

    private void makeTables() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "CREATE TABLE account(account_id varchar(16) primary key,username varchar(40) unique, accountNumber integer,password varbinary(64),Salt varbinary(16),first_name varchar(40),last_name varchar(40),national_id integer, date_of_birth date,type enum('client','employee'),interest_rate numeric(4,2));");
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(
                "CREATE TABLE login_log(username varchar(40),login_time timestamp,FOREIGN KEY(username) REFERENCES account(username));");
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(
                "CREATE INDEX idx_account_id ON account (accountNumber);");
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(
                "CREATE TABLE transactions(transaction_type enum('deposit', 'withdraw', 'transfer', 'interest'),transaction_time timestamp,from_account integer,to_account integer,amount numeric(25,5),FOREIGN KEY(from_account) REFERENCES account(accountNumber),FOREIGN KEY(to_account) REFERENCES account(accountNumber));");
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(
                "CREATE TABLE latest_balances(accountNumber integer,amount numeric(25,5),FOREIGN KEY(accountNumber) REFERENCES account(accountNumber));");
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(
                "CREATE TABLE snapshot_log(snapshot_id integer auto_increment primary key,snapshot_timestamp timestamp);");
        preparedStatement.execute();
    }

    private void makeProcedures() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("CREATE PROCEDURE GenerateSaltedHashPassword (IN password VARCHAR(50), OUT salt VARBINARY(16), OUT hashed_password VARBINARY(64))\n" +
                "BEGIN\n" +
                "  -- Generate a random salt\n" +
                "  SET salt = UNHEX(SHA2(RAND(), 256));\n" +
                "  -- Hash the password with the salt\n" +
                "  SET hashed_password = UNHEX(SHA2(CONCAT(password, HEX(salt)), 256));\n" +
                "END;");
        preparedStatement.execute();

    }

}
