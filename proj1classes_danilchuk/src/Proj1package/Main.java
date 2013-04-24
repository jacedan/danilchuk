package Proj1package;


//Яков Данильчук

//import java.io.*;
import java.util.Scanner;


/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 14.04.13
 * Time: 21:11
 * To change this template use File | Settings | File Templates.
 */
public class Main
{
    public static void main(String[] args) {
    //System.out.println("jacedan");

    int inputValue;
    Scanner scn = new Scanner(System.in);
    RndWithUser checker=new RndWithUser();

    for(int att=0;att<8;att++)
        {

            try
            {
                System.out.println("input number here :");
                inputValue=scn.nextInt();
            }
            catch (Exception e)
            {
                att-- ;
                System.out.println("ERROR!!!");
                inputValue=-100;
                scn.next();
            }
            //checker.getUserValue(inputValue);
            if(checker.checkValues(inputValue))
            {
                System.out.println("WIN :)");
                break;
            }


            if(att>=7) System.out.println("LOSE (:");
        }

    System.out.println("random number :"+ checker.getRndValue());
    }
}
