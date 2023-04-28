package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GeneralDao {

    protected Connection connection;
    {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Project","root","root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
