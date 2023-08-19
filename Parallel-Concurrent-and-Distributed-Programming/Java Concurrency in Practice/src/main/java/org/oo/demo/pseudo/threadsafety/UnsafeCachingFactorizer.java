package org.oo.demo.pseudo.threadsafety;

import org.oo.demo.annotation.NotThreadSafe;
import org.oo.demo.iface.Servlet;
import org.oo.demo.model.ServletRequest;
import org.oo.demo.model.ServletResponse;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.oo.demo.pseudo.threadsafety.CommonServletService.*;

@NotThreadSafe
public class UnsafeCachingFactorizer implements Servlet {

    private final AtomicReference<BigInteger> lastNumber = new AtomicReference<>();
    private final AtomicReference<BigInteger[]> lastFactors = new AtomicReference<>();

    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        if (i.equals(lastNumber.get())) {
            encodeIntoResponse(resp, lastFactors.get());
        } else {
            BigInteger[] factors = factor(i);
            lastNumber.set(i);
            lastFactors.set(factors);
            encodeIntoResponse(resp, factors);
        }
    }
}
