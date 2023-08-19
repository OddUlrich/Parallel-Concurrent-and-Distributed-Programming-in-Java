package org.oo.demo.pseudo.threadsafety;

import org.oo.demo.annotation.ThreadSafe;
import org.oo.demo.iface.Servlet;
import org.oo.demo.model.ServletRequest;
import org.oo.demo.model.ServletResponse;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;

import static org.oo.demo.pseudo.threadsafety.CommonServletService.*;

@ThreadSafe
public class CountingFactorizer implements Servlet {

    private final AtomicLong count = new AtomicLong(0);

    public long getCount() {
        return count.get();
    }

    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = factor(i);
        count.incrementAndGet();
        encodeIntoResponse(resp, factors);
    }

}
