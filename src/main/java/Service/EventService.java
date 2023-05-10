package Service;

import Dao.EventDao;
import Dao.QueryRunner;
import Dao.SnapshotDao;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class EventService {

    private static EventService fileService = null;
    private final EventDao eventDao = EventDao.getInstance();
    private final File backupFile = new File("Backup.txt");
    private final File queryFile = new File("Queries.txt");
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
            FileWriter fileWriter = new FileWriter(backupFile,true);
            FileWriter fileWriter_2 = new FileWriter(queryFile,true);
            fileWriter.write(query + " Time : " + LocalDateTime.now() + "\n");
            fileWriter.flush();
            fileWriter.close();
            fileWriter_2.write(query + "\n");
            fileWriter_2.flush();
            fileWriter_2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runEvents() throws IOException, SQLException {
        String query = null;
        Scanner scanner = new Scanner(queryFile);
        while (scanner.hasNext()){
            try {
                query = scanner.nextLine();
                queryRunner.run(query);
            }catch (SQLException sqlException){
                System.out.println("Error : " + sqlException.getMessage() + ", query : " + query);
            }
        }
        eventDao.interestPayments();
        snapshotDao.logSnapshot();
        FileWriter fileWriter = new FileWriter(queryFile);
        fileWriter.write("");
        fileWriter.flush();
        fileWriter.close();
    }

}
