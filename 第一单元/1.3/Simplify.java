import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

import expr.Unit;

public class Simplify {
    public static String pre_process(String input) {
        String output = input.replaceAll("[ \t]", "");
        for (int i = 0; i < 2; i++) {
            output = output.replaceAll("\\+\\+", "+");
            output = output.replaceAll("\\+-", "-");
            output = output.replaceAll("-\\+", "-");
            output = output.replaceAll("--", "+");
        }
        return output;
    }

    public static String simplify(HashMap<Unit, BigInteger> poly) {
        StringBuilder ans = new StringBuilder();
        if (poly.isEmpty()) {
            return "0";
        }
        if (poly.size() == 1) {
            for (Unit i : poly.keySet()) {
                if (poly.get(i).equals(BigInteger.ZERO)) {
                    ans.append("0");
                    return ans.toString();//特判0
                }
            }
        }
        Unit flag = new Unit(BigInteger.ONE.negate(), new HashMap<>());//指数暂时不会是负数
        for (Unit i : poly.keySet()) {
            if (poly.get(i).compareTo(BigInteger.ZERO) > 0) {
                ans.append(print_unit(i, poly.get(i)).substring(1));
                flag = i;//优先输出正数
                break;
            }
        }
        for (Unit i : poly.keySet()) {
            if (i.equals(flag)) {
                continue;
            }
            ans.append(print_unit(i, poly.get(i)));
        }
        return ans.toString();
    }

    public static String print_unit(Unit unit, BigInteger parameter) {
        BigInteger exponent = unit.get_exp();
        StringBuilder string = new StringBuilder();

        if (parameter.compareTo(BigInteger.ZERO) > 0) {
            string.append("+");//先判定符号
        } else {
            string.append("-");
        }

        //针对系数是1或-1的情况进行化简
        if (parameter.equals(BigInteger.ONE) || parameter.equals(BigInteger.ONE.negate())) {
            if (exponent.equals(BigInteger.ZERO) && unit.get_factor().isEmpty()) {
                string.append("1");
                return string.toString();
            }

            if (exponent.equals(BigInteger.ONE)) {
                string.append("x");
            } else if (exponent.compareTo(BigInteger.ONE) > 0) {
                string.append("x^");
                string.append(exponent);
            }

            if (!unit.get_factor().isEmpty()) {
                if (exponent.equals(BigInteger.ZERO)) {
                    string.append("exp(");
                } else {
                    string.append("*exp(");
                }
                ArrayList<String> stringFactor = gcd_simplify(unit.get_factor());//提取公因数
                string.append(stringFactor.get(0));
                string.append(")");
                string.append(stringFactor.get(1));
            }

            return string.toString();
        }

        string.append(parameter.abs());//确定系数不为1或-1
        if (exponent.equals(BigInteger.ONE)) {
            string.append("*x");
        } else if (exponent.compareTo(BigInteger.ONE) > 0) {
            string.append("*x^");
            string.append(exponent);
        }

        if (!unit.get_factor().isEmpty()) {
            string.append("*exp(");
            ArrayList<String> stringFactor = gcd_simplify(unit.get_factor());
            string.append(stringFactor.get(0));
            string.append(")");
            string.append(stringFactor.get(1));
        }
        return string.toString();
    }

    public static String print_exp(HashMap<Unit, BigInteger> factor) {
        StringBuilder exp = new StringBuilder();
        int flag = 0;//是不是表达式
        if (factor.size() > 1) {
            flag = 2;
        } else {
            for (Unit it : factor.keySet()) {
                if (!factor.get(it).equals(BigInteger.ONE)) {
                    flag++;
                }
                if (!it.get_exp().equals(BigInteger.ZERO)) {
                    flag++;
                }
                if (!it.get_factor().isEmpty()) {
                    flag++;
                }
            }
        }
        if (flag >= 2) {
            exp.append("(");
        }
        exp.append(Simplify.simplify(factor));
        if (flag >= 2) {
            exp.append(")");
        }
        return exp.toString();
    }

    public static ArrayList<String> gcd_simplify(HashMap<Unit, BigInteger> factor) { //提出最大公因数来化简
        ArrayList<String> stringFactor = new ArrayList<>();
        ArrayList<BigInteger> parameter = new ArrayList<>(factor.values());

        BigInteger gcd = parameter.get(0).abs();
        for (int i = 1; i < parameter.size(); i++) {
            gcd = gcd.gcd(parameter.get(i));
        }
        if (gcd.equals(BigInteger.ONE)) {
            stringFactor.add(print_exp(factor));//exp((300000+299999*x))=exp(1)*exp((1+x))^299999
            stringFactor.add("");
        } else {
            String tmp1 = print_exp(factor);
            HashMap<Unit, BigInteger> newfactor = new HashMap<>();
            for (Unit i : factor.keySet()) {
                newfactor.put(i, factor.get(i).divide(gcd));
            
            String tmp2 = print_exp(newfactor);
            if (tmp2.length() + gcd.toString().length() + 1 < tmp1.length()) {
                stringFactor.add(tmp2);
                stringFactor.add("^" + gcd);
            } else {
                stringFactor.add(tmp1);
                stringFactor.add("");
            }
        }
        return stringFactor;
    }

}
