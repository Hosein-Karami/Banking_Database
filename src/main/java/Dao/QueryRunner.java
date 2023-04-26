package Dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QueryRunner extends GeneralDao{

    private static QueryRunner queryRunner = null;

    public static QueryRunner getInstance(){
        if(queryRunner == null)
            queryRunner = new QueryRunner();
        return queryRunner;
    }

    private QueryRunner(){}

    public void run(String query) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.execute();
    }

}
