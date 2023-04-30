package Service;

import Dao.QueryRunner;
import Dao.SnapshotDao;

import java.io.*;
import java.sql.SQLException;
import java.util.Scanner;

public class EventService {

    private static EventService fileService = null;
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
            fileWriter.write(query);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runEvents() throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        String query;
        do{
            query = scanner.nextLine();
            System.out.println(query);
            try {
                queryRunner.run(query);
            }catch (SQLException e){
                System.out.println("Error : " + e.getMessage() + ", query : " + query);
            }
        }while (scanner.hasNext());
        try {
            snapshotDao.logSnapshot();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();
    }

}
