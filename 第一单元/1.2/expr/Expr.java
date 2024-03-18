package expr;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class Expr {
    private final ArrayList<Term> terms;//项之间是相加关系

    public Expr() {
        this.terms = new ArrayList<>();
    }

    public void addTerm(String sign, Term term) {
        if (sign.equals("-")) {
            term.addFactor(new NumberFactor(BigInteger.ONE.negate(), BigInteger.ZERO));
        }
        this.terms.add(term);
    }

    public HashMap<Unit, BigInteger> toPoly() {
        HashMap<Unit, BigInteger> ans = new HashMap<>();
        for (int i = 0; i < terms.size(); i++) {
            ans = Poly.polyAdd(ans, terms.get(i).toPoly());
        }
        return ans;
    }

}
