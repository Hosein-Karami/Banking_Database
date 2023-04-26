package Checker;

import Exceptions.IntervalException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class NumberChecker {

    private static final Scanner scanner = new Scanner(System.in);

    public static int getProperNumber(int min,int max){
        int number;
        while (true){
            try {
                System.out.print("Enter your number : ");
                number = scanner.nextInt();
                if(number >= min && number <= max)
                    return number;
                throw new IntervalException("Your input should between " + min + " and " + max);
            }catch (InputMismatchException e){
                System.out.println("Your input should be a number!");
                scanner.nextLine();
            }catch (IntervalException e){
                System.out.println(e.getExceptionMessage());
                scanner.nextLine();
            }
        }
    }

}
