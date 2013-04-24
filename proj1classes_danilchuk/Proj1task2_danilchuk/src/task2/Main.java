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
public class Main
{
    static Stack<Double> mainStack=new Stack<Double>();
    static Map <String, Double> varMap = new HashMap<String, Double>();
    static Map <String, CalcCommands> commandMap = new HashMap<String, CalcCommands>();

    public static void main(String[] args)
    {

        String s;
        Scanner scn;


        if (args.length > 0)
        {
            try
            {
                scn = new Scanner(new FileInputStream(args[0]));
            }
            catch(FileNotFoundException e)
            {
                System.out.println("Файл не найден");
                return;
            }

        }
        else
        {
            scn=new Scanner(System.in);
        }

        addCommandToMap();

        while (scn.hasNextLine())
        {

            s = scn.nextLine();
            String[] str = s.split(" ");
            if (commandMap.containsKey(str[0]))                     //Проверяем есть ли ключ в Map
            {




                commandMap.get(str[0]).execCommand(mainStack, str, varMap);    //Выполнится если ключ есть в Map
            }
            else
            {
                System.out.println("Неизвестная команда!");
            }

        }


    }

    public static void addCommandToMap()
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
