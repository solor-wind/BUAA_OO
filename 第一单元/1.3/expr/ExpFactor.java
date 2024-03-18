package expr;

import java.math.BigInteger;
import java.util.HashMap;

public class ExpFactor implements Factor {
    private Factor factor;//指数内的因子
    private BigInteger exponent;

    public ExpFactor(Factor factor, BigInteger exponent) {
        this.factor = factor;
        this.exponent = exponent;
    }

    public HashMap<Unit, BigInteger> toPoly() {
        Unit unit = new Unit(BigInteger.ZERO, Poly.polyMult(factor.toPoly(),
                new NumberFactor(exponent, BigInteger.ZERO).toPoly()));
        HashMap<Unit, BigInteger> poly = new HashMap<>();
        poly.put(unit, BigInteger.ONE);
        return poly;
    }

    public HashMap<Unit, BigInteger> toDelta() {
        HashMap<Unit, BigInteger> poly = new HashMap<>();
        if (exponent.equals(BigInteger.ZERO)) {
            return poly;
        }
        Unit unit = new Unit(BigInteger.ZERO, Poly.polyMult(factor.toPoly(),
                new NumberFactor(exponent, BigInteger.ZERO).toPoly()));
        poly.put(unit, exponent);
        poly = Poly.polyMult(poly, factor.toDelta());
        return poly;
    }
}
