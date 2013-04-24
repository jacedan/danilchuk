package task2.commands;

import task2.CalcCommands;

import java.util.Map;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 22.05.13
 * Time: 23:24
 * To change this template use File | Settings | File Templates.
 */
public class CalcLog implements CalcCommands
{
    public void execCommand(Stack<Double> mainStack ,String[] toStack, Map<String,Double> map)
    {
        if (mainStack.size() == 0){
            System.out.println("empty stack");
        } else {
            mainStack.push(StrictMath.log(mainStack.pop()));
        }
    }



}
