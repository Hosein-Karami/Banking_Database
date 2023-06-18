package Dao;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class EventDao extends GeneralDao {

    private static EventDao eventDao = null;

    public static EventDao getInstance() {
        if (eventDao == null)
            eventDao = new EventDao();
        return eventDao;
    }

    private EventDao() {
    }

    public void interestPayments() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("CALL InterestPayments();");
        preparedStatement.execute();
    }

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

    public void runEvents() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("CALL RunDepositEvents();");
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement("CALL RunWithdrawEvents();");
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement("CALL RunTransferEvents();");
        preparedStatement.execute();
    }

}
