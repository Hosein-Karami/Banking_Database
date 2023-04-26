package Dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InitializeDao extends GeneralDao{

    public void initialize() throws SQLException {
        makeTables();
        makeProcedures();
    }

    private void makeTables() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "CREATE TABLE account(account_id varchar(16) primary key,username varchar(40) unique, accountNumber biginteger,password varbinary(64),salt varbinary(16),first_name varchar(40),last_name varchar(40),national_id biginteger, date_of_birth date,account_type enum('client','employee'),interest_rate numeric(4,2));");
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(
                "CREATE TABLE login_log(username varchar(40),login_time timestamp,FOREIGN KEY(username) REFERENCES account(username));");
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(
                "CREATE INDEX idx_account_id ON account (accountNumber);");
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(
                "CREATE TABLE transactions(transaction_type enum('deposit', 'withdraw', 'transfer', 'interest'),transaction_time timestamp,from_account biginteger,to_account biginteger,amount numeric(25,5),FOREIGN KEY(from_account) REFERENCES account(accountNumber),FOREIGN KEY(to_account) REFERENCES account(accountNumber));");
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(
                "CREATE TABLE latest_balances(accountNumber biginteger,amount numeric(25,5),FOREIGN KEY(accountNumber) REFERENCES account(accountNumber));");
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(
                "CREATE TABLE snapshot_log(snapshot_id integer auto_increment primary key,snapshot_timestamp timestamp);");
        preparedStatement.execute();
    }

    private void makeProcedures() throws SQLException {
        hashPasswordProcedure();
        register();
    }

    private void hashPasswordProcedure() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("CREATE PROCEDURE GenerateSaltedHashPassword (IN password VARCHAR(40), OUT salt VARBINARY(16), OUT hashed_password VARBINARY(64))\n" +
                "BEGIN\n" +
                "  -- Generate a random salt\n" +
                "  SET salt = UNHEX(SHA2(RAND(), 256));\n" +
                "  -- Hash the password with the salt\n" +
                "  SET hashed_password = UNHEX(SHA2(CONCAT(password, HEX(salt)), 256));\n" +
                "END;");
        preparedStatement.execute();
    }

    private void register() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("CREATE PROCEDURE Register (IN username VARCHAR(40),IN accountNumber integer,IN password VARCHAR(40),IN first_name VARCHAR(40),IN last_name VARCHAR(40),IN national_id integer,IN date_of_birth date,IN type enum('client','employee'),IN interest_rate numeric(4,2))\n" +
                "BEGIN\n" +
                "  DECLARE salt VARBINARY(16);\n" +
                "  DECLARE hashed_password VARBINARY(64);\n" +
                "  CALL GenerateSaltedHashPassword(password,salt,hashed_password);\n" +
                "  INSERT INTO account VALUES (NULL,username,accountNumber,hashed_password,salt,first_name,last_name,national_id,date_of_birth,account_type,interest_rate);\n" +
                "END;");
        preparedStatement.execute();
    }

}
