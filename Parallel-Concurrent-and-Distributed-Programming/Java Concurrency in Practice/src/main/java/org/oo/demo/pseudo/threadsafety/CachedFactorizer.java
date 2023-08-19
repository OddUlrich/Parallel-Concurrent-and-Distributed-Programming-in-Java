package org.oo.demo.pseudo.threadsafety;

import org.oo.demo.annotation.GuardedBy;
import org.oo.demo.annotation.ThreadSafe;
import org.oo.demo.iface.Servlet;
import org.oo.demo.model.ServletRequest;
import org.oo.demo.model.ServletResponse;

import java.math.BigInteger;

import static org.oo.demo.pseudo.threadsafety.CommonServletService.*;

@ThreadSafe
public class CachedFactorizer implements Servlet {

    @GuardedBy("this")
    private BigInteger lastNumber;
    @GuardedBy("this")
    private BigInteger[] lastFactors;
    @GuardedBy("this")
    private long hits;
    @GuardedBy("this")
    private long cacheHits;

    public synchronized  long getHits() {
        return hits;
    }

    public synchronized double getCacheHitRatio() {
        return (double) cacheHits / (double) hits;
    }

    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = null;
        synchronized (this) {
            ++hits;
            if (i.equals(lastNumber)) {
                ++cacheHits;
                factors = lastFactors.clone();
            }
        }
        if (factors == null) {
            factors = factor(i);
            synchronized (this) {
                lastNumber = i;
                lastFactors = factors.clone();
            }
        }
        encodeIntoResponse(resp, factors);

    }
}
