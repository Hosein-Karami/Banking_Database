import Controller.MainMenu;
import Dao.InitializeDao;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            InitializeDao initializeDao = new InitializeDao();
            initializeDao.initialize();
            MainMenu start = new MainMenu();
            start.start();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
