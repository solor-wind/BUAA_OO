package expr;

import java.math.BigInteger;
import java.util.HashMap;

public class ExprFactor implements Factor {
    private Expr expr;
    private BigInteger exponent;

    public ExprFactor(Expr expr, BigInteger exponent) {
        this.expr = expr;
        this.exponent = exponent;
    }

    public HashMap<Unit, BigInteger> toPoly() {
        HashMap<Unit, BigInteger> poly = new HashMap<>();
        if (exponent.equals(BigInteger.ZERO)) {
            Unit unit = new Unit(BigInteger.ZERO, new HashMap<>());
            poly.put(unit, BigInteger.ONE);
            return poly;
        } else if (exponent.equals(BigInteger.ONE)) {
            return expr.toPoly();
        } else {
            HashMap<Unit, BigInteger> tmp = expr.toPoly();
            poly = Poly.polyMult(tmp, tmp);
            for (BigInteger i = BigInteger.ONE.add(BigInteger.ONE); i.compareTo(exponent) < 0;
                 i = i.add(BigInteger.ONE)) {
                poly = Poly.polyMult(poly, tmp);//可以优化成快速幂？
            }
            return poly;
        }
    }
}
