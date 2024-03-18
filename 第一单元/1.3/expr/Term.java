package expr;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class Term {
    private final ArrayList<Factor> factors;//因子之间是相乘关系

    public Term() {
        this.factors = new ArrayList<>();
    }

    public void addFactor(Factor factor) {
        factors.add(factor);
    }

    public HashMap<Unit, BigInteger> toPoly() {
        if (factors.size() == 1) {
            return factors.get(0).toPoly();
        } else {
            HashMap<Unit, BigInteger> ans =
                    Poly.polyMult(factors.get(0).toPoly(), factors.get(1).toPoly());
            for (int i = 2; i < factors.size(); i++) {
                ans = Poly.polyMult(ans, factors.get(i).toPoly());
            }
            return ans;
        }
    }

    public HashMap<Unit, BigInteger> toDelta() {
        if (factors.size() == 1) {
            return factors.get(0).toDelta();
        } else {
            HashMap<Unit, BigInteger> ans = new HashMap<>();
            for (int i = 0; i < factors.size(); i++) {
                HashMap<Unit, BigInteger> tmp = factors.get(i).toDelta();
                for (int j = 0; j < factors.size(); j++) {
                    if (i == j) {
                        continue;
                    }
                    tmp = Poly.polyMult(tmp, factors.get(j).toPoly());
                }
                ans = Poly.polyAdd(ans, tmp);
            }
            return ans;
        }
    }
}
