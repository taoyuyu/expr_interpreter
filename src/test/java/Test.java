import java.util.HashMap;
import java.util.Map;

/**
 * Created by yvestao on 2017/6/13.
 */
public class Test {
  @org.junit.Test
  public void testExpression() {
//    System.out.println(19.99+20);
//    System.out.println(1.0-0.66);
//    System.out.println(0.033*100);
//    System.out.println(12.3/100);

    // java 小问题 double 做减法溢出
    Interpreter interpreter = new Interpreter("${n1}+abs((${n2}+${n3})*${n4}/${1.55}-${n5})<=${1.3}");
    Map<String, String> parameter = new HashMap<String, String>();

    parameter.put("n1", "1");
    parameter.put("n2", "1");
    parameter.put("n3", "2");
    parameter.put("n4", "3.1");
    parameter.put("n5", "10");

    System.out.println(interpreter.caculate(parameter));
    parameter.put("n5", "9.3");
    System.out.println(interpreter.caculate(parameter));
  }
}
