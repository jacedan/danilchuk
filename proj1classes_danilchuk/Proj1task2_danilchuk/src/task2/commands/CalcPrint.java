package task2.commands;

import task2.CalcCommands;

import java.util.Map;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 09.05.13
 * Time: 11:33
 * To change this template use File | Settings | File Templates.
 */
public class CalcPrint   implements CalcCommands
{
    public void execCommand(Stack<Double> mainStack ,String[] toStack, Map<String,Double> map)
    {
        Double value;
        try
        {
            value=mainStack.peek();
            System.out.println("Значение:"+value);
        }
        catch (Exception e)
        {
            System.out.println("Стек пуст");
        }

    }
}