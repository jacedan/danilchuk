package task2.commands;

import task2.CalcCommands;

import java.util.Map;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 09.05.13
 * Time: 11:57
 * To change this template use File | Settings | File Templates.
 */
public class CalcMult implements CalcCommands {
    public void execCommand(Stack<Double> mainStack, String[] toStack, Map<String, Double> map) {
        Double value;
        try {
            value = mainStack.pop() * mainStack.pop();

        } catch (Exception e) {
            System.out.println("Недостаточно данных в стэке для операции");
            return;
        }
        mainStack.push(value);
    }

}
