package task2.commands;

import task2.CalcCommands;

import java.util.Map;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 09.05.13
 * Time: 12:12
 * To change this template use File | Settings | File Templates.
 */
public class CalcDefine implements CalcCommands {
    public void execCommand(Stack<Double> mainStack, String[] toStack, Map<String, Double> map) {
        try {
            map.put(toStack[1], Double.valueOf(toStack[2]));
        } catch (Exception e) {
            System.out.println("Некорректный ввод данных");
        }
    }

}
