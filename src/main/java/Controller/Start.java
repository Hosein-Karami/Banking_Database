package Controller;

import Checker.NumberChecker;

public class Start {

    public void run(){
        System.out.println("1)Login\n2)Sign up\n3)Exit");
        int choice = NumberChecker.getProperNumber(1,3);
    }

}
