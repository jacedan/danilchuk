package task2;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 22.05.13
 * Time: 22:21
 * To change this template use File | Settings | File Templates.
 */
public class CalcCommandsFactory
{
    private final Map<String, CalcCommands> commandMap = new HashMap<String, CalcCommands>();
    private final static  CalcCommandsFactory inst = new CalcCommandsFactory();

    private CalcCommandsFactory()
    {
        Properties properties = new Properties();

        try
        {
            InputStream in = CalcCommandsFactory.class.getResourceAsStream("commands.properties");
            properties.load(in);
        }catch (IOException e)
        {
            System.out.println("Error in read commands.properties");
        }

        for (String key : properties.stringPropertyNames())
        {
            //properties.get(key);
            try{
                Class cls = Class.forName(properties.getProperty(key));
                commandMap.put(key, (CalcCommands) cls.newInstance());
            }
            catch (Exception e)
            {
                System.out.println("Не удалось прочитать класс: " + key );
            }
        }
    }

    public CalcCommands getCalcCommand (String command)
    {

        if(commandMap.containsKey(command))
        {
            return commandMap.get(command);
        }
        else
        {
            return null;
        }

    }

    public static CalcCommandsFactory getInst()
    {
        return inst;
    }


}
