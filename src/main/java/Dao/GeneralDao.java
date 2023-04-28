package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GeneralDao {

    protected Connection connection;

    public GeneralDao() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/Project","root","root");
    }

}
