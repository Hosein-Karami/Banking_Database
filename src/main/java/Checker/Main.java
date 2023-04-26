package Checker;

import Controller.AccountController;
import Dao.InitializeDao;
import Dao.QueryRunner;
import Entity.Account;
import Entity.AccountType;
import Service.AccountService;
import Service.FileService;

import java.math.BigInteger;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Formatter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            InitializeDao initializeDao = new InitializeDao();
            initializeDao.initialize();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Account account = new Account("hosein","karami",1111111111111111L,"hosein",
                    "Karami",5555555555L,"2003-11-27",AccountType.client,0);
            AccountService service = AccountService.getInstance();
            service.register(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        FileService fileService = FileService.getInstance();
//        fileService.runQueries();
    }

}
