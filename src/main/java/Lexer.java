import java.util.Map;

/**
 * Created by yvestao on 2017/6/13.
 */
public class Lexer {
  private String expression;
  private int position;
  private char currentChar;
  private Map<String, String> parameter;

  public void init(String expression) {
    this.expression = expression;
    reset();
  }

  public void reset() {
    this.position = 0;
    currentChar = expression.charAt(position);
  }


  public void setMap(Map<String, String > parameter) {
    this.parameter = parameter;
  }


  public Token getNextToken() {
    switch (currentChar) {
      case '+':
        advance();
        return new Token(TypeOfToken.PLUS);
      case '-':
        advance();
        return new Token(TypeOfToken.MINUS);
      case '*':
        advance();
        return new Token(TypeOfToken.MUL);
      case '/':
        advance();
        return new Token(TypeOfToken.DIV);
      case '(':
        advance();
        return new Token(TypeOfToken.LPAREN);
      case ')':
        advance();
        return new Token(TypeOfToken.RPAREN);
      case 'a':
        return abs();
      case '>':
        return compareToken('>');
      case '<':
        return compareToken('<');
      case '$':
        return getDigital();
      case '\0':
        return null;
      default:
        return null;
    }
  }

  private Token getDigital() {
    advance(); // $
    advance(); // {
    double value;
    if (currentChar >= '0' && currentChar <= '9') {
      int tag = position;
      while (currentChar == '.' || (currentChar>='0' && currentChar <= '9') ) {
        advance();
      }
      value = Double.valueOf(expression.substring(tag,position)).doubleValue();
    } else {
      int tag = position;
      while (currentChar != '}') {
        advance();
      }
      String key = expression.substring(tag, position);
      value = Double.valueOf(parameter.get(key)).doubleValue();
    }
    advance();
    return new Token(TypeOfToken.DIGITAL, value);
  }

  private void advance() {
    position ++;
    if (position >= expression.length()) {
      currentChar = '\0';
    } else {
      currentChar = expression.charAt(position);
    }
  }

  private Token abs() {
    if (expression.charAt(position) == 'a' && expression.charAt(position+1) == 'b'
            && expression.charAt(position+2) == 's') {
      advance();  // a
      advance();  // b
      advance();  // s
      advance();  // (
    }
    return new Token(TypeOfToken.ABS);
  }

  private Token compareToken(char c) {
    advance();
    if (c == '>') {
      if (currentChar == '=') {
        advance();
        return new Token(TypeOfToken.NOT_LESS);
      } else {
        return new Token(TypeOfToken.GREATER);
      }
    } else {
      if (currentChar == '=') {
        advance();
        return new Token(TypeOfToken.NOT_GREATER);
      } else {
        return new Token(TypeOfToken.LESS);
      }
    }
  }

  private double getInteger () {
    int tag = position;
    while (currentChar == '.' || (currentChar>='0' && currentChar <= '9') ) {
      advance();
    }
    return Double.valueOf(expression.substring(tag,position)).doubleValue();
  }

}
