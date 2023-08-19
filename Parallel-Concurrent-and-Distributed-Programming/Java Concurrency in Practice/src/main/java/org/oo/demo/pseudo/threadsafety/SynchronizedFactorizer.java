package org.oo.demo.pseudo.threadsafety;

import org.oo.demo.annotation.GuardedBy;
import org.oo.demo.annotation.ThreadSafe;
import org.oo.demo.iface.Servlet;
import org.oo.demo.model.ServletRequest;
import org.oo.demo.model.ServletResponse;

import java.math.BigInteger;

import static org.oo.demo.pseudo.threadsafety.CommonServletService.*;

@ThreadSafe
public class SynchronizedFactorizer implements Servlet {

    @GuardedBy("this")
    private BigInteger lastNumber;
    @GuardedBy("this")
    private BigInteger[] lastFactors;

    public synchronized void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        if (i.equals(lastNumber)) {
            encodeIntoResponse(resp, lastFactors);
        } else {
            BigInteger[] factors = factor(i);
            lastNumber = i;
            lastFactors = factors;
            encodeIntoResponse(resp, factors);
        }
    }
}
