package Dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InitializeDao extends GeneralDao{

    public InitializeDao() throws SQLException {}

    public void initialize() throws SQLException {
        makeTables();
        makeProcedures();
    }

    private void makeTables() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "CREATE TABLE account(account_id varchar(16) primary key,username varchar(40) unique NOT NULL, accountNumber BIGINT,password BLOB NOT NULL,salt BLOB NOT NULL,first_name varchar(40) NOT NULL ,last_name varchar(40) NOT NULL ,national_id BIGINT NOT NULL, date_of_birth date,account_type enum('client','employee'),interest_rate numeric(4,2));");
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(
                "CREATE TABLE login_log(username varchar(40),login_time timestamp,FOREIGN KEY(username) REFERENCES account(username));");
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(
                "CREATE INDEX idx_account_id ON account (accountNumber);");
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(
                "CREATE TABLE transactions(transaction_type enum('deposit', 'withdraw', 'transfer', 'interest'),transaction_time timestamp,from_account BIGINT NOT NULL,to_account BIGINT NOT NULL,amount numeric(25,5) NOT NULL,FOREIGN KEY(from_account) REFERENCES account(accountNumber),FOREIGN KEY(to_account) REFERENCES account(accountNumber));");
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(
                "CREATE TABLE latest_balances(accountNumber BIGINT NOT NULL,amount numeric(25,5),FOREIGN KEY(accountNumber) REFERENCES account(accountNumber),CHECK (amount >= 0));");
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement(
                "CREATE TABLE snapshot_log(snapshot_id integer auto_increment primary key,snapshot_timestamp timestamp);");
        preparedStatement.execute();
    }

    private void makeProcedures() throws SQLException {
        hashPassword();
        register();
        checkPassword();
        loginLog();
    }

    private void hashPassword() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("CREATE PROCEDURE GenerateSaltedHashPassword (IN password VARCHAR(40), OUT salt BLOB, OUT hashed_password BLOB)\n" +
                "BEGIN\n" +
                "  -- Generate a random salt\n" +
                "  SET salt = UNHEX(SHA2(RAND(), 256));\n" +
                "  -- Hash the password with the salt\n" +
                "  SET hashed_password = UNHEX(SHA2(CONCAT(password, HEX(salt)), 256));\n" +
                "END;");
        preparedStatement.execute();
    }

    private void register() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("CREATE PROCEDURE Register (IN username VARCHAR(40),IN accountNumber BIGINT,IN password VARCHAR(40),IN first_name VARCHAR(40),IN last_name VARCHAR(40),IN national_id BIGINT,IN date_of_birth date,IN account_type enum('client','employee'),IN interest_rate numeric(4,2))\n" +
                "BEGIN\n" +
                "  DECLARE salt BLOB;\n" +
                "  DECLARE hashed_password BLOB;\n" +
                "  CALL GenerateSaltedHashPassword(password,salt,hashed_password);\n" +
                "  INSERT INTO account VALUES (NULL,username,accountNumber,hashed_password,salt,first_name,last_name,national_id,date_of_birth,account_type,interest_rate);\n" +
                "  INSERT INTO latest_balances VALUES (accountNumber,0);\n" +
                "END;");
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement("CREATE TRIGGER generate_user_id\n" +
                "BEFORE INSERT ON account\n" +
                " FOR EACH ROW\n" +
                " BEGIN\n" +
                "    SET NEW.account_id = CONCAT(\n" +
                "        LEFT(NEW.first_name, 8),\n" +
                "        LEFT(NEW.last_name, 8),\n" +
                "        SUBSTRING(MD5(CONCAT(NEW.first_name, NEW.last_name, NOW())), 1, 4)\n" +
                "    );\n" +
                " END;");
        preparedStatement.execute();
    }

    private void checkPassword() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("CREATE PROCEDURE CheckPassword(IN input_username VARCHAR(40), IN input_password VARCHAR(40), OUT is_correct BOOL)\n" +
                "BEGIN\n" +
                "  DECLARE hashed_password BLOB;\n" +
                "  DECLARE salt_value BLOB;\n" +
                "  DECLARE input_hash BLOB;\n" +
                "  -- Get the stored salt and hashed password for the user\n" +
                "  SELECT salt, password FROM account WHERE username = input_username INTO salt_value, hashed_password;\n" +
                "  -- Hash the input password with the stored salt\n" +
                "  SET input_hash = UNHEX(SHA2(CONCAT(input_password, HEX(salt_value)), 256));\n" +
                "  -- Compare the input hash with the stored hashed password\n" +
                "  IF input_hash = hashed_password THEN\n" +
                "    SET is_correct = true;\n" +
                "  ELSE\n" +
                "    SET is_correct = false;\n" +
                "  END IF;\n" +
                "END;");
        preparedStatement.execute();
    }

    private void loginLog() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("CREATE PROCEDURE LoginLog(IN username VARCHAR(40),IN login_time TIMESTAMP)\n" +
                "BEGIN\n" +
                "  INSERT INTO login_log VALUES(username,login_time);\n" +
                "END;");
        preparedStatement.execute();
    }

}
