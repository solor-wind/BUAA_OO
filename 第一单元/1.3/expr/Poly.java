package expr;

import java.math.BigInteger;
import java.util.HashMap;

public class Poly {
    private HashMap<Unit, BigInteger> poly;

    public Poly(HashMap<Unit, BigInteger> poly) {
        this.poly = poly;
    }

    public static HashMap<Unit, BigInteger> polyMult(HashMap<Unit, BigInteger> a,
                                                     HashMap<Unit, BigInteger> b) {
        HashMap<Unit, BigInteger> ans = new HashMap<>();
        BigInteger parameter;
        BigInteger exponent;
        HashMap<Unit, BigInteger> expfun;
        for (Unit i : a.keySet()) {
            for (Unit j : b.keySet()) {
                exponent = i.get_exp().add(j.get_exp());
                parameter = a.get(i).multiply(b.get(j));
                expfun = polyAdd(i.get_factor(), j.get_factor());
                Unit unit = new Unit(exponent, expfun);
                if (ans.containsKey(unit)) {
                    ans.replace(unit, ans.get(unit).add(parameter));
                } else {
                    ans.put(unit, parameter);
                }
            }
        }
        return ans;
    }

    public static HashMap<Unit, BigInteger> polyAdd(HashMap<Unit, BigInteger> a,
                                                    HashMap<Unit, BigInteger> b) {
        HashMap<Unit, BigInteger> ans = new HashMap<>(a);
        for (Unit i : b.keySet()) {
            if (ans.containsKey(i)) {
                ans.replace(i, ans.get(i).add(b.get(i)));
            } else {
                ans.put(i, b.get(i));
            }
        }
        HashMap<Unit, BigInteger> tmp = new HashMap<>(ans);
        for (Unit it : tmp.keySet()) {
            if (ans.get(it).equals(BigInteger.ZERO)) {
                ans.remove(it);
            }
        }
        return ans;
    }

    public static HashMap<Unit, BigInteger> polyDelta(HashMap<Unit, BigInteger> c) {
        HashMap<Unit, BigInteger> ans = new HashMap<>();
        for (Unit i : c.keySet()) {
            if (i.get_exp().compareTo(BigInteger.ZERO) > 0) {
                Unit unit = new Unit(i.get_exp().subtract(BigInteger.ONE), i.get_factor());
                HashMap<Unit, BigInteger> tmp = new HashMap<>();
                tmp.put(unit, c.get(i).multiply(i.get_exp()));
                ans = polyAdd(ans, tmp);
            }
            HashMap<Unit, BigInteger> tmp = new HashMap<>();
            tmp.put(i, c.get(i));
            ans = polyAdd(ans, polyMult(tmp, polyDelta(i.get_factor())));
        }
        HashMap<Unit, BigInteger> tmp = new HashMap<>(ans);
        for (Unit it : tmp.keySet()) {
            if (ans.get(it).equals(BigInteger.ZERO)) {
                ans.remove(it);
            }
        }
        return ans;
    }
}
