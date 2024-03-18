import java.util.ArrayList;
import java.util.HashMap;

public class MyFun {
    private static HashMap<String, Integer> map = new HashMap<>();//函数名-具体函数
    private static ArrayList<ArrayList<String>> can = new ArrayList<>();//参数列表
    private static ArrayList<String> fun = new ArrayList<>();//函数体

    public static void addFunction(String input0) {
        String input = input0;
        input = input.replaceAll("exp", "a");//保护x不被替换
        map.put(input.substring(0, 1), map.size());
        int i = 2;
        ArrayList<String> canshu = new ArrayList<>();
        for (; input.charAt(i) != '='; i += 2) {
            char ch = (char) (input.charAt(i) - 15);
            input = input.replaceAll(String.valueOf(input.charAt(i)), String.valueOf(ch));
            canshu.add(String.valueOf(ch));//f(x,y,z)=//0 1 2 3 4 5 6 7 8
        }
        can.add(canshu);
        input = input.replaceAll("a", "exp");
        fun.add(input.substring(i + 1));
    }

    public static boolean containFun(String string) {
        return map.containsKey(string);
    }

    public static String callFun(String string, ArrayList<String> array) {
        String ans = fun.get(map.get(string));
        for (int i = 0; i < array.size(); i++) {
            ans = ans.replaceAll(can.get(map.get(string)).get(i), array.get(i));
        }
        return ans;
    }

    public static String parseFun(Lexer lexer) {
        final String name = lexer.peek();
        ArrayList<String> can = new ArrayList<>();
        StringBuilder tmp = new StringBuilder("(");
        lexer.next();//跳过函数名
        lexer.next();//跳过第一个括号
        int left = 1;
        int right = 0;
        while (true) {
            if (lexer.peek().equals(",")) {
                tmp.append(")");
                can.add(tmp.toString());
                tmp = new StringBuilder("(");
                lexer.next();
                continue;
            }
            if (lexer.peek().equals(")")) {
                right++;
                if (right == left) {
                    lexer.next();
                    break;
                }
            } else if (lexer.peek().equals("(")) {
                left++;
            } else if (map.containsKey(lexer.peek())) {
                tmp.append(parseFun(lexer));//循环嵌套
                continue;
            }
            tmp.append(lexer.peek());
            lexer.next();
        }
        tmp.append(")");
        can.add(tmp.toString());
        return "(" + MyFun.callFun(name, can) + ")";
    }
}
