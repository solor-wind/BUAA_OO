package expr;

import java.math.BigInteger;
import java.util.HashMap;

public class NumberFactor implements Factor {
    private final BigInteger parameter;
    private final BigInteger exponent;

    public NumberFactor(BigInteger parameter, BigInteger exponent) {
        this.parameter = parameter;
        this.exponent = exponent;
    }

    public HashMap<Unit, BigInteger> toPoly() {
        if (parameter.equals(BigInteger.ZERO)) {
            return new HashMap<>();
        }
        Unit unit = new Unit(exponent, new HashMap<>());
        HashMap<Unit, BigInteger> poly = new HashMap<>();
        poly.put(unit, parameter);
        return poly;
    }
}
