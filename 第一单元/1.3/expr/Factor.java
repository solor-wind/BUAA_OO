package expr;

import java.math.BigInteger;
import java.util.HashMap;

public interface Factor {
    HashMap<Unit, BigInteger> toPoly();

    HashMap<Unit, BigInteger> toDelta();
}
