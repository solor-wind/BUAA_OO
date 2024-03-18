package expr;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Objects;

public class Unit {
    //1*x^2*exp(3)
    private final BigInteger exponent;//指数
    private final HashMap<Unit, BigInteger> factor;//因子

    public Unit(BigInteger exponent, HashMap<Unit, BigInteger> factor) {
        this.exponent = exponent;
        this.factor = factor;
    }

    public BigInteger get_exp() {
        return exponent;
    }

    public HashMap<Unit, BigInteger> get_factor() {
        return factor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Unit)) {
            return false;
        }
        Unit other = (Unit) o;
        return other.exponent.equals(exponent) && other.factor.equals(factor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exponent, factor.hashCode());//这样写行吗？
    }

}
