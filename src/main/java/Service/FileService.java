package Service;

import Dao.QueryRunner;

import java.io.*;
import java.sql.SQLException;
import java.util.Scanner;

public class FileService {

    private static FileService fileService = null;
    private final File file = new File("Queries.txt");;
    private final QueryRunner queryRunner = QueryRunner.getInstance();

    public static FileService getInstance(){
        if(fileService == null)
            fileService = new FileService();
        return fileService;
    }

    private FileService(){}


    public void saveQuery(String query){
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(query);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runQueries(){
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
