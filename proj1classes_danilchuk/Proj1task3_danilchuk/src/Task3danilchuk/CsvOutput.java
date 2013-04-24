package Task3danilchuk;

import java.util.*;
//import java.util;
import java.util.Map.Entry;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 20.04.13
 * Time: 15:35
 * To change this template use File | Settings | File Templates.
 */
public class CsvOutput
{
    private Map<String,Integer> mainStorage=new HashMap<String, Integer>();
    private ArrayList<Entry<String, Integer>> sortList;
    private Integer sum;

    public void addString(String str)
    {
        Integer countOfStrings;
        countOfStrings=mainStorage.get(str);
        if(countOfStrings!=null)
        {
            int t=countOfStrings.intValue();
            t++;
            countOfStrings=new Integer(t);
        }
        else
        {
            countOfStrings=new Integer(1);
        }
        mainStorage.put(str,countOfStrings);
    }

    public void getSum()
    {

       int summ=0;
       Integer temp;
       /*for(int i=0;i<sortList.size();i++)
       {
           summ+=sortList.get(i).getValue();

       } на всякий случай закомментил старый вариант */
       for(Entry<String, Integer> n : sortList)
       {
           summ+=n.getValue();
       }

       sum=new Integer(summ);
    }

    public void sortByComparator(final boolean order)  // Метод содрал, до конца не разобрался как работает
    {

        sortList = new ArrayList<Entry<String, Integer>>(mainStorage.entrySet());

        // Sorting the list based on values
        Collections.sort(sortList, new Comparator<Entry<String, Integer>>()
        {
            public int compare(Entry<String, Integer> o1,
                               Entry<String, Integer> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });



    }

    public String outCSV()
    {
        String out=new String();
        String percStr;
        getSum();
        for(int i=0;i<sortList.size();i++)
        {
            percStr=percentCount(sortList.get(i).getValue());
            out=out.concat(sortList.get(i).getKey()+","+sortList.get(i).getValue()+","+percStr+",");
        }
        return out;
    }

    public String percentCount(Integer count)
    {
        String percent;
        percent=Double.toString(Math.rint((count.doubleValue()/sum.doubleValue())*10000)/100);

        return percent;
    }





}
