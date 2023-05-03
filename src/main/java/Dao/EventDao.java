package Dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EventDao extends GeneralDao{

    private static EventDao eventDao = null;

    public static EventDao  getInstance(){
        if(eventDao == null)
            eventDao = new EventDao();
        return eventDao;
    }

    private EventDao(){}

    public void interestPayments() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("CALL InterestPayments();");
        preparedStatement.execute();
    }

}
