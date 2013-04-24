package task2;

import java.util.Map;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 28.04.13
 * Time: 12:43
 * To change this template use File | Settings | File Templates.
 */
public interface CalcCommands {
    public void execCommand(Stack<Double> mainStack, String[] toStack, Map<String, Double> map);

}
