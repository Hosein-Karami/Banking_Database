import Dao.InitializeDao;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            InitializeDao initializeDao = new InitializeDao();
            initializeDao.initialize();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
