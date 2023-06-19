package Dao;

import java.sql.*;
import java.util.Objects;

public class EventDao extends GeneralDao {

    private static EventDao eventDao = null;

    public static EventDao getInstance() {
        if (eventDao == null)
            eventDao = new EventDao();
        return eventDao;
    }

    private EventDao() {}

    public void saveDepositEvent(long accountNumber,double amount) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("CALL DepositEvent(?,?)");
        preparedStatement.setLong(1,accountNumber);
        preparedStatement.setDouble(2,amount);
        preparedStatement.execute();
    }

    public void saveWithdrawEvent(long accountNumber,double amount) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("CALL WithdrawEvent(?,?)");
        preparedStatement.setLong(1,accountNumber);
        preparedStatement.setDouble(2,amount);
        preparedStatement.execute();
    }

    public void saveTransferEvent(long from,long to,double amount) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("CALL TransferEvent(?,?,?)");
        preparedStatement.setLong(1,from);
        preparedStatement.setLong(2,to);
        preparedStatement.setDouble(3,amount);
        preparedStatement.execute();
    }

    public void saveInterestEvent(long accountNumber) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("CALL SaveInterestEvent(?);");
        preparedStatement.setLong(1,accountNumber);
        preparedStatement.execute();
    }

    public void runEvents() throws SQLException {
        String transactionType;
        long fromAccount;
        long toAccount;
        double amount;
        PreparedStatement preparedStatement = null;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM transactions WHERE ((transaction_time >= " +
                                                               "(SELECT snapshot_timestamp FROM snapshot_log " +
                "                                                    WHERE snapshot_id=(SELECT MAX(snapshot_id) FROM snapshot_log))) or (SELECT COUNT(*) FROM snapshot_log) = 0);");
        while (resultSet.next()) {
            try {
                transactionType = resultSet.getString(1);
                amount = resultSet.getDouble(5);
                switch (transactionType) {
                    case "deposit":
                        toAccount = resultSet.getLong(4);
                        preparedStatement = connection.prepareStatement("CALL Deposit(?,?)");
                        preparedStatement.setLong(1, toAccount);
                        preparedStatement.setDouble(2, amount);
                        break;
                    case "withdraw":
                        fromAccount = resultSet.getLong(3);
                        preparedStatement = connection.prepareStatement("CALL Withdraw(?,?)");
                        preparedStatement.setLong(1, fromAccount);
                        preparedStatement.setDouble(2, amount);
                        break;
                    case "transfer":
                        toAccount = resultSet.getLong(4);
                        fromAccount = resultSet.getLong(3);
                        preparedStatement = connection.prepareStatement("CALL Transfer(?,?,?)");
                        preparedStatement.setLong(1, fromAccount);
                        preparedStatement.setLong(2, toAccount);
                        preparedStatement.setDouble(3, amount);
                        break;
                    case "interest":
                        toAccount = resultSet.getLong(4);
                        preparedStatement = connection.prepareStatement("CALL Interest(?,?)");
                        preparedStatement.setLong(1, toAccount);
                        preparedStatement.setDouble(2, amount);
                        break;
                }
                Objects.requireNonNull(preparedStatement).execute();
            }catch (Exception e){
                System.out.println("ERROR : " + e.getMessage());
            }
        }
    }

}
