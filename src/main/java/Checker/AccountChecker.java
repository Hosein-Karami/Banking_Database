package Checker;

import Entity.Account;
import Entity.AccountType;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AccountChecker {

    private static final Scanner scanner = new Scanner(System.in);
    private static AccountChecker accountChecker = null;

    public static AccountChecker getInstance(){
        if(accountChecker == null)
            accountChecker = new AccountChecker();
        return accountChecker;
    }

    private AccountChecker(){}


    public Account getProperAccount() {
        System.out.print("Enter username(length of username should between 1 and 40) : ");
        String username = getProperStringInformation(40);
        System.out.print("Enter password(length of password should between 1 and 40) : ");
        String password = getProperStringInformation(40);
        System.out.print("Enter firstname(length of firstname should between 1 and 40) : ");
        String firstname = getProperStringInformation(40);
        System.out.print("Enter lastname(length of lastname should between 1 and 40) : ");
        String lastname = getProperStringInformation(40);
        System.out.print("Enter account number(16 digit number) : ");
        long accountNumber = getProperLongInformation(16);
        System.out.print("Enter your national_id : ");
        long national_id = getProperLongInformation(10);
        scanner.nextLine();
        System.out.print("Enter your birthdate(yyyy-mm-dd) : ");
        String birthdate = getProperDate();
        AccountType accountType = getProperType();
        //TODO design interest_rate
        return new Account(username,password,accountNumber,firstname,lastname,national_id,birthdate,accountType,0);
    }

    private String getProperStringInformation(int maxLength){
        String info;
        while (true) {
            info = scanner.nextLine();
            if (((info.length() >= 1) && (info.length() <= maxLength)))
                return info;
            else
                System.out.print("length of this data should between 1 and " + maxLength + " : ");
        }
    }

    private long getProperLongInformation(int length) {
        long info;
        while(true) {
            try {
                info = scanner.nextLong();
                if ((Long.toString(info)).length() == length)
                    return info;
                else
                    System.out.print("The length of account number should be " + length + " : ");
            }catch (InputMismatchException e){
                System.out.print("Invalid account number,try again : ");
                scanner.nextLine();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private String getProperDate() {
        return scanner.nextLine();
    }

    private AccountType getProperType(){
        System.out.println("Account type :\n1)Client\n2)Employee");
        int choice = NumberChecker.getProperNumber(1,2);
        if(choice == 1)
            return AccountType.client;
        else
            return AccountType.employee;
    }

}
