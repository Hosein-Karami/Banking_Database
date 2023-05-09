package Service;

import Dao.EventDao;
import Dao.QueryRunner;
import Dao.SnapshotDao;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class EventService {

    private static EventService fileService = null;
    private final ArrayList<String> queries = new ArrayList<>();
    private final EventDao eventDao = EventDao.getInstance();
    private final File file = new File("Queries.txt");
    private final QueryRunner queryRunner = QueryRunner.getInstance();
    private final SnapshotDao snapshotDao = SnapshotDao.getInstance();

    public static EventService getInstance() {
        if(fileService == null)
            fileService = new EventService();
        return fileService;
    }

    private EventService() {}

    public void saveEvent(String query){
        try {
            FileWriter fileWriter = new FileWriter(file,true);
            fileWriter.write(query + ", Time : " + LocalDateTime.now() + "\n");
            fileWriter.flush();
            fileWriter.close();
            queries.add(query);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runEvents() throws SQLException {
        for(String query : queries){
            try{
                queryRunner.run(query);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        eventDao.interestPayments();
        snapshotDao.logSnapshot();
        queries.clear();
    }

}
