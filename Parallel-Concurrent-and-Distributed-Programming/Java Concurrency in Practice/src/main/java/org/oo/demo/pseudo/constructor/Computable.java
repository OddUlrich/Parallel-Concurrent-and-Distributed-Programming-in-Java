package org.oo.demo.pseudo.constructor;

public interface Computable<A, V> {
    V compute(A arg) throws InterruptedException;
}
