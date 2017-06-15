
/**
 * Created by yvestao on 2017/6/13.
 */
public class Token {
  TypeOfToken type;
  double value;
  public Token(TypeOfToken type) {
    this.type = type;
  }
  public Token(TypeOfToken type, double value) {
    this.type = type;
    this.value = value;
  }
}
