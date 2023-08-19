package org.oo.demo.pseudo.threadsafety;

import org.oo.demo.annotation.ThreadSafe;
import org.oo.demo.iface.Servlet;
import org.oo.demo.model.ServletRequest;
import org.oo.demo.model.ServletResponse;

import java.math.BigInteger;

import static org.oo.demo.pseudo.threadsafety.CommonServletService.*;

@ThreadSafe
public class StatelessFactorizer implements Servlet {

    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = factor(i);
        encodeIntoResponse(resp, factors);
    }
}
