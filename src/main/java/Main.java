import Dao.GeneralDao;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            GeneralDao generalDao = new GeneralDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
