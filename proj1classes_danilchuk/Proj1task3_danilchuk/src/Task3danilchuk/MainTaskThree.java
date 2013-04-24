package Task3danilchuk;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 20.04.13
 * Time: 20:38
 * To change this template use File | Settings | File Templates.
 */
public class MainTaskThree
{
    public static void main(String[] args)
    {
         CsvOutput csv=new CsvOutput();
         readFile(csv,"C:\\input.txt");
         csv.sortByComparator(false);
         System.out.println(csv.outCSV());
         System.out.println("end");
    }

    public static void readFile(CsvOutput csv,String fileName)
    {
        StringBuilder bufferStr=new StringBuilder();
        int ch;
        char chr;
        Reader buffer;
        try
        {
            buffer=new BufferedReader(new FileReader(fileName));
            do
            {
                ch=buffer.read();
                chr=(char)ch;
                if(Character.isLetterOrDigit(chr))
                {
                    bufferStr=bufferStr.append(chr);
                }
                else
                {
                    if(bufferStr.length()>0)
                    {
                        csv.addString(bufferStr.toString());
                        bufferStr=bufferStr.delete(0,bufferStr.length());
                    }
                }
            }
            while(ch!=-1);

        }
        catch (FileNotFoundException e)
        {
            System.out.println("file not found!!!!");
            return;
        }
        catch (IOException e)
        {
            System.out.println(e);
            return;

        }

    }

}