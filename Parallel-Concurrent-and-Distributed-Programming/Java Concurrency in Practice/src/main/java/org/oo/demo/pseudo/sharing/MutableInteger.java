package org.oo.demo.pseudo.sharing;

import org.oo.demo.annotation.NotThreadSafe;

@NotThreadSafe
public class MutableInteger {
    private int value;

    public int get() {
        return value;
    }

    public void set(int value) {
        this.value = value;
    }
}
