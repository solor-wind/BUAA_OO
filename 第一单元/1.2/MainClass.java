import expr.Expr;

import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < n; i++) {
            MyFun.addFunction(Simplify.pre_process(scanner.nextLine()));
        }
        String input = Simplify.pre_process(scanner.nextLine());//预处理
        scanner.close();

        Lexer lexer = new Lexer(input);//词法分析，获得token
        Parser parser = new Parser(lexer);//解析输入，获得输出
        Expr expr = parser.parseExpr();

        System.out.println(Simplify.simplify(expr.toPoly()));//化简
    }
}
