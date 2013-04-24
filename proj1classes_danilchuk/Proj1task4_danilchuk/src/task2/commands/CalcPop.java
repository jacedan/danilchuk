package task2.commands;

import task2.CalcCommands;

import java.util.Map;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 28.04.13
 * Time: 12:56
 * To change this template use File | Settings | File Templates.
 */
public class CalcPop implements CalcCommands {
    public void execCommand(Stack<Double> mainStack, String[] toStack, Map<String, Double> map) {
        Double value;
        try {
            value = mainStack.pop();
            System.out.println("Значение:" + value);
        } catch (Exception e) {
            System.out.println("Стек пуст");
        }

    }
}
