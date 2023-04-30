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
        getAccountNumber();
        getAccountBalance();
        updateLogSnapshot();
        checkAccountNumberExistence();
        depositToAccount();
        withdrawFromAccount();
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

    private void getAccountBalance() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("CREATE PROCEDURE GetBalance(IN account_number BIGINT,OUT balance numeric(25,5))\n" +
                "BEGIN\n" +
                "  SELECT amount FROM latest_balances WHERE accountNumber=account_number INTO balance;\n" +
                "END;");
        preparedStatement.execute();
    }

    private void getAccountNumber() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("CREATE PROCEDURE GetAccountNumber(IN client_username VARCHAR(40),OUT account_number BIGINT)\n" +
                "BEGIN\n" +
                "  SELECT accountNumber FROM account WHERE username=client_username INTO account_number;\n" +
                "END;");
        preparedStatement.execute();
    }

    private void updateLogSnapshot() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("CREATE PROCEDURE LogSnapshot()\n" +
                "BEGIN\n" +
                "  INSERT INTO snapshot_log SET snapshot_timestamp=NOW();\n" +
                "END;");
        preparedStatement.execute();
    }

    private void checkAccountNumberExistence() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("CREATE PROCEDURE CheckAccountNumber(IN account_number BIGINT,OUT check_result BOOL)\n" +
                "BEGIN\n" +
                "  SELECT COUNT(*) FROM account WHERE accountNumber=account_number INTO check_result;\n" +
                "END;");
        preparedStatement.execute();
    }

    private void depositToAccount() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("CREATE PROCEDURE Deposit(IN account_number BIGINT,IN deposit_amount numeric(25,5))\n" +
                "BEGIN\n" +
                "  DECLARE now_amount numeric(25,5);\n" +
                "  DECLARE final_amount numeric(25,5);\n" +
                "  SELECT amount FROM latest_balances WHERE accountNumber=account_number INTO now_amount;\n" +
                "  SET final_amount = now_amount + deposit_amount;\n" +
                "  UPDATE latest_balances SET amount=final_amount WHERE accountNumber=account_number;\n" +
                "END;");
        preparedStatement.execute();
    }

    private void withdrawFromAccount() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("CREATE PROCEDURE Withdraw(IN account_number BIGINT,IN withdraw_amount numeric(25,5))\n" +
                "BEGIN\n" +
                "  DECLARE now_amount numeric(25,5);\n" +
                "  DECLARE final_amount numeric(25,5);\n" +
                "  SELECT amount FROM latest_balances WHERE accountNumber=account_number INTO now_amount;\n" +
                "  SET final_amount = now_amount - withdraw_amount;\n" +
                "  IF final_amount >= 0 THEN\n" +
                "    UPDATE latest_balances SET amount=final_amount WHERE accountNumber=account_number;\n" +
                "  ELSE" +
                "    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Lack of balance,First increase your balance and try again';" +
                "  END IF;\n" +
                "END;");
        preparedStatement.execute();
    }

}
