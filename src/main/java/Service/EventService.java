package Service;

import Dao.QueryRunner;

import java.io.*;
import java.sql.SQLException;
import java.util.Scanner;

public class EventService {

    private static EventService fileService = null;
    private final File file = new File("Queries.txt");
    private final QueryRunner queryRunner = QueryRunner.getInstance();

    public static EventService getInstance(){
        if(fileService == null)
            fileService = new EventService();
        return fileService;
    }

    private EventService(){}


    public void saveEvent(String query){
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(query);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runEvents(){
        try {
            Scanner scanner = new Scanner(file);
            String query;
            while (scanner.hasNext()) {
                query = scanner.nextLine();
                queryRunner.run(query);
            }
            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}
