import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by yvestao on 2017/6/13.
 */
public class Interpreter {

  private Lexer lexer;
  private Token currentToken;
  public Interpreter(String expression) {
    lexer = new Lexer();
    lexer.init(expression);
  }

  public boolean caculate(Map<String, String> parameter) {
    lexer.setMap(parameter);
    currentToken = lexer.getNextToken();
    boolean result = compare();
    lexer.reset();
    return result;
  }


  private void eat(TypeOfToken type) {
    if (type.equals(currentToken.type)) {
      currentToken = lexer.getNextToken();
    } else {
      throw new RuntimeException("Token no matched");
    }
  }

  private double factor() {
    double result = 0.0;

    switch (currentToken.type) {
      case DIGITAL:
        result = currentToken.value;
        eat(TypeOfToken.DIGITAL);
        break;
      case LPAREN:
        eat(TypeOfToken.LPAREN);
        result = expr();
        eat(TypeOfToken.RPAREN);
        break;
      case ABS:
        eat(TypeOfToken.ABS);
        result = Math.abs(expr());
        eat(TypeOfToken.RPAREN);
        break;
    }
    return result;
  }

  private double term() {
    double result = factor();
    while (currentToken != null) {
      switch (currentToken.type) {
        case MUL:
          eat(TypeOfToken.MUL);
          result *= factor();
          break;
        case DIV:
          eat(TypeOfToken.DIV);
          result /= factor();
          break;
        default:
          return result;
      }
    }
    return result;
  }

  public double expr() {
    double result = term();
    while (currentToken != null) {
      switch (currentToken.type) {
        case PLUS:
          eat(TypeOfToken.PLUS);
          result += term();
          break;
        case MINUS:
          eat(TypeOfToken.MINUS);
          //double减法操作精度问题
          BigDecimal a = new BigDecimal(String.valueOf(result));
          BigDecimal b = new BigDecimal(String.valueOf(term()));
          result = a.subtract(b).doubleValue();
          break;
        default:
          return result;
      }
    }
    return result;
  }

  private boolean compare() {
    double l_result = expr();
    double r_result;
    while (currentToken != null) {
      Token compareToken = currentToken;
      eat(currentToken.type);
      r_result = expr();
      System.out.println(l_result);
      System.out.println(r_result);
      switch (compareToken.type) {
        case GREATER:
          return l_result > r_result ? true: false;
        case LESS:
          return l_result < r_result ? true: false;
        case NOT_GREATER:
          return l_result <= r_result ? true: false;
        case NOT_LESS:
          return l_result >= r_result ? true: false;
      }
    }
    return false;
  }

}
