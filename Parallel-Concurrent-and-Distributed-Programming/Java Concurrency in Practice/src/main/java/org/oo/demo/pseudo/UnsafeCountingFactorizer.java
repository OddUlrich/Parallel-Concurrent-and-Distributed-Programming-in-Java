package org.oo.demo.pseudo;

import org.oo.demo.annotation.NotThreadSafe;
import org.oo.demo.iface.Servlet;
import org.oo.demo.model.ServletRequest;
import org.oo.demo.model.ServletResponse;

import java.math.BigInteger;

import static org.oo.demo.pseudo.CommonServletService.*;

@NotThreadSafe
public class UnsafeCountingFactorizer implements Servlet {

    private long count = 0;

    public long getCount() {
        return count;
    }

    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = factor(i);
        ++count;
        encodeIntoResponse(resp, factors);
    }
}
