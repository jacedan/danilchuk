package task2.commands;

import task2.CalcCommands;

import java.util.Map;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 09.05.13
 * Time: 11:05
 * To change this template use File | Settings | File Templates.
 */
public class CalcPush    implements CalcCommands
{
    public void execCommand(Stack<Double> mainStack ,String[] toStack, Map<String,Double> map)
    {
        Double value;
        try
        {

                if(map.containsKey(toStack[1]))
                {
                    value=map.get(toStack[1]);
                    //toStack[1]=tmp.toString();
                }
                else
                {
                 value=Double.valueOf(toStack[1]);
                }
        }
        catch(NumberFormatException e)
        {
            System.out.println("Facepalm...");
            return;
        }
        mainStack.push(value);
    }
}
