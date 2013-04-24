package Proj1package;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 17.04.13
 * Time: 21:20
 * To change this template use File | Settings | File Templates.
 */
public class RndWithUser    // Не могу вьехать в разницу между паблик и привате в случае с классом а не его членами и методами(((
{
    private int rndValue;
    private int userValue;

    RndWithUser()                      //конструктор с инициализацией
    {
        Random rnd = new Random();
        rndValue=rnd.nextInt(100);

    }

   /* public void getUserValue(int interfaceValue)
    {
        userValue=interfaceValue;
    }               */

    public boolean checkValues(int interfaceValue)
    {
        userValue=interfaceValue;
        if(rndValue==userValue)
        {
            return true;
        }
        else
        {
            return false;

        }


    }
    public int getRndValue()
    {
        return rndValue;
    }


}
