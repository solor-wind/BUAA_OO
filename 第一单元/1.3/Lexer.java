import java.util.Arrays;
import java.util.HashSet;

public class Lexer { //词法分析器
    private final String input;
    private int pos = 0;
    private String curToken;
    private final HashSet<Character> set =
            new HashSet<>(Arrays.asList('+', '-', '*', '^', 'x', '(', ')', 'f', 'g', 'h', ','));

    public Lexer(String input) {
        this.input = input;
        this.next();//创建的时候next一下，更新pos与curToken为第一个值
    }

    private String getNumber() {
        StringBuilder sb = new StringBuilder();
        while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
            sb.append(input.charAt(pos));
            ++pos;
        }

        return sb.toString();
    }

    public void next() {
        if (pos == input.length()) {
            return;
        }

        char c = input.charAt(pos);
        if (Character.isDigit(c)) {
            curToken = getNumber();
        } else if (set.contains(c)) {
            pos += 1;
            curToken = String.valueOf(c);
        } else if (c == 'e') {
            pos += 3;
            curToken = "exp";
        } else if (c == 'd') {
            pos += 2;
            curToken = "dx";
        }
    }

    public String peek() {
        return this.curToken;
    }
}
