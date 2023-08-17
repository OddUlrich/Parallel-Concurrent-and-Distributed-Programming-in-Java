package org.oo.demo.pseudo;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.math.BigInteger;

public class CommonServletService {

    public static BigInteger extractFromRequest(ServletRequest req) {
        return BigInteger.ONE;
    }

    public static BigInteger[] factor(BigInteger i) {
        return new BigInteger[]{BigInteger.ONE};
    }

    public static void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {

    }

}
