package Dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SnapshotDao extends GeneralDao{

    private static SnapshotDao snapshotDao = null;

    public static SnapshotDao getInstance(){
        if(snapshotDao == null)
            snapshotDao = new SnapshotDao();
        return snapshotDao;
    }

    private SnapshotDao(){}

    public void logSnapshot() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("CALL LogSnapshot();");
        preparedStatement.execute();
    }

    public void createSnapshotTable() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("CALL CreateSnapshotTable();");
        preparedStatement.execute();
    }

}
