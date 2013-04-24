package task2;

import task2.commands.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 28.04.13
 * Time: 13:16
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    static Stack<Double> mainStack = new Stack<Double>();
    static Map<String, Double> varMap = new HashMap<String, Double>();
    //static Map<String, CalcCommands> commandMap;

    public static void main(String[] args) {

        String s;
        Scanner scn;

        CalcCommandsFactory commFactory=CalcCommandsFactory.getInst();
        if (args.length > 0) {
            try {
                scn = new Scanner(new FileInputStream(args[0]));
            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден");
                return;
            }

        } else {
            scn = new Scanner(System.in);
        }


        while (scn.hasNextLine()) {

            s = scn.nextLine();
            String[] str = s.split(" ");
            try                    //Проверяем есть ли ключ в Map
            {
                commFactory.getCalcCommand(str[0]).execCommand(mainStack, str, varMap);    //Выполнится если ключ есть в Map
            }
            catch (NullPointerException e)
            {
                System.out.println("Неизвестная команда!");
            }

        }


    }




}
