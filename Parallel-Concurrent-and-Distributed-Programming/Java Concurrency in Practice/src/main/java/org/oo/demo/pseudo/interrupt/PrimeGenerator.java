package org.oo.demo.pseudo.interrupt;

import org.oo.demo.annotation.GuardedBy;
import org.oo.demo.annotation.ThreadSafe;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@ThreadSafe
public class PrimeGenerator implements Runnable {

    @GuardedBy("this")
    private final List<BigInteger> primes = new ArrayList<>();
    private volatile  boolean cancelled;

    public void run() {
        BigInteger p = BigInteger.ONE;
        while (!cancelled) {
            p = p.nextProbablePrime();
            synchronized (this) {
                primes.add(p);
            }
        }
    }

    public void cancel() {
        cancelled = true;
    }

    public synchronized List<BigInteger> get() {
        return new ArrayList<>(primes);
    }

}
