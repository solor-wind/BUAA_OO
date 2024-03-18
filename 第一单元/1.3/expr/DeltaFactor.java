package expr;

import java.math.BigInteger;
import java.util.HashMap;

public class DeltaFactor implements Factor {
    private final Expr expr;

    public DeltaFactor(Expr expr) {
        this.expr = expr;
    }

    public HashMap<Unit, BigInteger> toPoly() {
        return expr.toDelta();
    }

    public HashMap<Unit, BigInteger> toDelta() {
        return Poly.polyDelta(expr.toDelta());
    }
}
