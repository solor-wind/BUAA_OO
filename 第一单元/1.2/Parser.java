import expr.ExprFactor;
import expr.ExpFactor;
import expr.NumberFactor;
import expr.Factor;
import expr.Expr;
import expr.Term;

import java.math.BigInteger;

public class Parser {
    private final Lexer lexer;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public Expr parseExpr() {
        Expr expr = new Expr();//表达式
        String sign;
        if (lexer.peek().equals("+") || lexer.peek().equals("-")) {
            sign = lexer.peek();
            lexer.next();
            expr.addTerm(sign, parseTerm());
        } else {
            expr.addTerm("+", parseTerm());
        }

        while (lexer.peek().equals("+") || lexer.peek().equals("-")) {
            sign = lexer.peek();
            lexer.next();
            expr.addTerm(sign, parseTerm());
        }
        return expr;
    }

    public Term parseTerm() {
        Term term = new Term();//项
        term.addFactor(parseFactor());

        while (lexer.peek().equals("*")) {
            lexer.next();
            term.addFactor(parseFactor());
        }
        return term;
    }

    public Factor parseFactor() {
        if (lexer.peek().equals("(")) { //表达式因子
            return parseExprFactor();
        } else if (lexer.peek().equals("exp")) { //指数函数因子
            return parseExpFactor();
        } else if (MyFun.containFun(lexer.peek())) { //自定义函数因子
            Lexer lexer2 = new Lexer(Simplify.pre_process(MyFun.parseFun(lexer)));
            Parser parser2 = new Parser(lexer2);
            Expr expr2 = parser2.parseExpr();
            return new ExprFactor(expr2, BigInteger.ONE);
        } else {
            return parseXFactor();
        }
    }

    public ExprFactor parseExprFactor() {
        lexer.next();
        Expr expr = parseExpr();
        lexer.next();//跳过括号
        BigInteger exp = BigInteger.ONE;
        if (lexer.peek().equals("^")) {
            lexer.next();
            if (lexer.peek().equals("+")) {
                lexer.next();
            }
            exp = new BigInteger(lexer.peek());
            lexer.next();
        }
        return new ExprFactor(expr, exp);
    }

    public ExpFactor parseExpFactor() {
        lexer.next();
        lexer.next();//去括号
        Factor expr = parseFactor();
        lexer.next();
        BigInteger exp = BigInteger.ONE;
        if (lexer.peek().equals("^")) {
            lexer.next();
            if (lexer.peek().equals("+")) {
                lexer.next();
            }
            exp = new BigInteger(lexer.peek());
            lexer.next();
        }
        return new ExpFactor(expr, exp);
    }

    public NumberFactor parseXFactor() {
        if (lexer.peek().equals("x")) { //幂函数因子
            lexer.next();
            BigInteger para = new BigInteger("1");
            BigInteger exp = BigInteger.ONE;
            if (lexer.peek().equals("^")) {
                lexer.next();
                if (lexer.peek().equals("+")) {
                    lexer.next();
                }
                exp = new BigInteger(lexer.peek());
                lexer.next();
            }
            return new NumberFactor(para, exp);
        } else { //常数因子
            String sign;
            if (lexer.peek().equals("+") || lexer.peek().equals("-")) {
                sign = lexer.peek();
                lexer.next();
            } else {
                sign = "+";
            }
            BigInteger para = new BigInteger(sign + lexer.peek());//自动转前导0
            lexer.next();
            return new NumberFactor(para, BigInteger.ZERO);
        }
    }

}
