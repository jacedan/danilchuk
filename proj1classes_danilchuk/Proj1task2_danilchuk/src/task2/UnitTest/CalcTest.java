package task2.UnitTest;


import task2.commands.*;
import task2.CalcCommands;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 18.05.13
 * Time: 17:06
 * To change this template use File | Settings | File Templates.
 */
public class CalcTest
{
    private Stack<Double> stack = new Stack<Double>();
    private Map<String, Double> map = new HashMap<String, Double>();
    Map <String, CalcCommands> commandMap = new HashMap<String, CalcCommands>();

    @Test
    public void testDiv() {
        CalcCommands t = new CalcDiv();
        stack.push(1000.);
        stack.push(100.);
        t.execCommand(stack,  new String[0],map);
        assertEquals(1.0e-1, stack.peek(), 1.0e-9);
        assertEquals(1, stack.size());
    }

    @Test
    public void testSumm() {
        CalcCommands t = new CalcSumm();
        stack.push(1.);
        stack.push(2.);
        t.execCommand(stack, new String[0], map);
        assertEquals(3., stack.peek(), 3.);
        assertEquals(1, stack.size());
    }

    @Test
    public void testDiff() {
        CalcCommands t = new CalcDiff();
        stack.push(10.);
        stack.push(5.);
        t.execCommand(stack, new String[0], map);
        assertEquals((Double)(-5.0), stack.peek());
        assertEquals(1, stack.size());
    }

    @Test
    public void testMult() {
        CalcCommands t = new CalcMult();
        stack.push(3.);
        stack.push(4.);
        t.execCommand(stack, new String[0], map);
        assertEquals((Double)12.0, stack.peek());
        assertEquals(1, stack.size());
    }

    @Test
    public void testSqrt() {
        CalcCommands t = new CalcSqrt();
        stack.push(25.);
        t.execCommand(stack, new String[0], map);
        assertEquals((Double)5.0, stack.peek());
        assertEquals(1, stack.size());
    }

    @Test
    public void testPush() {
        CalcCommands t = new CalcPush();
        t.execCommand(stack,new String[]{"push", "5"}, map);
        assertEquals((Double)5.0, stack.peek());
        assertEquals(1, stack.size());
    }

    @Test
    public void testPop() {
        CalcCommands t = new CalcPop();
        stack.push(5.);
        stack.push(6.);
        t.execCommand(stack,new String[]{"pop"}, map);
        assertEquals((Double)5.0, stack.peek());
        assertEquals(1, stack.size());
    }

    @Test
    public void testPrint() {
        CalcCommands t = new CalcPrint();
        stack.push(10.);
        t.execCommand(stack,  new String[]{"print"},map);
        assertEquals((Double)10.0, stack.peek());
        assertEquals(1, stack.size());
    }

    @Test
    public void testDefine() {
        CalcCommands t = new CalcDefine();
        CalcCommands y = new CalcPush();
        t.execCommand(stack,  new String[]{"define", "a", "4."}, map);
        y.execCommand(stack,  new String[]{"push", "a"}, map);
        assertEquals((Double)4.0, stack.peek());
    }

    @Test
    public void dounRadix() {


        String commands[]=new String[]{
        "define a 2.","define b 3.","define c 1.",
        "push 2","push a","mult", "push b","push 0","diff","push 4","push a",
         "mult" , "push c", "mult", "push b","push b", "mult",
                "diff", "sqrt", "push 0", "diff", "summ", "div"
        };

        addCommandToMap(commandMap);
        for(String i: commands)
        {
            String[] str = i.split(" ");
            if (commandMap.containsKey(str[0]))                     //Проверяем есть ли ключ в Map
            {

                commandMap.get(str[0]).execCommand(stack, str, map);

            }
            else
            {
                System.out.println("Неизвестная команда! ");
            }
        }


        assertEquals(-1., stack.peek(), -1.);
    }

    @Test
    public void topRadix() {


        String commands[]=new String[]{
                "define a 2.","define b 3.","define c 1.",
                "push 2","push a","mult", "push b","push 0","diff","push 4","push a",
                "mult" , "push c", "mult", "push b","push b", "mult",
                "diff", "sqrt", "summ", "div"
        };

        addCommandToMap(commandMap);
        for(String i: commands)
        {
            String[] str = i.split(" ");
            if (commandMap.containsKey(str[0]))                     //Проверяем есть ли ключ в Map
            {

                commandMap.get(str[0]).execCommand(stack, str, map);

            }
            else
            {
                System.out.println("Неизвестная команда!");
            }
        }


        assertEquals(-0.5, stack.peek(), -0.5);
    }

    private void addCommandToMap(Map <String, CalcCommands> commandMap)
    {
        //Заполняем map обьектами
        commandMap.put("push",new CalcPush());
        commandMap.put("#",new CalcComment());
        commandMap.put("diff",new CalcDiff());
        commandMap.put("div",new CalcDiv());
        commandMap.put("mult",new CalcMult());
        commandMap.put("pop",new CalcPop());
        commandMap.put("print",new CalcPrint());
        commandMap.put("sqrt",new CalcSqrt());
        commandMap.put("summ",new CalcSumm());
        commandMap.put("define",new CalcDefine());

    }


}


