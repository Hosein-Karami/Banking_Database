import Controller.Start;
import Dao.InitializeDao;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            InitializeDao initializeDao = new InitializeDao();
            initializeDao.initialize();
            Start start = new Start();
            start.run();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
